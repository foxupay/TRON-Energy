<template>
  <div style="background: #e7f0f9">
    <div
      style="
        background: #fff;
        display: flex;
        justify-content: space-between;
        align-items: center;
        height: 60px;
      "
    >
      <div style="display: flex; justify-content: flex-start; align-items: center">
        <i id="b3" class="el-icon-s-unfold" @click="dianjichu"></i>
        <i id="b4" class="el-icon-s-fold" @click="dianjijin"></i>
        <div style="margin-left: 20px; font-weight: 550">{{ text }}</div>
      </div>
      <div class="b1">
        <el-menu
          :default-active="activeIndex2"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          style="border-bottom: 0"
          background-color="#ffffff"
          text-color="#000"
          active-text-color="#1f87ff"
        >
          <el-menu-item index="1">首页</el-menu-item>
          <!-- <el-menu-item index="2">最新订单</el-menu-item> -->
          <el-menu-item index="3">租用能量</el-menu-item>
          <el-menu-item index="4">租用记录</el-menu-item>
          <el-menu-item index="7">API文档</el-menu-item>
          <el-menu-item v-if="account != ''" index="5">个人中心</el-menu-item>
        </el-menu>
      </div>

      <div style="display: flex; justify-content: flex-end; align-items: center">
        <div
          v-if="account == ''"
          style="cursor: pointer; width: 180px; text-align: right; margin-right: 25px"
          @click="denglu"
        >
          登录
        </div>
        <div
          v-if="account != ''"
          style="cursor: pointer; font-size: 14px; width: 180px; text-align: right"
        >
          {{ account }}
        </div>
        <div
          v-if="account != ''"
          style="margin-right: 20px; margin-left: 10px; position: relative"
        >
          <i v-if="!kuaiguan" class="el-icon-arrow-down" @click="kai"></i>
          <i v-if="kuaiguan" class="el-icon-arrow-up" @click="kai"></i>
          <div
            v-if="kuaiguan"
            style="
              font-size: 12px;
              position: absolute;
              width: 100px;
              background: #fff;
              top: 30px;
              left: -65px;
            "
          >
            <div
              style="margin: 5px; padding: 5px 0; text-align: center; cursor: pointer"
              @click="gaimima"
            >
              更改密码
            </div>
            <div
              style="margin: 5px; padding: 5px 0; text-align: center; cursor: pointer"
              @click="tuichu"
            >
              退出登录
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="b2">
      <el-menu
        :default-active="activeIndex2"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        style="border-bottom: 0"
        background-color="#fff"
        text-color="#000"
        active-text-color="#1f87ff"
      >
        <el-menu-item index="1">首页</el-menu-item>
        <!-- <el-menu-item index="2">最新订单</el-menu-item> -->
        <el-menu-item index="3">租用能量</el-menu-item>
        <el-menu-item index="4">租用记录</el-menu-item>
        <el-menu-item index="7">API文档</el-menu-item>
        <el-menu-item v-if="account != ''" index="5">个人中心</el-menu-item>
      </el-menu>
    </div>

    <el-dialog title="更改密码" :visible.sync="centerDialogVisible" width="30%" center>
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="新密码" prop="pass">
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input
            type="password"
            v-model="ruleForm.checkPass"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <div style="text-align: right; width: 100%; margin-top: 20px">
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          </div>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import JSEncrypt from "jsencrypt";
import qs from "qs";

export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      text: '',
      currentPage: 0,
      activeIndex2: "",
      windowWidth: document.body.clientWidth,
      windowHeight: document.body.clientHeight,
      account: "",
      kuaiguan: false,
      centerDialogVisible: false,
      ruleForm: {
        pass: "",
        checkPass: "",
      },
      rules: {
        pass: [{validator: validatePass, trigger: "blur"}],
        checkPass: [{validator: validatePass2, trigger: "blur"}],
      },
    };
  },
  watch: {
    // 监听页面高度
    windowHeight(val) {
      this.windowHeight = val;
    },
    // 监听页面宽度
    windowWidth(val) {
      if (val > 699) {
        var btn = document.querySelector(".b2");
        var b3 = document.querySelector("#b3");
        var b4 = document.querySelector("#b4");
        btn.style.display = "none";
        b3.style.display = "none";
        b4.style.display = "none";
      } else {
        var btn = document.querySelector(".b2");
        var b3 = document.querySelector("#b3");
        var b4 = document.querySelector("#b4");
        btn.style.display = "none";
        b3.style.display = "block";
        b4.style.display = "none";
      }

      // console.log('实时屏幕宽度：', val, this.windowHeight)
    },
  },
  mounted() {
    // <!--把window.onresize事件挂在到mounted函数上-->
    window.onresize = () => {
      return (() => {
        this.windowHeight = document.body.clientHeight; // 高
        this.windowWidth = document.body.clientWidth; // 宽
      })();
    };
  },

  methods: {
    gaimima() {
      this.centerDialogVisible = true;
      this.ruleForm.pass = "";
      this.ruleForm.checkPass = "";
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.setmima();
        } else {
          return false;
        }
      });
    },
    setmima() {
      var encryptor = new JSEncrypt();
      encryptor.setPublicKey(
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjV+uSsM9XcALPMnxjS1v3rhPLfkehaEx2q1eoX5x8YDizqjAO3bLmmzXIhmQIgu1saBKBN9HPQ3q+izpN7b0MGMPeYLiXVWXpe6z40L1RTSt7Y96nMBqB2RwD6ezimc0d9RWvmB7KpeBzLYS1u5ov8kDGqjWvY+zV6EcPB1lC+oMvhUezAnzBSAtyZqnByHznXIFuL9TkJuPoGOj7wykNk7j4enrnSrOhCv26Wyte7NVvNS3Gqetf4OkPCub+28g9reEi+6DPBOVDja2e8DPcIGQxqLRkA7AaSJPB/B/WzXoMq+MOQ5NJBseRvUg5owgWCcTH3NxLLN/kfu370L7jwIDAQAB"
      ); // 设置公钥
      var mimajiami = "DECRYPT:" + this.ruleForm.pass;
      var mima = encryptor.encrypt(mimajiami); // 对需要加密的数据进行加密
      var params = qs.stringify({
        password: mima,
      });

      this.$axios
        .post("/api/app/user/info/set/pwd", params, {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            Authorization: this.getCookie("nltoken"),
          },
        })
        .then((userData) => {
          if (userData.data.code == "200") {
            this.$message({
              message: userData.data.msg,
              type: "success",
            });
          } else {
            this.loading = false;
            this.$message.error(userData.data.msg);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //退出登录
    tuichu() {
      this.delcookie("nltoken");
      this.delcookie("nlaccount");

      this.account = "";
    },
    kai() {
      this.kuaiguan = !this.kuaiguan;
    },
    genggai(num) {
      this.activeIndex2 = num;
    },
    dianjichu() {
      var btn = document.querySelector(".b2");
      var b3 = document.querySelector("#b3");
      var b4 = document.querySelector("#b4");
      btn.style.display = "block";
      b3.style.display = "none";
      b4.style.display = "block";
    },
    dianjijin() {
      var btn = document.querySelector(".b2");
      var b3 = document.querySelector("#b3");
      var b4 = document.querySelector("#b4");
      btn.style.display = "none";
      b3.style.display = "block";
      b4.style.display = "none";
    },

    handleSelect(key, keyPath) {
      // this.setcookie('daohangshu',key)
      if (key === '1') {
        this.$router.push({
          name: "index",
        });
      }
      if (key === '2') {
        this.$router.push({
          name: "orders",
        });
      }
      if (key === '3') {
        this.$router.push({
          name: "energy",
        });
      }
      if (key === '4') {
        this.$router.push({
          name: "record",
        });
      }
      if (key === '5') {
        this.$router.push({
          name: "my",
        });
      }
      if (key === '6') {
        this.$router.push({
          name: "appxz",
        });
      }
      if (key === '7') {
        this.$router.push({
          name: 'apiDoc'
        })
      }
    },
    denglu() {
      this.$router.push({
        name: "login",
      });
    },

    getzhanghu() {
      // var params = qs.stringify({
      //     'account': this.ruleForm.account,
      //     'code': this.ruleForm.code,
      // })

      this.$axios
        .post("/api/app/user/info", null, {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            Authorization: this.getCookie("nltoken"),
          },
        })
        .then((userData) => {
          if (userData.data.code == "200") {
            this.rechargeAddress = userData.data.data.rechargeAddress;
            this.setcookie("nlaccount", userData.data.data.account);
            this.account = userData.data.data.account;
          } else {
            this.loading = false;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },

  created: function () {
    this.text = this.getCookie('nltext')
    if (this.getCookie('nlaccount') !== undefined && this.getCookie('nlaccount') !== '' && this.getCookie('nlaccount') !== null) {
      this.account = this.getCookie('nlaccount')
    } else {
      if (this.getCookie('nltoken') !== undefined && this.getCookie('nltoken') !== '' && this.getCookie('nltoken') !== null) {
        this.getzhanghu()
      }
    }
  },
  filters: {
    zhuangtai: function (data) {
      if (data === "1") {
        return "待支付";
      }
      if (data === "2") {
        return "支付完成";
      }
      if (data === "3") {
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

.b1 {
  display: block;
}

.b2 {
  width: 100%;
  display: none;

  /* height: calc(100vh - 60px); */
  background: rgb(84, 92, 100);
}

#b3 {
  font-size: 20px;
  padding: 10px;
  display: none;
}

#b4 {
  font-size: 20px;
  padding: 10px;
  display: none;
}

@media screen and (max-width: 699px) {
  .b1 {
    display: none;
  }

  .b2 {
    display: none;
    width: 100%;

    background: rgb(84, 92, 100);
  }

  #b3 {
    font-size: 20px;
    padding: 10px;
    display: block;
  }
}

/* 平板  768px-992px  屏幕宽度在768px以上显示的颜色*/
@media screen and (min-width: 700px) and (max-width: 1200px) {
}

@media screen and (min-width: 1201px) {
}
</style>
