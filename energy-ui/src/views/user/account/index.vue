<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="邮箱" prop="email">
        <el-input
          v-model="queryParams.email"
          placeholder="请输入邮箱"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="accountList" @selection-change="handleSelectionChange">
      <el-table-column label="用户编号" align="center" prop="id"/>
      <el-table-column label="昵称" align="center" prop="nickName"/>
      <el-table-column label="邮箱" align="center" prop="email"/>
      <el-table-column label="余额(TRX)" align="center" prop="balance">
        <template slot-scope="scope">
          <el-link type="primary" @click="openLog(scope.row)">
            {{ scope.row.balance }}
          </el-link>
          &nbsp;
          <el-button
            size="mini"
            type="text"
            style="color: #ff4949;"
            @click="changeBalance(scope.row)"
          >修改
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 1" type="success" disable-transitions>正常</el-tag>
          <el-tag v-if="scope.row.status === 2" type="danger" disable-transitions>禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="注册时间" align="center" prop="createTime"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEnable(scope.row)"
            v-has-permi="['user:account:edit']"
          >{{ scope.row.status === 2 ? '启用' : '禁用' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <balance-log ref="select" @ok="handleQuery"/>
  </div>
</template>

<script>
import {listAccount, getAccount, addAccount, updateAccount, enable} from "@/api/user/account";
import balanceLog from "@/views/user/balance/balanceLog.vue";
import {updateBalance} from "@/api/user/balance";

export default {
  name: "Account",
  components: {balanceLog},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 客户端账户体系表格数据
      accountList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderByColumn: "id",
        isAsc: 'descending',
        nickName: null,
        phone: null,
        email: null,
        sign: null,
        inviteId: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询客户端账户体系列表 */
    getList() {
      this.loading = true;
      listAccount(this.queryParams).then(response => {
        this.accountList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        nickName: null,
        phone: null,
        email: null,
        sign: null,
        inviteId: null,
        status: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加客户端账户体系";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAccount(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改客户端账户体系";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAccount(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAccount(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleEnable(row) {
      const text = row.status === 1 ? "禁用" : "启用";
      this.$modal.confirm('是否确认' + text + '账号："' + row.email + '"？').then(function () {
        return enable(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(text + "成功");
      }).catch(() => {
      });
    },
    /** 打开授权用户表弹窗 */
    openLog(row) {
      this.$refs.select.initUserId(row.id)
      this.$refs.select.show();
    },
    /** 打开授权用户表弹窗 */
    changeBalance(row) {
      let that = this
      this.$prompt('请输入余额', '设置余额', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        let data = {
          userId: row.id,
          balance: value
        }
        updateBalance(data).then(response => {
          that.$modal.msgSuccess("操作成功");
          that.getList();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    },
  }
};
</script>
