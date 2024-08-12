<template>
  <div style="background: #f9fcff;height: 100%;">
    <myheader ref="gaibian" class="gongyong"></myheader>
    <div style="height:80px ;width: 100%;"></div>

    <div class="a1">
      <div class="a2">
        <div :class="[active===1?'liang':'buliang']" @click="dianji(1)">
          下单
        </div>
        <div :class="[active===2?'liang':'buliang']" @click="dianji(2)">
          状态查询
        </div>
        <div :class="[active===3?'liang':'buliang']" @click="dianji(3)">
          配置查询
        </div>
        <div :class="[active===4?'liang':'buliang']" @click="dianji(4)">
          余额查询
        </div>
      </div>
      <div class="a3">
        <div v-if="active===1">
          <div style="font-weight: 550;">
            下单接口 ：/api/external/pay
          </div>
          <div style="margin-top: 10px;">
            请求方式 ：POST
          </div>
          <div style="margin:15px 0 ;">
            <p style="font-weight: 550;">请求数据</p>
            <pre style="white-space: pre-wrap;line-height: 30px;">
            {
              "username": "energy@163.com",//账户
              "password": "aaaaaaa",//密码
              "address": "TQECdSusKMTBLpvTKsgueURrvGeHo85RzV",//能量接收地址
              "amount": 32000,//能量数量，整数
              "time": "1h"租用时长 h代表小时，d代表天数， 仅支持1h、1d、3d及更多天数时间
            }
            </pre>
            <p style="margin:15px 0 ;font-weight: 550;">返回数据：code=200是成功，其他为失败</p>
            <pre style="white-space: pre-wrap;line-height: 30px;">
            {
              "msg": "支付成功，正在安排...",
              "trace_id": "e71f2069921c447f8e575fdf1e0f9b75",
              "code": 200,
              "data": {
                "orderNo": "1801152914933288960"
              }
            }
            </pre>

          </div>
        </div>
        <div v-if="active===2">
          <div style="font-weight: 550;">
            状态查询接口 ：/api/external/query?orderNo=订单号
          </div>
          <div style="margin-top: 10px;">
            请求方式 ：GET
          </div>
          <div style="margin:15px 0 ;">
            <p style="margin:15px 0 ;font-weight: 550;">返回数据：code=200是成功，其他为失败</p>
            <pre style="white-space: pre-wrap;line-height: 30px;">
            {
             "msg": "操作成功",
             "trace_id": "d651915fe04d40a39bf0240653a2ab21",
             "code": 200,
             "data": {
               "amount": 32000, //租用能量数
               "orderNo": "1801152914933288960", //订单编号
               "address": "TQECdSusKMTBLpvTKsgueURrvGeHo85RzV", //租用地址
               "price": 49.85, //单价，SUN
               "time": "1h", //租用时长
               "trx": 1.6, //付款金额，trx
               "hash": "39fe43614e37942a0f9fe887b9c013f4fa2bca2ae8ef0b6276e2760e09b6c79b", //租用Hash
               "status": 2 //租用状态 1未租用 2租用中 3租用成功 4租用失败
              }
             }
            </pre>
          </div>
        </div>
        <div v-if="active===3">
          <div style="font-weight: 550;">
            配置查询接口 ：/api/external/config?username=账号
          </div>
          <div style="margin-top: 10px;">
            请求方式 ：GET
          </div>
          <div style="margin:15px 0 ;">
            <p style="margin:15px 0 ;font-weight: 550;">返回数据：code=200是成功，其他为失败</p>
            <pre style="white-space: pre-wrap;line-height: 30px;">
            {
             "msg": "操作成功",
             "trace_id": "84bc76feba8c41388af47b72179ec79d",
             "code": 200,
             "data": {
               "min": 32000, //下单最小数量
               "max": 32876768, //当前剩余能量额度
               "canPay": true, //是否可以下单
               "h1": 49.852, //1小时单价，SUN
               "h24": 132, //1天单价，SUN
               "h72": 99, //3天单价，SUN
               "other": 99 //其他单价，SUN
              }
             }
            </pre>
          </div>
        </div>
        <div v-if="active===4">
          <div style="font-weight: 550;">
            余额查询接口 ：/api/external/balance?username=账号
          </div>
          <div style="margin-top: 10px;">
            请求方式 ：GET
          </div>
          <div style="margin:15px 0 ;">
            <p style="margin:15px 0 ;font-weight: 550;">返回数据：code=200是成功，其他为失败</p>
            <pre style="white-space: pre-wrap;line-height: 30px;">
            {
              "msg": "操作成功",
              "trace_id": "26c9d2131a304177aba8b37fb5711d5a",
               "code": 200,
               "data": {
                "balance": "1.804072"
               }
            }
            </pre>
          </div>
        </div>
      </div>

    </div>


  </div>
</template>
<script>
import myheader from "@/components/gongyong"

export default {
  data() {
    return {
      active: 1,
    }
  },
  components: {
    myheader
  },
  mounted() {
    this.$refs.gaibian.genggai('7')
  },
  methods: {
    dianji(num) {
      this.active = num
    }

  },

  created: function () {


  },
  filters: {}
}
</script>
<style scoped>
.gongyong {
  position: fixed;
  width: 100%;
  left: 0;
  top: 0;
  z-index: 100000;
}

@media screen and (max-width: 699px) {
  .a1 {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .a2 {
    padding-top: 60px;
    padding-bottom: 20px;
    width: 100%;
    background: #fff;
    height: 100%;
    display: flex;
    justify-content: space-around;
    align-items: center;
    font-size: 14px;
    font-weight: 600;

  }

  .a3 {
    width: 100%;
    font-size: 12px;
    margin: 20px;
    padding-left: 20px;
  }

  .liang {
    width: 100%;
    text-align: center;
    color: #1787ff;
    padding: 15px;
    background: #c9e2fd;
    cursor: pointer;
  }

  .buliang {
    color: #000;
    width: 100%;
    text-align: center;
    padding: 15px;
    background: #fff;
    cursor: pointer;
  }
}

/* 平板  768px-992px  屏幕宽度在768px以上显示的颜色*/
@media screen and (min-width: 700px) and (max-width: 1200px) {
  .a1 {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: flex-start;


  }

  .a2 {
    width: 150px;
    background: #fff;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    font-weight: 600;

  }

  .a3 {
    font-size: 14px;
    padding: 20px;

  }

  .liang {
    width: 100%;
    text-align: center;
    color: #1787ff;
    padding: 15px;
    background: #c9e2fd;
    cursor: pointer;
  }

  .buliang {
    color: #000;
    width: 100%;
    text-align: center;
    padding: 15px;
    background: #fff;
    cursor: pointer;
  }
}

/*笔记本电脑 中等屏幕  992px - 1200px*/
@media screen and (min-width: 1201px) {
  .a1 {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: flex-start;


  }

  .a2 {
    width: 150px;
    background: #fff;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    font-weight: 800;
  }

  .a3 {
    font-size: 14px;
    padding-left: 20%;
  }

  .liang {
    width: 100%;
    text-align: center;
    color: #1787ff;
    padding: 15px;
    background: #c9e2fd;
    cursor: pointer;
  }

  .buliang {
    color: #000;
    width: 100%;
    text-align: center;
    padding: 15px;
    background: #fff;
    cursor: pointer;
  }
}
</style>
