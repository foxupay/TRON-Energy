package com.fox.energy.tron.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fox.energy.common.core.domain.CommonResult;
import com.fox.energy.common.core.domain.tron.TronAccountResponse;
import com.fox.energy.common.core.domain.tron.TronBalance;
import com.fox.energy.common.core.domain.tron.TronTransactionModel;
import com.fox.energy.common.enums.TronToken;
import com.fox.energy.common.utils.spring.SpringUtils;
import com.fox.energy.system.service.ISysConfigService;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class TronSupport {

    private static final Logger logger = LoggerFactory.getLogger(TronSupport.class);

    private static final ISysConfigService sysConfigService = SpringUtils.getBean(ISysConfigService.class);

    private static AtomicInteger index = new AtomicInteger(0);
    private static final int MAX_AVAILABLE = 10;
    private static final Semaphore semaphore = new Semaphore(MAX_AVAILABLE);

    private static int getIndex() {
        String[] TRON_KEYS = sysConfigService.selectConfigByKey("app.tronscan.key").split(";");
        if (index.get() >= (TRON_KEYS.length - 1)) {
            index = new AtomicInteger(0);
            return index.get();
        }
        return index.incrementAndGet();
    }

    public static String TRON_KEY() {
        String[] TRON_KEYS = sysConfigService.selectConfigByKey("app.tronscan.key").split(";");
        return TRON_KEYS[getIndex()];
    }


    public static TronTransactionModel getTransaction(String hash) {
        try {
            semaphore.acquire();
            String url = "https://apilist.tronscanapi.com/api/transaction-info?hash=" + hash;
            logger.info("查询TRC20交易 : {}", hash);
            logger.info("查询TRC20交易 : {}", TRON_KEY());
            String result = HttpRequest.get(url).header("TRON-PRO-API-KEY", TRON_KEY()).execute().body();
            logger.info("查询TRC20交易 : {}", result);
            JSONObject resultObj = JSONUtil.parseObj(result);
            if (!resultObj.containsKey("contractRet")) {
                return null;
            }
            TronTransactionModel model = new TronTransactionModel();
            model.setHash(resultObj.getStr("hash"));
            model.setContractRet("SUCCESS".equals(resultObj.getStr("contractRet")));
            if (!model.isContractRet()) {
                if (resultObj.containsKey("info")) {
                    JSONObject info = resultObj.getJSONObject("info");
                    model.setInfo(info.containsKey("resMessage") ? info.getStr("resMessage") : resultObj.getStr("contractRet"));
                } else {
                    model.setInfo(resultObj.getStr("contractRet"));
                }
            }
            model.setConfirmed(resultObj.getBool("confirmed"));
            model.setConfirmations(resultObj.getInt("confirmations"));

            if (resultObj.getInt("contractType") == 1) {
                JSONObject contractData = resultObj.getJSONObject("contractData");
                model.setSymbol("TRX");
                model.setAmount(contractData.getBigDecimal("amount"));
                model.setFrom(contractData.getStr("owner_address"));
                model.setTo(contractData.getStr("to_address"));
            } else if (resultObj.containsKey("tokenTransferInfo") && !resultObj.getJSONObject("tokenTransferInfo").isEmpty()) {
                JSONObject tokenTransferInfo = resultObj.getJSONObject("tokenTransferInfo");
                model.setSymbol(tokenTransferInfo.getStr("symbol"));
                model.setAmount(tokenTransferInfo.getBigDecimal("amount_str"));
                model.setFrom(tokenTransferInfo.getStr("from_address"));
                model.setTo(tokenTransferInfo.getStr("to_address"));
            } else if (resultObj.getJSONObject("contractData").containsKey("tokenInfo")) {
                JSONObject contractData = resultObj.getJSONObject("contractData");
                JSONObject tokenInfo = contractData.getJSONObject("tokenInfo");
                model.setSymbol(tokenInfo.getStr("tokenAbbr"));
                model.setAmount(tokenInfo.getBigDecimal("amount"));
                model.setFrom(contractData.getStr("from_address"));
                model.setTo(contractData.getStr("to_address"));
            }
            return model;
        } catch (Exception e) {
            logger.info("TRON交易查询: {}  {}", hash, e.getMessage());
            return null;
        } finally {
            semaphore.release();
        }

    }


    /**
     * 获取地址的信息
     */
    public static TronAccountResponse getAccount(String address) {
        try {
            String url = "https://apilist.tronscanapi.com/api/accountv2?address=" + address;
            logger.info("请求：{}", url);
            String result = HttpRequest.get(url).header("TRON-PRO-API-KEY", TRON_KEY()).execute().body();
            logger.info("返回：{}", result);
            JSONObject resultObj = JSONUtil.parseObj(result);
            TronAccountResponse response = new TronAccountResponse();
            response.setValidate(resultObj.containsKey("address"));
            response.setActivated(resultObj.containsKey("activated") && resultObj.getBool("activated"));
            if (resultObj.containsKey("bandwidth")) {
                JSONObject bandwidth = resultObj.getJSONObject("bandwidth");
                response.setEnergyLimit(bandwidth.containsKey("energyLimit") ? bandwidth.getInt("energyLimit") : 0);
                response.setEnergyPercentage(bandwidth.containsKey("energyPercentage") ? bandwidth.getDouble("energyPercentage") : 1);
            } else {
                response.setEnergyLimit(0);
                response.setEnergyPercentage(0);
            }
            response.setEnableEnergy((int) Math.floor(response.getEnergyLimit() * (1 - response.getEnergyPercentage())));
            return response;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取价格
     *
     * @param tronToken
     * @return
     */
    public static BigDecimal getPrice(TronToken tronToken) {
        String url = "https://apilist.tronscanapi.com/api/token/price?token=" + tronToken.getToken();
        String body = HttpRequest.get(url).header("TRON-PRO-API-KEY", TRON_KEY()).execute().body();
        if (body == null) {
            return BigDecimal.ZERO;
        }
        JSONObject response = JSONUtil.parseObj(body);
        logger.info("获取代币汇率 : {}", response.toStringPretty());
        if (!response.containsKey("price_in_trx")) {
            return BigDecimal.ZERO;
        }
        return response.getBigDecimal("price_in_trx").setScale(6, RoundingMode.DOWN);
    }

    /**
     * 获取账户余额
     *
     * @param address
     * @return
     */
    public static TronBalance getBalance(String address) {
        try {
            semaphore.acquire();
            String url = "https://apilist.tronscanapi.com/api/accountv2?address=" + address;
            String body = HttpRequest.get(url).header("TRON-PRO-API-KEY", TRON_KEY()).execute().body();
            JSONObject response = JSONUtil.parseObj(body);
            if (!response.containsKey("withPriceTokens")) {
                return new TronBalance(BigDecimal.ZERO, BigDecimal.ZERO);
            }
            BigDecimal trx = BigDecimal.ZERO;
            BigDecimal usdt = BigDecimal.ZERO;
            JSONArray withPriceTokens = response.getJSONArray("withPriceTokens");
            for (Object data : withPriceTokens) {
                JSONObject dataJson = (JSONObject) data;
                if ("trx".equals(dataJson.getStr("tokenAbbr"))) {
                    trx = dataJson.getBigDecimal("balance");
                }
                if ("USDT".equals(dataJson.getStr("tokenAbbr"))) {
                    usdt = dataJson.getBigDecimal("balance");
                }
            }
            logger.info("获取账户余额 {} 返回 : TRX-{}\tUSDT-{}", address, trx.toPlainString(), usdt.toPlainString());
            return new TronBalance(trx, usdt);
        } catch (InterruptedException e) {
            return new TronBalance(BigDecimal.ZERO, BigDecimal.ZERO);
        } finally {
            semaphore.release();
        }
    }


    /**
     * 判断地址有效性
     *
     * @param address
     * @return
     */
    public static boolean isValid(String address) {
        String result = HttpRequest.get("https://api.trongrid.io/v1/accounts/" + address).execute().body();
        if (StringUtil.isEmpty(result)) {
            return false;
        }
        JSONObject resultObj = JSONUtil.parseObj(result);
        if (!resultObj.containsKey("success")) {
            return false;
        }
        return resultObj.getBool("success");
    }

    /**
     * 判断地址有效性
     *
     * @param address
     * @return
     */
    public static boolean validateAddress(String address) {
        String result = HttpRequest.get("https://api.trongrid.io/wallet/validateaddress?address=" + address)
                .execute().body();
        if (StringUtil.isEmpty(result)) {
            return false;
        }
        JSONObject resultObj = JSONUtil.parseObj(result);
        if (!resultObj.containsKey("result")) {
            return false;
        }
        return resultObj.getBool("result");
    }


    /**
     * 判断地址的安全性
     */
    public static CommonResult securityAccount(String address) {
        try {
            String url = "https://apilist.tronscanapi.com/api/security/account/data?address=" + address;
            String result = HttpRequest.get(url).header("TRON-PRO-API-KEY", TRON_KEY()).execute().body();
            JSONObject resultObj = JSONUtil.parseObj(result);
            //账户是否频繁发送广告
            if (resultObj.containsKey("send_ad_by_memo") && resultObj.getBool("send_ad_by_memo")) {
                return CommonResult.error("账户频繁发送广告");
            }
            //账户是否存在欺诈交易
            if (resultObj.containsKey("has_fraud_transaction") && resultObj.getBool("has_fraud_transaction")) {
                return CommonResult.error("账户存在欺诈交易");
            }
            //账户是否是欺诈代币的创建者
            if (resultObj.containsKey("fraud_token_creator") && resultObj.getBool("fraud_token_creator")) {
                return CommonResult.error("账户是欺诈代币的创建者");
            }
            //账户在稳定币黑名单中
            if (resultObj.containsKey("is_black_list") && resultObj.getBool("is_black_list")) {
                return CommonResult.error("账户在稳定币黑名单中");
            }
            return CommonResult.success();
        } catch (Exception e) {
            return CommonResult.success();
        }
    }
}
