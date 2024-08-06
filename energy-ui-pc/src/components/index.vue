<template>
  <div style="background: #f9fcff">
    <myheader ref="gaibian" class="gongyong"></myheader>
    <div style="height: 60px; width: 100%"></div>
    <div class="b1">TRON 能量交易平台</div>
    <div class="b2">便宜、 便捷、 快速、 高效、 安全</div>
    <div class="b3">
      TRX能量租赁是一个TRON
      能量交易平台，我们提供TRON能量交易服务，让TRON能量交易更加便捷、安全、快速
    </div>
    <div class="b4" id="top">
      <div class="b5">
        <div class="b6">平台下单</div>
        <div class="b7">
          <div class="b9">
            <img
              style="width: 150px; height: 100px"
              src="../assets/img/che.jpg"
              alt=""
            />
          </div>

          <div class="b8">
            <div
              style="
                width: 10px;
                height: 10px;
                background: #1787ff;
                border-radius: 50%;
              "
            ></div>
            <div class="b11">操作简单</div>
          </div>
          <div class="b8">
            <div
              style="
                width: 10px;
                height: 10px;
                background: #1787ff;
                border-radius: 50%;
              "
            ></div>
            <div class="b11">数据详细</div>
          </div>
          <div class="b8">
            <div
              style="
                width: 10px;
                height: 10px;
                background: #1787ff;
                border-radius: 50%;
              "
            ></div>
            <div class="b11">支持扫码付款</div>
          </div>
          <div class="b8">
            <div
              style="
                width: 10px;
                height: 10px;
                background: #1787ff;
                border-radius: 50%;
              "
            ></div>
            <div class="b11">Dapp付款</div>
          </div>
        </div>
        <div class="b12" @click="goxiadan">前往下单</div>
      </div>

      <div class="b5">
        <div class="b6">API下单</div>
        <div class="b7">
          <div class="b9">
            <img
              style="width: 150px; height: 100px"
              src="../assets/img/api.png"
              alt=""
            />
          </div>

          <div class="b8">
            <div
              style="
                width: 10px;
                height: 10px;
                background: #1787ff;
                border-radius: 50%;
              "
            ></div>
            <div class="b11">对接平台API</div>
          </div>
          <div class="b8">
            <div
              style="
                width: 10px;
                height: 10px;
                background: #1787ff;
                border-radius: 50%;
              "
            ></div>
            <div class="b11">下单更高效</div>
          </div>
        </div>
        <div class="b12" @click="apixiadan">前往下单</div>
      </div>
    </div>

    <div class="b13">
      <el-collapse accordion>
        <el-collapse-item>
          <template slot="title">
            <div class="b14">什么是能量？能量是免费的吗？</div>
          </template>
          <div>
            TRON网络中的资源有3种:带宽,能量,存储。得益于波场独有的设计模型,TRON网络中的存储资源几乎是无限的。
            TRON网络引入了Bandwidth Point 和 Energy
            两种可以进行量化的资源。其中Bandwidth
            Point表示带宽资源,Energy表示能量资源。
          </div>
          <div>
            我们在比特币、以太坊上转账时需要通过 BTC、ETH
            来支付手续费。在波场进行转账时消耗的是带宽和能量。 BTC, ETH, and TRX
            are all main network tokens, and tokens created based on these main
            networks are equivalent to renting in the home of the main network
            token landlord, and the method of paying the transaction fee is
            determined by the main network landlord.
          </div>
          <div>
            比特币和以太坊立下的规矩是,代币转账需要用 BTC 和 ETH
            来支付,而波场立下的规矩则是用能量和带宽来支付。
            波场上有两类常见租客(两类常见的代币格式):TRC-10 和 TRC-20。转账 TRX
            和 TRC-10 代币时需要消耗带宽,转账 TRC-20 代币时需要消耗带宽和能量。
          </div>
          <div>
            如果你不知道代币格式,可以在资产首页点击该代币,进入交易记录界面后再点击右上角,进入
            TRONSCAN 即可查看其代币格式,例如波场上的 USDT 就是 TRC-20 格式
          </div>
          <div>
            波场账户能量不是免费的,需要冻结TRX获取能量。也可以向能量交易平台租能量。假如账户中有100万能量,使用完毕后,会慢慢恢复,24小时恢复满100万。冻结(租用)默认最少1小时,
            当1小时后账户取消冻结(租用到期)时,通过冻结(租用)获得的能量也将自动清零。
          </div>
        </el-collapse-item>
        <el-collapse-item>
          <template slot="title">
            <div class="b14">能量有什么用？</div>
          </template>
          <div>
            能量主要可在以下几种场景用于消耗(以下消耗数据为链上数据概揽,为大概数据,实际准确消耗数据需操作之后区块浏览器查询所得数据。
          </div>
          <div>TRC20 消耗</div>
          <div>
            TRC20代币,消耗带宽和能量,例如USDT是20代币,转账一笔需消耗31895能量和345带宽,需要抵扣能量13.39TRX和带宽0.345TRX左右。
          </div>
          <div>智能合约 消耗</div>
          <div>
            调用智能合约,需消耗带宽和能量。例如SUNSWAP买卖交易,均是智能合约交互,需消耗账户中的能量和带宽。
          </div>
        </el-collapse-item>
        <el-collapse-item>
          <template slot="title">
            <div class="b14">如何获得能量？</div>
          </template>
          <div>方法一:燃烧 TRX</div>
          <div>
            如果账号没有足够能量,转账时会自动燃烧 TRX
            以抵扣转账所需的带宽和能量。相比消耗能量,燃烧TRX显然并不划算。
          </div>
          <div>方法二:冻结 TRX</div>
          <div>
            点击资产首页的能量 / 带宽,进入 Tron
            资源管理界面,选择想获得的资源类型,输入需冻结的 TRX 数量,获得的能量 =
            冻结的TRX / 全网获得能量冻结的总TRX *
            总能量限制。(这是基于冻结的TRX为所有用户平均分配的固定能量),冻结后的
            TRX 在 3 天后可以解冻取回。如果您有大量闲置TRX,可以考虑这种方法。
          </div>
          <div>方法三:租用能量</div>
          <div>
            可以通过本平台进行能量租赁的方式来节省手续费,能量租赁既不需要冻结大量TRX,也不用燃烧更多TRX,还可节省至少80%的手续费,以租用100万能量为例,若直接燃烧TRX,3天需要燃烧大约1266TRX,使用用本平台租用100万能量只需要120TRX左右,节省了90%≈1140TRX。
          </div>
        </el-collapse-item>
        <el-collapse-item>
          <template slot="title">
            <div class="b14">如何租用能量？</div>
          </template>
          <div style="display: flex; justify-content: flex-start">
            可以通过本平台去 <a href="#top" style="color: #1787ff">租用能量</a>
          </div>
        </el-collapse-item>
        <el-collapse-item>
          <template slot="title">
            <div class="b14">支付成功后，能量什么时候到账？</div>
          </template>
          <div>
            付款完成后,能量最快3秒内到账。由于广播延迟等原因,极少数情况,会存在5到10分钟的延迟。
          </div>
        </el-collapse-item>
        <el-collapse-item>
          <template slot="title">
            <div class="b14">能量使用后多久能恢复满？</div>
          </template>
          <div>能量使用后将会在24小时内恢复满。</div>
        </el-collapse-item>
        <el-collapse-item>
          <template slot="title">
            <div class="b14">订单可以撤回吗？</div>
          </template>
          <div>订单生成后,暂不支持撤回</div>
        </el-collapse-item>
      </el-collapse>
    </div>

    <div class="f1">
      <div style="width: 50px; height: 60px; text-align: center" @click="goqun">
        <img
          style="width: 40px; height: 40px"
          src="../assets/img/qun.png"
          alt=""
        />
        <div style="font-size: 12px; color: #1787ff; text-align: center">
          tel能量群
        </div>
      </div>
      <div
        style="width: 50px; height: 60px; text-align: center"
        id="copyBtn"
        data-clipboard-text="admin@foxupay.com"
        data-clipboard-action="copy"
        @click="copy"
      >
        <img
          style="width: 40px; height: 40px"
          src="../assets/img/kf.png"
          alt=""
        />
        <div style="font-size: 12px; color: #1787ff; text-align: center">
          客服
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Clipboard from "clipboard";
import myheader from "@/components/gongyong";
import qs from "qs";
export default {
  data() {
    return {
      activeNames: ["0"],
    };
  },
  components: {
    myheader,
  },
  mounted() {
    this.$refs.gaibian.genggai("1");
  },
  methods: {
    apixiadan() {
      window.open("https://www.foxupay.com/ENERGY_API.html");
    },
    goqun() {
      window.open("https://t.me/+Ry8pEQ1RIKczNjkx");
    },
    iosxiazai() {
      window.open(
        "https://apps.apple.com/us/app/trx%E8%83%BD%E9%87%8F%E7%A7%9F%E8%B5%81/id6503272045"
      );
    },
    anzhuoxiazai() {
      window.open("https://www.foxupay.com/app/energy.apk");
    },
    //复制
    copy() {
      let clipboard = new Clipboard("#copyBtn");
      clipboard.on("success", (e) => {
        this.$message({
          message: "复制邮箱成功",
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

    goxiadan() {
      this.$router.push({
        name: "energy",
      });
    },
  },

  created: function () {},
  filters: {
    zhuangtai: function (data) {
      if (data == "1") {
        return "待支付";
      }
      if (data == "2") {
        return "支付完成";
      }
      if (data == "3") {
        return "取消支付";
      }
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
  z-index: 100000;
}

.f1 {
  position: fixed;
  right: 20px;
  bottom: 200px;
  width: 60px;
  height: 150px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  z-index: 10000000;
}

.b1 {
  font-size: 40px;
  font-weight: 600;
  color: #1787ff;
  text-align: center;
  padding: 30px;
}

.b2 {
  font-size: 40px;
  font-weight: 600;
  color: #1787ff;
  text-align: center;
}

.b3 {
  font-size: 18px;
  font-weight: 600;
  color: #888989;
  text-align: center;
  padding: 30px;
}

.b4 {
  width: 70%;
  margin: 0 auto;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.b5 {
  width: 48%;
  background: #fff;
  height: 450px;
  border-radius: 20px;
  border: 1px #f4f4f4 solid;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}
.b5:hover {
  box-shadow: 0 2px 12px 0 #409eff;
}

.b6 {
  padding: 20px;
  font-size: 20px;
  font-weight: 550;
  color: #1787ff;
  border-bottom: 1px solid #f4f4f4;
}

.b7 {
  flex: 1;
}

.b8 {
  display: flex;
  justify-content: flex-start;
  padding: 10px;
  align-items: center;
}

.b9 {
  text-align: center;
  padding: 20px;
}

/* #b10 {
        font-size: 100px;
        color: #f58822;
    } */

.b11 {
  color: #646566;
  margin-left: 10px;
}

.b12 {
  width: 90%;
  margin-left: 5%;
  margin-bottom: 20px;
  height: 30px;
  line-height: 30px;
  text-align: center;
  color: #fff;
  background: #1787ff;
  border-radius: 15px;
  font-size: 14px;
  cursor: pointer;
}

.b13 {
  width: 70%;
  margin: 0 auto;
  background: #fff;
  border-radius: 20px;
  padding: 30px;
  margin-top: 80px;
}

.b14 {
  font-size: 20px;
  font-weight: 550;
  color: #1787ff;
}

@media screen and (max-width: 699px) {
  .f1 {
    position: fixed;
    right: 0;
    bottom: 200px;
    width: 60px;
    height: 150px;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
    z-index: 100000;
  }

  .b1 {
    font-size: 30px;
    font-weight: 600;
    color: #1787ff;
    text-align: center;
    padding: 30px;
  }

  .b2 {
    font-size: 30px;
    font-weight: 600;
    color: #1787ff;
    text-align: center;
  }

  .b3 {
    color: #909399;
    text-align: center;
    padding: 30px;
    font-size: 20px;
    font-weight: 600;
    margin: 20px 0px;
  }

  .b4 {
    width: 100%;
    margin: 0 auto;
    display: flex;
    justify-content: flex-start;
    flex-wrap: wrap;
    align-items: center;
  }

  .b5 {
    width: 80%;
    margin: 20px auto;
    background: #fff;
    height: 450px;
    border-radius: 20px;
    border: 1px #f4f4f4 solid;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
  }

  .b13 {
    width: 90%;
    margin: 0 auto;
    background: #fff;
    border-radius: 20px;
    padding: 30px 10px;
    margin-top: 80px;
  }

  .b14 {
    font-size: 16px;
    font-weight: 550;
    color: #1787ff;
  }
}

/* 平板  768px-992px  屏幕宽度在768px以上显示的颜色*/
@media screen and (min-width: 700px) and (max-width: 1200px) {
  .b1 {
    font-size: 30px;
    font-weight: 600;
    color: #1787ff;
    text-align: center;
    padding: 30px;
  }

  .b2 {
    font-size: 30px;
    font-weight: 600;
    color: #1787ff;
    text-align: center;
  }

  .b3 {
    font-size: 16px;
    font-weight: 600;
    color: #888989;
    text-align: center;
    padding: 30px;
  }

  .b4 {
    width: 100%;
    margin: 0 auto;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }

  .b13 {
    width: 90%;
    margin: 0 auto;
    background: #fff;
    border-radius: 20px;
    padding: 30px;
    margin-top: 80px;
  }

  .b14 {
    font-size: 16px;
    font-weight: 550;
    color: #1787ff;
  }
}

/*笔记本电脑 中等屏幕  992px - 1200px*/
@media screen and (min-width: 1201px) {
}
</style>
