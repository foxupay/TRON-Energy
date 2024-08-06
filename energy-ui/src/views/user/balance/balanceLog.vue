<template>
  <!-- 授权用户 -->
  <el-dialog title="余额明细" :visible.sync="visible" width="1000px" top="5vh" append-to-body>
    <el-row :gutter="10" class="mb8">
      <right-toolbar :search="false" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row>
      <el-table ref="table" :data="userList" height="500px">
        <el-table-column label="订单号" prop="orderNo"/>
        <el-table-column label="操作前(TRX)" prop="beforeBalance"/>
        <el-table-column label="操作数量(TRX)" prop="amount"/>
        <el-table-column label="操作后(TRX)" prop="afterBalance"/>
        <el-table-column label="操作时间" prop="createAt"/>
      </el-table>
      <pagination
        v-show="total"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-row>
  </el-dialog>
</template>

<script>
import {listLog} from "@/api/user/log";

export default {
  dicts: ['sys_normal_disable'],
  props: {
    // 角色编号
    userId: {
      type: [Number, String]
    }
  },
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中数组值
      userIds: [],
      // 总条数
      total: 0,
      // 未授权用户数据
      userList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderByColumn: "id",
        isAsc: 'descending',
        userId: undefined,
      }
    };
  },
  methods: {
    // 显示弹框
    show() {
      this.userList = [];
      this.getList();
      this.visible = true;
    },
    initUserId(userId) {
      this.queryParams.userId = userId;
    },
    // 查询表数据
    getList() {
      listLog(this.queryParams).then(res => {
        this.userList = res.rows;
        this.total = res.total;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
  }
};
</script>
