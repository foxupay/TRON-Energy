<template>
  <div style="min-height: 100%">
    <myheader ref="gaibian" class="gongyong"></myheader>
    <div style="height: 60px; width: 100%"></div>

    <div class="e2">
      <!-- 左侧 -->
      <div class="e3">
        <div class="e4">快捷交易</div>
        <div class="e5">能量秒到账</div>
        <div class="e6">租期更长，最长30天</div>
        <div class="e6">范围更大，支持3.2 万起租</div>
        <div class="e6">支持多币种支付购买</div>
        <div class="e6">支持DAPP支付和转账租赁</div>
        <div class="e6">可以预定大额能量</div>

        <div class="e7">
          剩余可租能量 <span style="color: #1f87ff"> {{ tab.max | wan }}</span>
        </div>
      </div>
      <!-- 右侧 -->
      <div class="e3">
        <div class="e8" style="box-shadow: 5px 5px 10px 0 rgba(23, 135, 255, 0.5)">
          <div class="e9">能量租赁</div>
          <div class="e10">
            <div>接收地址</div>
            <div
              v-if="chajianxianshi"
              style="color: #1f87ff; cursor: pointer"
              @click="getTronweb11"
            >
              连接钱包获取地址
            </div>
          </div>
          <div>
            <el-input v-model="receiveAddress" placeholder="请输入内容"></el-input>
          </div>
          <div class="e10">
            <div>资源租用数量</div>
            <div style="color: #646566; cursor: pointer">
              预估可转账USDT <span style="color: red">{{ ci }}</span> 次
            </div>
          </div>
          <div class="e11">
            <div class="e12" @click="jian">-</div>
            <div class="e13">
              <el-input
                @input="getinput"
                v-model="resourceValue"
                min="32000"
                type="number"
                oninput="if(value){value=value.replace(/[^\d]/g,1);
                                if(Number(value)<=32000){value=32000}} "
                placeholder="请输入数量"
              >
              </el-input>
            </div>

            <div class="e12" @click="jia">+</div>
          </div>
          <div class="e14">
            <div @click="zhurulinghun(32000)" class="e15">
              <span v-if="resourceValue !== 32000">3.2万</span>
              <span
                v-if="resourceValue === 32000"
                style="font-size: 16px; font-weight: 700; color: #1f87ff"
                >3.2万</span
              >
            </div>
            <div @click="zhurulinghun(65000)" class="e15-1">
              <span v-if="resourceValue !== 65000">6.5万</span>
              <span
                v-if="resourceValue === 65000"
                style="font-size: 16px; font-weight: 700; color: #1f87ff"
                >6.5万</span
              >
            </div>
            <div @click="zhurulinghun(100000)" class="e15-1">
              <span v-if="resourceValue !== 100000">10万</span>
              <span
                v-if="resourceValue === 100000"
                style="font-size: 16px; font-weight: 700; color: #1f87ff"
                >10万</span
              >
            </div>
            <div @click="zhurulinghun(1000000)" class="e15-1">
              <span v-if="resourceValue !== 1000000">100万</span>
              <span
                v-if="resourceValue === 1000000"
                style="font-size: 16px; font-weight: 700; color: #1f87ff"
                >100万</span
              >
            </div>
            <div @click="zhurulinghun(10000000)" class="e15-1">
              <span v-if="resourceValue !== 10000000">1000万</span>
              <span
                v-if="resourceValue === 10000000"
                style="font-size: 16px; font-weight: 700; color: #1f87ff"
                >1000万</span
              >
            </div>
          </div>

          <div class="e10">
            <div>资源租用时间</div>
          </div>

          <div class="e11">
            <div class="e13-1">
              <el-select
                style="width: 100%"
                @change="bianhua"
                v-model="leaseDurationzs"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in options"
                  :key="item.label"
                  :label="item.value"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
          </div>
          <div class="e14">
            <div @click="zhurushijian('1h', '1小时')" class="e15">
              <span v-if="leaseDuration !== '1h'">1小时</span>
              <span
                v-if="leaseDuration === '1h'"
                style="font-size: 16px; font-weight: 700; color: #1f87ff"
              >
                1小时
              </span>
            </div>
            <div @click="zhurushijian('1d', '1天')" class="e15-1">
              <span v-if="leaseDuration !== '1d'">1天</span>
              <span
                v-if="leaseDuration === '1d'"
                style="font-size: 16px; font-weight: 700; color: #1f87ff"
              >
                1天
              </span>
            </div>
            <div @click="zhurushijian('3d', '3天')" class="e15-1">
              <span v-if="leaseDuration !== '3d'">3天</span>
              <span
                v-if="leaseDuration === '3d'"
                style="font-size: 16px; font-weight: 700; color: #1f87ff"
              >
                3天
              </span>
            </div>
            <div @click="zhurushijian('7d', '7天')" class="e15-1">
              <span v-if="leaseDuration !== '7d'">7天</span>
              <span
                v-if="leaseDuration === '7d'"
                style="font-size: 16px; font-weight: 700; color: #1f87ff"
              >
                7天
              </span>
            </div>
            <div @click="zhurushijian('15d', '15天')" class="e15-1">
              <span v-if="leaseDuration !== '15d'">15天</span>
              <span
                v-if="leaseDuration === '15d'"
                style="font-size: 16px; font-weight: 700; color: #1f87ff"
              >
                15天
              </span>
            </div>
            <div @click="zhurushijian('30d', '30天')" class="e15-1">
              <span v-if="leaseDuration !== '30d'">30天</span>
              <span
                v-if="leaseDuration === '30d'"
                style="font-size: 16px; font-weight: 700; color: #1f87ff"
              >
                30天
              </span>
            </div>
          </div>
          <div v-if="yujishijian != ''" style="font-size: 12px; margin-top: 5px">
            预计
            <span style="color: #1f87ff">{{ yujishijian }}</span> 到期被系统回收
          </div>

          <div style="margin-top: 20px" v-if="leaseDuration != ''">
            <div style="margin-bottom: 10px; font-size: 16px">支付</div>
            <div class="e16">
              <div class="e17">订单信息</div>
              <div class="e18">{{ resourceValue | wan }}能量 , {{ leaseDurationzs }}</div>
            </div>
            <div class="e16">
              <div class="e17">支付</div>
              <div class="e18">
                <span style="text-decoration: line-through">{{ yingzhifu }}</span>
                <span style="font-size: 24px; font-weight: 700; color: #27c1a5"
                  >{{ zhifuxinxi }}TRX</span
                >
              </div>
            </div>

            <div class="e20">
              <div style="color: #27c1a5; font-size: 14px">
                单价 {{ danjia }} SUN 较 {{ leaseDurationzs }} 烧毁省 {{ zhekoujine }} ≈
                {{ jieshengjine }} TRX
              </div>
            </div>
          </div>

          <div class="e19" @click="zhifubuttom">支付</div>
        </div>
      </div>
    </div>

    <el-dialog
      title="详情"
      :visible.sync="centerDialogVisible"
      :width="kuan"
      center
      @close="guanbitc"
    >
      <div class="e16">
        <div class="e17">订单金额</div>
        <div class="e18">
          {{ zhifuxinxi }}
        </div>
      </div>
      <div class="e16">
        <div class="e17">{{ leaseDurationzs }}单价</div>
        <div v-if="leaseDuration == '1h'" class="e18">
          {{ tab.h1 }}
        </div>
        <div v-if="leaseDuration == '1d'" class="e18">
          {{ tab.h24 }}
        </div>
        <div v-if="leaseDuration == '3d'" class="e18">
          {{ tab.h72 }}
        </div>
        <div
          v-if="leaseDuration != '3d' && leaseDuration != '1d' && leaseDuration != '1h'"
          class="e18"
        >
          {{ tab.other }}
        </div>
      </div>
      <div class="e16">
        <div class="e17">订单时长</div>
        <div class="e18">
          {{ leaseDurationzs }}
        </div>
      </div>
      <div class="e16">
        <div class="e17">租用数量</div>
        <div class="e18">
          {{ resourceValue }}
        </div>
      </div>
      <div class="e16">
        <div class="e17">节 省</div>
        <div class="e18">
          {{ jieshengjine }}
        </div>
      </div>
      <div class="e16">
        <div class="e17">折 扣</div>
        <div class="e18">
          {{ zhekoujine }}
        </div>
      </div>
      <div class="e16">
        <div class="e17">接收地址</div>
        <div class="e18">
          {{ receiveAddress }}
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <div class="e21">
          <div
            class="e22"
            v-if="qianbaoxianshi"
            style="background: #4280f3"
            @click="qianbaozhifu(1)"
          >
            <div
              class="e24"
              style="
                display: flex;
                justify-content: center;
                align-items: center;
                border: 1px solid #fff;
                background: #4280f3;
              "
            >
              <div style="background: #4280f3; width: 20px; height: 20px">
                <img
                  style="width: 20px; height: 20px"
                  src="../assets/img/qb.png"
                  alt=""
                />
              </div>

              <!-- <i style="color:#e9831d ;font-size: 20px;text-align: center;line-height: 30px;"
                                class="el-icon-link"></i> -->
            </div>
            <div class="e23">
              <div>钱包支付</div>
              <div>连接钱包支付</div>
            </div>
          </div>
          <div
            v-if="dizhiyue > 0 && dizhiyue > zhifuxinxi"
            class="e22"
            style="background: #1f87ff"
            @click="qianbaozhifu(3)"
          >
            <div class="e24">
              <i
                style="
                  color: #1f87ff;
                  font-size: 20px;
                  text-align: center;
                  line-height: 30px;
                "
                class="el-icon-s-finance"
              ></i>
            </div>
            <div class="e23">
              <div>地址余额支付</div>
              <div>{{ dizhiyue }} TRX</div>
            </div>
            <div class="e25">推荐</div>
          </div>
          <div
            v-if="dizhiyue > 0 && zhifuxinxi > dizhiyue"
            class="e22"
            style="background: #9b9999"
          >
            <div class="e24">
              <i
                style="
                  color: #1f87ff;
                  font-size: 20px;
                  text-align: center;
                  line-height: 30px;
                "
                class="el-icon-s-finance"
              ></i>
            </div>
            <div class="e23">
              <div>地址余额支付</div>
              <div>{{ dizhiyue }} TRX</div>
            </div>
            <div class="e25">推荐</div>
          </div>
          <div
            v-if="cxxianshi && chaxunyue > zhifuxinxi"
            class="e22"
            style="background: #05c160"
            @click="qianbaozhifu(2)"
          >
            <div class="e24">
              <i
                style="
                  color: #05c160;
                  font-size: 20px;
                  text-align: center;
                  line-height: 30px;
                "
                class="el-icon-coin"
              ></i>
            </div>
            <div class="e23">
              <div>账户余额支付</div>
              <div>{{ chaxunyue }} TRX</div>
            </div>
          </div>
          <div
            v-if="cxxianshi && zhifuxinxi > chaxunyue"
            class="e22"
            style="background: #9b9999"
            @click="qianbaozhifu(2)"
          >
            <div class="e24">
              <i
                style="
                  color: #05c160;
                  font-size: 20px;
                  text-align: center;
                  line-height: 30px;
                "
                class="el-icon-coin"
              ></i>
            </div>
            <div class="e23">
              <div>账户余额支付</div>
              <div>{{ chaxunyue }} TRX</div>
            </div>
          </div>
          <div class="e22" style="background: #e9831d" @click="qianbaozhifu(4)">
            <div class="e24">
              <i
                style="
                  color: #e9831d;
                  font-size: 20px;
                  text-align: center;
                  line-height: 30px;
                "
                class="el-icon-link"
              ></i>
            </div>
            <div class="e23">
              <div>地址支付</div>
              <div>使用链上钱包进行转账支付</div>
            </div>
          </div>
        </div>
        <!-- <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button> -->
      </span>
    </el-dialog>

    <el-dialog
      title="地址支付"
      :visible.sync="centerDialogVisibledz"
      :width="dzkuan"
      center
      @close="guanbizf"
    >
      <div style="padding: 10px; border-radius: 10px; border: 1px solid #1f87ff">
        <div class="e26">· 请在有效的时间内支付</div>
        <div class="e26">· 到账金额需要与下方显示的金额一致，否则系统无法确认</div>
        <div class="e27">{{ dizhixinxi.trx }}TRX / {{ dizhixinxi.usdt }}USDT(TRC20)</div>
        <div class="e28">
          <div id="qrcode" ref="qrcode"></div>
        </div>
        <div class="e30">
          {{ dizhixinxi.address }}
        </div>
        <!-- <div>支付倒计时</div> -->
        <!-- format="mm分ss秒" -->

        <el-statistic
          @finish="hilarity"
          format="mm分ss秒"
          :value="time"
          time-indices
          title="支付倒计时"
        >
        </el-statistic>
        <div class="e29">监控支付中...</div>
      </div>
      <div
        class="e31"
        id="copyBtn"
        :data-clipboard-text="dizhixinxi.address"
        data-clipboard-action="copy"
        @click="copy"
      >
        复制收款地址
      </div>
      <div
        class="e31"
        id="copyBtn1"
        :data-clipboard-text="dizhixinxi.trx"
        data-clipboard-action="copy"
        @click="copy1"
      >
        复制支付TRX数量
      </div>
      <div
        class="e31"
        id="copyBtn2"
        :data-clipboard-text="dizhixinxi.usdt"
        data-clipboard-action="copy"
        @click="copy2"
      >
        复制支付USDT数量
      </div>

      <span slot="footer" class="dialog-footer">
        <!-- <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button> -->
      </span>
    </el-dialog>
  </div>
</template>
<script>
import Clipboard from "clipboard";
import QRCode from "qrcodejs2";
import myheader from "@/components/gongyong";
import qs from "qs";
export default {
  data() {
    return {
      windowWidth: document.body.clientWidth,
      kuan: "30%",
      timer: null,
      tab: {},
      receiveAddress: "", //租赁地址
      tronWebs: false,
      resourceValue: 32000, //租用不过数量
      ci: 1,
      options: [
        { value: "1小时", label: "1h" },
        { value: "1天", label: "1d" },
        { value: "3天", label: "3d" },
        { value: "4天", label: "4d" },
        { value: "5天", label: "5d" },
        { value: "6天", label: "6d" },
        { value: "7天", label: "7d" },
        { value: "8天", label: "8d" },
        { value: "9天", label: "9d" },
        { value: "10天", label: "10d" },
        { value: "11天", label: "11d" },
        { value: "12天", label: "12d" },
        { value: "13天", label: "13d" },
        { value: "14天", label: "14d" },
        { value: "15天", label: "15d" },
        { value: "16天", label: "16d" },
        { value: "17天", label: "17d" },
        { value: "18天", label: "18d" },
        { value: "19天", label: "19d" },
        { value: "20天", label: "20d" },
        { value: "21天", label: "21d" },
        { value: "22天", label: "22d" },
        { value: "23天", label: "23d" },
        { value: "24天", label: "24d" },
        { value: "25天", label: "25d" },
        { value: "26天", label: "26d" },
        { value: "27天", label: "27d" },
        { value: "28天", label: "28d" },
        { value: "29天", label: "29d" },
        { value: "30天", label: "30d" },
      ],
      leaseDuration: "1h", //租用时长
      leaseDurationzs: "1小时",
      yujishijian: "",
      zhifuxinxi: "", //订单金额
      yingzhifu: "", //应支付
      jieshengjine: "", //节省金额
      zhekoujine: "", //折扣金额
      danjia: "", //单价
      centerDialogVisible: false,
      create: {},
      transactionHash: "",
      centerDialogVisibledz: false,
      dzkuan: "30%",
      dizhixinxi: {},
      time: "",
      timerzt: null,
      orderNo: "",
      chaxunyue: "", //查询余额
      cxxianshi: false,
      dizhiyue: 0,
      chajianxianshi: false,
      qianbaoxianshi: false,
    };
  },
  components: {
    myheader,
  },
  watch: {
    // 监听页面宽度
    windowWidth(val) {
      if (val > 1200) {
        this.kuan = "30%";
        this.dzkuan = "30%";
      } else if (val > 699 && val <= 1200) {
        this.kuan = "50%";
        this.dzkuan = "50%";
      } else {
        this.kuan = "90%";
        this.dzkuan = "90%";
      }

      // console.log('实时屏幕宽度：', val, this.windowHeight)
    },
  },

  methods: {
    jianting() {
      var val = this.windowWidth;
      if (val > 1200) {
        this.kuan = "30%";
        this.dzkuan = "30%";
      } else if (val > 699 && val <= 1200) {
        this.kuan = "50%";
        this.dzkuan = "50%";
      } else {
        this.kuan = "90%";
        this.dzkuan = "90%";
      }
    },
    //复制
    copy() {
      let clipboard = new Clipboard("#copyBtn");
      clipboard.on("success", (e) => {
        this.$message({
          message: "复制成功",
          type: "success",
        });

        clipboard.destroy();
      });
      clipboard.on("error", (e) => {
        this.$message({
          message: "复制失败",
          type: "warning",
        });
        clipboard.destroy();
      });
    },
    copy1() {
      let clipboard = new Clipboard("#copyBtn1");
      clipboard.on("success", (e) => {
        this.$message({
          message: "复制成功",
          type: "success",
        });

        clipboard.destroy();
      });
      clipboard.on("error", (e) => {
        this.$message({
          message: "复制失败",
          type: "warning",
        });
        clipboard.destroy();
      });
    },
    copy2() {
      let clipboard = new Clipboard("#copyBtn2");
      clipboard.on("success", (e) => {
        this.$message({
          message: "复制成功",
          type: "success",
        });

        clipboard.destroy();
      });
      clipboard.on("error", (e) => {
        this.$message({
          message: "复制失败",
          type: "warning",
        });
        clipboard.destroy();
      });
    },
    guanbizf() {
      this.centerDialogVisibledz = false;
      clearInterval(this.timerzt);
      this.timerzt = null;
    },
    //倒计时结束
    hilarity() {
      this.centerDialogVisibledz = false;
      clearInterval(this.timerzt);
      this.timerzt = null;
    },
    //关闭弹窗
    guanbitc() {
      this.centerDialogVisible = false;
      this.create = {};
    },

    //点击钱包支付 跟地址支付
    qianbaozhifu(data) {
      const loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      });
      var datas = "";
      if (data == 4) {
        datas = 1;
      } else {
        datas = data;
      }
      this.$axios({
        url: "/api/app/lease/create",
        method: "post",
        //发送格式为json
        data: JSON.stringify({
          payWay: datas,
          receiveAddress: this.receiveAddress,
          resourceValue: this.resourceValue,
          leaseDuration: this.leaseDuration,
        }),
        headers: {
          "content-type": "application/json",
          Authorization: "Bearer" + " " + this.getCookie("nltoken"),
        },
      })
        .then((userData) => {
          if (userData.data.code === 200) {
            this.setcookie("receiveAddress", this.receiveAddress);
            loading.close();
            if (data == 1) {
              this.create = userData.data.data;
              this.sendTrx(userData.data.data.address, userData.data.data.trx);
            }
            if (data != 1 && data != 4) {
              this.$message({
                message: userData.data.message,
                type: "success",
              });
              this.centerDialogVisible = false;
              this.create = {};
              this.transactionHas = "";
            }
            if (data == 4) {
              this.dizhixinxi = userData.data.data;
              this.centerDialogVisibledz = true;
              this.centerDialogVisible = false;
              this.create = {};
              this.transactionHas = "";

              this.time = userData.data.data.expireTime;
              this.orderNo = userData.data.data.orderNo;
              setTimeout(() => {
                document.getElementById("qrcode").innerHTML = "";
                let qrcode = new QRCode(this.$refs.qrcode, {
                  width: 200, // 二维码宽度，单位像素
                  height: 200, // 二维码高度，单位像素
                  text: userData.data.data.address, // 生成二维码的链接
                });
              }, 500);
              this.timerzt = setInterval(() => {
                this.getzhifuzhuangtai();
              }, 2000);
            }
          } else {
            loading.close();

            this.$message.error(userData.data.msg);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //根据单号查询支付状态
    getzhifuzhuangtai() {
      var params = {
        orderNo: this.orderNo,
      };
      this.$axios({
        method: "GET",
        url: "/api/app/lease/query",
        params: params,
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          Authorization: this.getCookie("nltoken"),
        },
      })
        .then((userData) => {
          if (userData.data.code == "200") {
            if (userData.data.data.status == 1) {
            }
            if (userData.data.data.status == 2) {
              clearInterval(this.timerzt);
              this.timerzt = null;
              this.centerDialogVisibledz = false;
              this.$message({
                message: "支付成功",
                type: "success",
              });
            }
            if (userData.data.data.status == 3) {
              clearInterval(this.timerzt);
              this.timerzt = null;
              this.centerDialogVisibledz = false;
              this.$message({
                message: "支付取消",
                type: "warning",
              });
            }
          } else {
            clearInterval(this.timerzt);
            this.$message.error(userData.data.msg);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //判断链接方法
    //判断是否已链接TronLink钱包
    async connectTronLink() {
      if (!window.tronLink) {
        this.$message.warning("请先连接 TronLink 钱包");
        return false;
      }
      if (!window.tronLink.ready) {
        try {
          await tron.request({ method: "eth_requestAccounts" });
        } catch (e) {
          // console.info("rese:", e)
          if (e.code === -32000) {
            this.$message.warning("连接操作频繁，请手动连接钱包");
          } else if (e.code === 4001) {
            this.$message.warning("连接钱包被取消");
          } else {
            this.$message.warning(e.message);
          }
          return false;
        }
      }
      return true;
    },
    //链接插件获取地址
    async sendTrx(address, num) {
      const loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      });

      if (!(await this.connectTronLink())) {
        loading.close();
        return;
      }
      const fromAddress = tronLink.tronWeb.defaultAddress.base58;
      const toAddress = address; // 替换为目标地址
      const amount = num * 1000000; // TRX数量，以Sun（TRX的小数单位）为单位

      try {
        const transaction = await tronLink.tronWeb.transactionBuilder.sendTrx(
          toAddress,
          amount,
          fromAddress
        ); // 步骤1
        // const transaction = await tronLink.tronWeb.transactionBuilder.sendTrx(toAddress, amount, {
        //     shouldPollForConfirmation: true
        // });
        // console.info(transaction)

        const signedTransaction = await tronLink.tronWeb.trx.sign(transaction);
        const transactionHash = await tronLink.tronWeb.trx.sendRawTransaction(
          signedTransaction
        );

        if (transactionHash.result == true) {
          this.transactionHash = transactionHash.txid;
          this.timer = setInterval(() => {
            this.gethash();
          }, 2000);
        } else {
          this.$message.error("交易失败");
          loading.close();
        }

        // console.info(transactionHash)
        // alert(`交易哈希：${transactionHash}`);
      } catch (error) {
        this.$message.error("交易失败");

        loading.close();
      }
    },
    //回去支付状态
    gethash() {
      const loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      });
      var params = {
        hash: this.transactionHash,
      };
      this.$axios({
        method: "GET",
        url: "/api/app/lease/hash",
        params: params,
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          Authorization: this.getCookie("nltoken"),
        },
      })
        .then((userData) => {
          if (userData.data.code == "200") {
            if (userData.data.data.status == 2) {
              clearInterval(this.timer);
              this.timer = null;
              this.centerDialogVisible = false;
              this.create = {};
              this.transactionHas = "";
              this.$message({
                message: "支付成功",
                type: "success",
              });
              loading.close();
            }
            if (userData.data.data.status == 3) {
              clearInterval(this.timer);
              this.timer = null;
              this.centerDialogVisible = false;
              this.create = {};
              this.transactionHas = "";
              this.$message({
                message: "支付取消",
                type: "warning",
              });
              loading.close();
            }
          } else {
            loading.close();
            this.$message.error(userData.data.msg);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //支付按钮
    zhifubuttom() {
      if (this.receiveAddress == "") {
        this.$message.error("请输入接收地址");
        return false;
      }
      if (this.leaseDuration == "") {
        this.$message.error("请选择租用时间");
        return false;
      }
      if (this.getCookie("nltoken") != "") {
        this.cxxianshi = true;
        this.getcxyue();
      }
      this.getdizhiyue();

      this.centerDialogVisible = true;
    },
    //查询余额
    getcxyue() {
      this.$axios
        .post("/api/app/user/info/balance", null, {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            Authorization: this.getCookie("nltoken"),
          },
        })
        .then((userData) => {
          if (userData.data.code == "200") {
            this.chaxunyue = userData.data.data.balance;
          } else {
            this.loading = false;
            this.$message.error(userData.data.msg);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //查询地址余额
    getdizhiyue() {
      var params = {
        address: this.receiveAddress,
      };
      this.$axios({
        method: "GET",
        url: "/api/app/user/info/balance/address",
        params: params,
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          Authorization: this.getCookie("nltoken"),
        },
      })
        .then((userData) => {
          if (userData.data.code == "200") {
            this.dizhiyue = userData.data.data.balance;
          } else {
            this.$message.error(userData.data.msg);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },

    //计算订单金额
    getqian(time) {
      if (time == "1h") {
        this.zhifuxinxi =
          Math.ceil(((this.tab.h1 * Number(this.resourceValue)) / 1000000) * 100) / 100;
        this.yingzhifu =
          Math.ceil((Number(this.resourceValue) / 32000) * 13.44 * 1 * 100) / 100;
        this.danjia = this.tab.h1;
      } else if (time == "1d") {
        this.zhifuxinxi =
          Math.ceil(((this.tab.h24 * Number(this.resourceValue)) / 1000000) * 100) / 100;
        this.yingzhifu =
          Math.ceil((Number(this.resourceValue) / 32000) * 13.44 * 1 * 100) / 100;
        this.danjia = this.tab.h24;
      } else if (time == "3d") {
        this.zhifuxinxi =
          Math.ceil(((this.tab.h72 * Number(this.resourceValue) * 3) / 1000000) * 100) /
          100;
        this.yingzhifu =
          Math.ceil((Number(this.resourceValue) / 32000) * 13.44 * 3 * 100) / 100;
        this.danjia = this.tab.h72;
      } else {
        var num = Number(time.substring(0, time.length - 1));
        this.zhifuxinxi =
          Math.ceil(
            ((this.tab.other * Number(this.resourceValue) * num) / 1000000) * 100
          ) / 100;
        this.yingzhifu =
          Math.ceil((Number(this.resourceValue) / 32000) * 13.44 * num * 100) / 100;
        this.danjia = this.tab.other;
      }

      this.jieshengjine =
        Math.ceil((Number(this.yingzhifu) - Number(this.zhifuxinxi)) * 100) / 100;
      this.zhekoujine =
        (Math.floor((Number(this.jieshengjine) / Number(this.yingzhifu)) * 100) / 100) *
          100 +
        "%";
    },

    //日期多加一天的方法
    // date为相加前的时间， days 为 需要相加的天数
    addDate(days) {
      var curTime = new Date();
      if (days == "1h") {
        var addHour = curTime.setHours(curTime.getHours() + 1);
      } else {
        var t = Number(days.slice(0, 1));
        var addHour = curTime.setHours(curTime.getHours() + t * 24);
      }
      var date = new Date(addHour);
      var year = date.getFullYear(); // 年
      var month = date.getMonth() + 1; // 月
      var day = date.getDate();
      var hour = date.getHours(); //获取当前小时数(0-23)
      var minute = date.getMinutes(); //获取当前分钟数(0-59)
      var time =
        year +
        "-" +
        this.fillZero(month) +
        "-" +
        this.fillZero(day) +
        " " +
        this.fillZero(hour) +
        ":" +
        this.fillZero(minute);
      return time;
    },
    // 给时间补零
    fillZero(str) {
      var realNum;
      if (str < 10) {
        realNum = "0" + str;
      } else {
        realNum = str;
      }
      return realNum;
    },

    //下拉选中
    bianhua(val) {
      this.leaseDurationzs = val;
      if (val == "1小时") {
        this.leaseDuration = "1h";
      } else {
        this.leaseDuration = val.slice(0, 1) + "d";
      }

      this.yujishijian = this.addDate(val);
      this.getqian(this.leaseDuration);
    },
    //手动点击
    zhurushijian(num, data) {
      this.leaseDurationzs = data;
      this.leaseDuration = num;
      this.yujishijian = this.addDate(num);
      this.getqian(num);
    },
    getinput(val) {
      this.ci = Math.floor(val / 32000);
    },
    zhurulinghun(num) {
      this.ci = Math.floor(Number(num) / 32000);
      this.resourceValue = num;
      this.getqian(this.leaseDuration);
    },
    jia() {
      this.resourceValue = Number(this.resourceValue) + 32000;
      this.ci = Math.floor(Number(this.resourceValue) / 32000);
      this.getqian(this.leaseDuration);
    },
    jian() {
      if (Number(this.resourceValue) <= 32000) {
        return false;
      } else {
        this.resourceValue = Number(this.resourceValue) - 32000;
        this.ci = Math.floor(Number(this.resourceValue) / 32000);
        this.getqian(this.leaseDuration);
      }
    },
    //获取钱包地址
    async getTronweb11() {
      if (await this.connectTronLink()) {
        this.receiveAddress = tronLink.tronWeb.defaultAddress.base58;
        this.chajianxianshi = false;
        this.qianbaoxianshi = true;
      }
    },

    getpeizhi() {
      
      this.$axios({
        method: "GET",
        url: "/api/app/lease/config",
        // params: params,
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      })
        .then((userData) => {
          if (userData.data.code == "200") {
            this.tab = userData.data.data;
            this.getqian(this.leaseDuration);
          } else {
            this.$message.error(userData.data.msg);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  mounted() {
    this.$refs.gaibian.genggai("3");
    window.onresize = () => {
      return (() => {
        this.windowWidth = document.body.clientWidth; // 宽
      })();
    };
  },
  beforeDestroy() {
    clearInterval(this.timer);
    this.timer = null;
  },
  created: function () {
    var that = this;
    that.jianting();

    setTimeout(() => {
      if (window.tronLink) {
        that.chajianxianshi = true;
        if (tronLink.tronWeb) {
          that.receiveAddress = tronLink.tronWeb.defaultAddress.base58;
          that.chajianxianshi = false;
          that.qianbaoxianshi = true;
        }
      }
      if (
        that.getCookie("receiveAddress") != "" &&
        that.getCookie("receiveAddress") != 0 &&
        that.getCookie("receiveAddress") != undefined
      ) {
        that.receiveAddress = that.getCookie("receiveAddress");
      }
    }, 2000);

    window.addEventListener("message", function (e) {
      if (e.data.message && e.data.message.action === "accountsChanged") {
        that.chajianxianshi = true;
      }
      if (e.data.message && e.data.message.action === "connect") {
        that.receiveAddress = tronLink.tronWeb.defaultAddress.base58;
        that.chajianxianshi = false;
        that.qianbaoxianshi = true;
      }
    });

    that.getpeizhi();
    that.yujishijian = that.addDate("1h");
  },
  filters: {
    wan: function (data) {
      return Math.floor(data / 100) / 100 + "W";
    },
  },
};
</script>
<style scoped>
.gongyong {
  position: fixed;
  width: 100%;
  left: 0;
  top: 0;
  z-index: 100;
}

.e2 {
  margin-top: 100px;
  width: 100%;
  display: flex;
  justify-content: space-around;
  padding: 10px 0;
}

.e3 {
  width: 40%;
  margin-left: 5%;
}

.e4 {
  font-size: 90px;
  font-weight: 800;

  color: #1f87ff;
}

.e5 {
  font-size: 48px;
  font-weight: 600;

  color: #1f87ff;
  padding: 30px 0;
}

.e6 {
  font-size: 20px;
  color: #646566;
  margin-bottom: 15px;
}

.e7 {
  font-size: 20px;
  color: #646566;

  padding-top: 30px;
}

.e8 {
  width: 60%;
  background: rgba(23, 135, 255, 0.1);
  border-radius: 20px;
  padding: 20px;
}

.e9 {
  font-size: 30px;
  font-weight: 600;
}

.e10 {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 15px;
  padding: 10px 0;
  font-size: 14px;
}

.e11 {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.e12 {
  width: 30px;
  height: 30px;
  /* border-radius: 3px; */
  cursor: pointer;
  text-align: center;
  line-height: 30px;
  background: #fff;
}

.e13 {
  height: 32px;
  flex: 1;
}

.e14 {
  cursor: pointer;
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  height: 30px;
  border: 1px solid #1f87ff;
  border-radius: 15px;
  margin-top: 5px;
}

.e15 {
  font-size: 14px;
  flex: 1;
  color: #1f87ff;
  text-align: center;
}

.e15-1 {
  font-size: 14px;
  flex: 1;
  color: #1f87ff;
  text-align: center;
  border-left: 1px solid #1f87ff;
}
.e13-1 {
  height: 32px;
  flex: 1;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  background: #fff;
}

.e16 {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 12px;
  padding: 3px;
}

.e17 {
  width: 100px;
  font-size: 16px;
}

.e18 {
  flex: 1;
  width: 0;
  text-align: right;
  font-size: 16px;
}

.e19 {
  width: 100%;
  height: 40px;
  line-height: 40px;
  background: #1f87ff;
  color: #fff;
  text-align: center;
  border-radius: 5px;
  margin-top: 20px;
}

.e20 {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding: 5px 0;
  color: #1f87ff;
  font-size: 12px;
}

.e21 {
  width: 80%;
  margin: 0 auto;
}

.e22 {
  height: 45px;
  font-size: 14px;
  color: #fff;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  border-radius: 3px;
  margin-bottom: 10px;
}

.e23 {
  flex: 1;
  text-align: left;
  margin-left: 10px;
}

.e24 {
  width: 30px;
  height: 30px;
  background: #fff;
  border-radius: 15px;
  margin-left: 10px;
}

.e25 {
  margin-right: 10px;
  padding: 5px 6px;
  background: #fff;
  font-size: 12px;
  color: #d43030;
}

.e26 {
  font-size: 12px;
  color: #1f87ff;
  padding: 5px 0;
}

.e27 {
  text-align: center;
  font-size: 18px;
  padding: 20px 0;
}

.e28 {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px 0;
}

.e29 {
  text-align: center;
  color: #f3455a;
  padding: 10px 0;
}

.e30 {
  background: #f2f8ff;
  padding: 10px 0;
  text-align: center;
}

.e31 {
  width: 100%;

  height: 40px;
  line-height: 40px;
  color: #fff;
  text-align: center;
  border-radius: 20px;
  background: #1f87ff;
  margin-top: 20px;
}

@media screen and (max-width: 699px) {
  .e2 {
    margin-top: 30px;
    width: 100%;
    padding: 10px 0;
    display: flex;
    flex-direction: column;
  }

  .e3 {
    width: 100%;
    text-align: center;
    margin-left: 2%;
  }

  .e4 {
    font-size: 50px;
    font-weight: 800;

    color: #1f87ff;
  }

  .e5 {
    font-size: 34px;
    font-weight: 600;

    color: #1f87ff;
    padding: 30px 0;
  }

  .e8 {
    margin-top: 50px;
    width: 96%;
    background: rgba(23, 135, 255, 0.1);
    border-radius: 20px;
    padding: 20px;
    margin-bottom: 100px;
  }
}

/* 平板  768px-992px  屏幕宽度在768px以上显示的颜色*/
@media screen and (min-width: 700px) and (max-width: 1200px) {
  .e2 {
    margin-top: 100px;
    width: 100%;
    padding: 10px 0;
    display: flex;
    justify-content: space-around;
  }

  .e3 {
    width: 45%;
  }

  .e4 {
    font-size: 70px;
    font-weight: 800;

    color: #1f87ff;
  }

  .e5 {
    font-size: 40px;
    font-weight: 600;

    color: #1f87ff;
    padding: 30px 0;
  }

  .e8 {
    width: 80%;
    margin-left: 2%;
    background: rgba(23, 135, 255, 0.1);
    border-radius: 20px;
    padding: 20px;
    margin-bottom: 100px;
  }
}

@media screen and (min-width: 1201px) {
}
</style>
