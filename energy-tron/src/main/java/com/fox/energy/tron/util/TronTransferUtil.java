package com.fox.energy.tron.util;

import cn.hutool.json.JSONObject;
import com.fox.energy.common.core.domain.CommonResult;
import com.fox.energy.common.core.domain.tron.TronAddressActivateResponse;
import com.fox.energy.common.utils.spring.SpringUtils;
import com.fox.energy.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.contract.Contract;
import org.tron.trident.core.contract.Trc20Contract;
import org.tron.trident.core.exceptions.IllegalException;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Response;

import java.math.BigDecimal;

public class TronTransferUtil {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TronTransferUtil.class);

    private static final ISysConfigService sysConfigService = SpringUtils.getBean(ISysConfigService.class);


    public static TronAddressActivateResponse activate(String address) {
        ApiWrapper apiWrapper = getApiWrapper();
        try {
            String base58CheckAddress = apiWrapper.keyPair.toBase58CheckAddress();
            logger.info("激活地址 {} 主地址 {}", address, base58CheckAddress);
            Response.TransactionExtention transaction = apiWrapper.createAccount(base58CheckAddress, address);
            Chain.Transaction signedTxn = apiWrapper.signTransaction(transaction);
            logger.info("激活地址 {} 成功 {}", address, signedTxn);
            return new TronAddressActivateResponse(true, apiWrapper.broadcastTransaction(signedTxn));
        } catch (Exception e) {
            if (e.getMessage().contains("Account has existed")) {
                logger.info("激活地址 {} 已激活", address);
                return new TronAddressActivateResponse(true);
            }
            logger.info("激活地址 {} 失败", address);
            return new TronAddressActivateResponse(false);
        } finally {
            apiWrapper.close();
        }
    }


    /**
     * TRX转账
     */
    public static CommonResult transTRX(String privateKey, String toAddress, BigDecimal amount) {
        ApiWrapper apiWrapper = getApiWrapper(privateKey);
        try {
            logger.info("[{}] 向 [{}] 转账 {} TRX", TronAddressUtil.getAddressByPrivateKey(privateKey), toAddress, amount.toPlainString());
            Response.TransactionExtention transfer = apiWrapper.transfer(TronAddressUtil.getAddressByPrivateKey(privateKey), toAddress, amount.longValue());
            Chain.Transaction signedTxn = apiWrapper.signTransaction(transfer);
            String hash = apiWrapper.broadcastTransaction(signedTxn);
            logger.info("[{}] 向 [{}] 转账 {} TRX {}", TronAddressUtil.getAddressByPrivateKey(privateKey), toAddress, amount.toPlainString(), hash);
            JSONObject data = new JSONObject();
            data.set("hash", hash);
            return CommonResult.success(data);
        } catch (IllegalException e) {
            return CommonResult.error(e.getMessage());
        } finally {
            apiWrapper.close();
        }
    }

    public static CommonResult transUsdt(String privateKey, String toAddress, BigDecimal amount) {
        ApiWrapper apiWrapper = getApiWrapper(privateKey);
        try {
            Contract contract = apiWrapper.getContract("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
            logger.info("[{}] 向 [{}] 转账 {} USDT", apiWrapper.keyPair.toBase58CheckAddress(), toAddress, amount.toPlainString());
            Trc20Contract trc20Contract = new Trc20Contract(contract, apiWrapper.keyPair.toBase58CheckAddress(), apiWrapper);
            String hash = trc20Contract.transfer(toAddress, TronAmountUtil.up(amount).longValue(), 0, "", 50000000L);
            logger.info("[{}] 向 [{}] 转账 {} USDT {}", apiWrapper.keyPair.toBase58CheckAddress(), toAddress, amount.toPlainString(), hash);
            JSONObject data = new JSONObject();
            data.set("hash", hash);
            return CommonResult.success(data);
        } catch (Exception e) {
            logger.error("[{}] 向 [{}] 转账 {} USDT 异常 {}", apiWrapper.keyPair.toBase58CheckAddress(), toAddress, amount.toPlainString(), e.toString());
            return CommonResult.error(e.getMessage());
        } finally {
            apiWrapper.close();
        }
    }

    private static ApiWrapper getApiWrapper() {
        String hexPrivateKey = sysConfigService.selectConfigByKey("app.tron.privateKey");
        return getApiWrapper(hexPrivateKey);
    }

    private static ApiWrapper getApiWrapper(String hexPrivateKey) {
        String apiKey = sysConfigService.selectConfigByKey("app.trongrid.apiKey");
        return ApiWrapper.ofMainnet(hexPrivateKey, apiKey);
    }
}
