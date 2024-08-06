<template>
  <div style="background: #e7f0f9; min-height: 100%">
    <myheader ref="gaibian" class="gongyong"></myheader>
    <div style="height: 60px; width: 100%"></div>

    <div class="d1">
      <el-table :data="postList">
        <el-table-column label="订单进度" align="center" prop="status" width="100px">
          <template slot-scope="scope">
            <el-tag effect="dark">
              {{ scope.row | zhuangtai }}
            </el-tag>
            <!-- <el-tag type="danger">{{ scope.row | zhuangtai}}</el-tag> -->
          </template>
        </el-table-column>
        <el-table-column label="资源类型" align="center" prop="status" width="100px">
          <template>
            <el-tag type="danger">能量</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="租用时间" align="center">
          <template slot-scope="scope">
            <el-tag> {{ scope.row | time }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="完成数量" align="center" width="150px">
          <template slot-scope="scope">
            <el-tag type="warning">{{ scope.row.resourceValue }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="消费TRX" align="center" width="150px">
          <template slot-scope="scope"> {{ scope.row.payTrx }}TRX </template>
        </el-table-column>
        <el-table-column label="接收地址" align="center" width="330">
          <template slot-scope="scope">
            <div
              style="
                display: flex;
                justify-content: flex-end;
                align-items: center;
                color: #1787ff;
              "
            >
              <i
                class="el-icon-document-copy"
                id="copyBtn"
                style="font-size: 16px; color: #1787ff"
                :data-clipboard-text="scope.row.receiveAddress"
                data-clipboard-action="copy"
                data-clipboard-target="#copyTarget"
                @click="copy"
              ></i>
              <div id="copyTarget" style="margin-left: 5px">
                {{ scope.row.receiveAddress }}
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime">
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="回收时间" align="center" prop="status">
          <template slot-scope="scope">
            <span>{{ scope.row.expireTime }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="代理Hash"
          align="center"
          class-name="small-padding fixed-width"
          width="150"
           show-overflow-tooltip
        >
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.frozenTxId != null && scope.row.frozenTxId != ''"
              size="mini"
              type="text"
              @click="handleUpdate(scope.row.frozenTxId)"
              >{{ scope.row.frozenTxId }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="d2">
      <div
        v-if="postList.length == 0"
        style="font-size: 12px; color: #646566; text-align: center; padding-top: 20px"
      >
        暂无数据
      </div>
      <div class="d3" v-for="item in postList" :key="item.id">
        <div class="d4">
          <div class="d5">订单进度</div>
          <div class="d6" style="color: #05c160">{{ item | zhuangtai }}</div>
        </div>
        <div class="d4">
          <div class="d5">资源类型</div>
          <div class="d6" style="color: #f3455a">能量</div>
        </div>
        <div class="d4">
          <div class="d5">租用时间</div>
          <div class="d6" style="color: #1787ff">{{ item | time }}</div>
        </div>

        <div class="d4">
          <div class="d5">完成数量</div>
          <div class="d6" style="color: #ff8d1a">{{ item.resourceValue }}</div>
        </div>
        <div class="d4">
          <div class="d5">消费TRX</div>
          <div class="d6" style="color: #1787ff">{{ item.payTrx }}TRX</div>
        </div>

        <div class="d4">
          <div class="d5">接收地址</div>
          <div class="d6">
            <div
              style="
                display: flex;
                justify-content: flex-end;
                align-items: center;
                color: #1787ff;
              "
            >
              <i
                class="el-icon-document-copy"
                id="copyBtn"
                style="font-size: 16px; color: #1787ff"
                :data-clipboard-text="item.receiveAddress"
                data-clipboard-action="copy"
                data-clipboard-target="#copyTarget"
                @click="copy"
              ></i>
              <div id="copyTarget" style="margin-left: 5px">
                {{ item.receiveAddress | dizhi }}
              </div>
            </div>
          </div>
        </div>
        <div class="d4">
          <div class="d5">创建时间</div>
          <div class="d6">{{ item.createTime }}</div>
        </div>
        <div class="d4">
          <div class="d5">回收时间</div>
          <div class="d6">{{ item.expireTime }}</div>
        </div>
        <div class="d4">
          <div class="d5">代理Hash</div>
          <div class="d6" v-if="item.frozenTxId != null && item.frozenTxId != ''">
            <span @click="handleUpdate(item.frozenTxId)" style="color: #1787ff"
              >查看</span
            >
          </div>
        </div>
      </div>
    </div>

    <!-- <div>
            <el-pagination class="d8" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage" :page-sizes="[10, 20, 50, 100]" :page-size="10" :pager-count="3"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div> -->
    <div class="d7" v-if="total > 0">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :pager-count="5"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      >
      </el-pagination>
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
      loading: true,

      // 表格数据
      // 总条数
      total: 0,
      // 表格数据
      postList: [],
      // 查询参数

      pageNum: 1,
      pageSize: 10,
      currentPage: 0,
    };
  },
  components: {
    myheader,
  },
  methods: {
    handleSizeChange(val) {
      this.pageNum = 1;
      this.pageSize = val;
      this.getList();
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getList();
    },
    //打开hash
    handleUpdate(url) {
      var tt = "https://tronscan.org/#/transaction/" + url;
      window.open(tt);
      clearInterval(this.timer);
      this.timer = null;
      0;
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

    getList() {
      this.loading = true;
      var params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      };
      this.$axios({
        method: "GET",
        url: "/api/app/lease/list",
        params: params,
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          Authorization: this.getCookie("nltoken"),
        },
      })
        .then((userData) => {
          if (userData.data.code == "200") {
            this.loading = false;
            this.postList = userData.data.rows;
            this.total = userData.data.total;
          } else {
            this.loading = false;
            this.$message.error(userData.data.msg);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },

  // beforeDestroy() {
  //     clearInterval(this.timer);
  //     this.timer = null;
  // },
  mounted() {
    this.$refs.gaibian.genggai("4");
  },
  created: function () {
    this.$nextTick(() => {
      this.getList();
    });
  },
  filters: {
    time: function (data) {
      if (data.leaseDurationType == 1) {
        return data.leaseDuration + "时";
      }
      if (data.leaseDurationType == 2) {
        return data.leaseDuration + "天";
      }
    },
    dizhi: function (data) {
      var s = data.substring(0, 5);
      var b = data.slice(-5);
      return s + "..." + b;
    },
    zhuangtai: function (data) {
      if (data.payStatus == 1) {
        return "待支付";
      }
      if (data.payStatus == 3) {
        return "支付失败";
      }
      if (data.payStatus == 4) {
        return "已退款";
      }
      if (data.leaseStatus == 1) {
        return "未处理";
      }
      if (data.leaseStatus == 2) {
        return "安排中";
      }
      if (data.leaseStatus == 3) {
        return "租用成功";
      }
      if (data.leaseStatus == 4) {
        return "租用失败";
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

@media screen and (max-width: 699px) {
  .d1 {
    display: none;
    width: 94%;
    margin: 50px auto;
  }

  .d2 {
    display: block;
  }

  .d3 {
    background: #fff;
    width: 94%;
    margin: 2% auto;
    border-radius: 10px;
    padding: 15px;
  }

  .d4 {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .d5 {
    width: 80px;
    font-size: 14px;
    color: #1787ff;
    padding: 5px 0;
  }

  .d6 {
    flex: 1;
    width: 0;
    text-align: right;
    font-size: 13px;
    padding: 5px 0;
  }

  .d7 {
    padding: 30px 0;
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }
}

/* 平板  768px-992px  屏幕宽度在768px以上显示的颜色*/
@media screen and (min-width: 700px) and (max-width: 1200px) {
  .d1 {
    display: block;
    width: 94%;
    margin: 50px auto;
  }

  .d2 {
    display: none;
  }

  .d7 {
    padding: 30px 0;
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }
}

@media screen and (min-width: 1201px) {
  .d1 {
    display: block;
    width: 94%;
    margin: 50px auto;
  }

  .d2 {
    display: none;
  }

  .d7 {
    padding: 30px 0;
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }
}
</style>
