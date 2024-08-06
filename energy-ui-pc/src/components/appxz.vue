<template>
  <div style="background: #f9fcff; height: 100%">
    <myheader ref="gaibian" class="gongyong"></myheader>
    <div style="height: 60px; width: 100%"></div>

    <div class="a1">
      <div class="a2" @click="azxiazai">
        <div>Android</div>
        <div class="a3">下载</div>
      </div>
      <div class="a2">
        <div>IOS</div>
        <div class="a3">下载</div>
      </div>
    </div>
  </div>
</template>
<script>
import myheader from "@/components/gongyong";
import qs from "qs";
export default {
  data() {
    return {};
  },
  components: {
    myheader,
  },
  mounted() {
    this.$refs.gaibian.genggai("6");
  },
  methods: {
    azxiazai() {
      window.open("https://www.foxupay.com/app/energy.apk");
    },
  },

  created: function () {},
  filters: {},
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

@media screen and (max-width: 699px) {
  .a1 {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .a2 {
    margin: 20px 0;
    border-radius: 20px;
    width: 70%;
    height: 200px;
    background: #fff;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-size: 50px;
    font-weight: 800;
  }

  .a3 {
    font-size: 24px;
    font-weight: 600;
    color: #1787ff;
  }
}

/* 平板  768px-992px  屏幕宽度在768px以上显示的颜色*/
@media screen and (min-width: 700px) and (max-width: 1200px) {
  .a1 {
    width: 100%;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }

  .a2 {
    margin-top: 100px;
    border-radius: 20px;
    width: 300px;
    background: #fff;
    height: 300px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-size: 60px;
    font-weight: 800;
  }

  .a3 {
    font-size: 30px;
    font-weight: 600;
    color: #1787ff;
  }
}

/*笔记本电脑 中等屏幕  992px - 1200px*/
@media screen and (min-width: 1201px) {
  .a1 {
    width: 100%;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }

  .a2 {
    margin-top: 100px;
    border-radius: 20px;
    width: 500px;
    background: #fff;
    height: 500px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-size: 80px;
    font-weight: 800;
  }

  .a3 {
    font-size: 30px;
    font-weight: 600;
    color: #1787ff;
  }
}
</style>