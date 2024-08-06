<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="发送者" prop="fromAddress">
        <el-input
          v-model="queryParams.fromAddress"
          placeholder="请输入发送者地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="接收者" prop="toAddress">
        <el-input
          v-model="queryParams.toAddress"
          placeholder="请输入接收者地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Hash" prop="hash">
        <el-input
          v-model="queryParams.hash"
          placeholder="请输入Hash"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付状态">
        <el-select v-model="queryParams.status" placeholder="状态" clearable>
          <el-option label="请选择状态" value=""></el-option>
          <el-option label="支付中" value="1"></el-option>
          <el-option label="支付成功" value="2"></el-option>
          <el-option label="支付失败" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="处理状态">
        <el-select v-model="queryParams.handle" placeholder="状态" clearable>
          <el-option label="请选择状态" value=""></el-option>
          <el-option label="待同步" value="1"></el-option>
          <el-option label="待处理" value="2"></el-option>
          <el-option label="已处理" value="3"></el-option>
          <el-option label="待人工确认" value="4"></el-option>
          <el-option label="交易无效" value="5"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="transactionList">
      <el-table-column label="编号" align="center" prop="id" width="120"/>
      <el-table-column label="发送者" align="left" width="350">
        <template slot-scope="scope">
          <el-link @click="openNewWindow('https://tronscan.org/#/address/'+scope.row.fromAddress)">
            {{ scope.row.fromAddress }}
          </el-link>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-copy-document"
            v-clipboard:copy="scope.row.fromAddress"
            v-clipboard:success="copySuccess"
          >
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="接收者" align="left" width="350">
        <template slot-scope="scope">
          <el-link @click="openNewWindow('https://tronscan.org/#/address/'+scope.row.toAddress)">
            {{ scope.row.toAddress }}
          </el-link>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-copy-document"
            v-clipboard:copy="scope.row.toAddress"
            v-clipboard:success="copySuccess"
          >
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="数量" align="right" prop="amount" width="150">
        <template slot-scope="scope">
          {{ scope.row.amount }}
          <span v-if="scope.row.type === 1" style="color: orangered">TRX</span>
          <span v-else-if="scope.row.type === 2" style="color: orangered">USDT</span>
        </template>
      </el-table-column>
      <el-table-column label="Hash" align="center" prop="hash" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-link type="primary" @click="openNewWindow('https://tronscan.org/#/transaction/'+scope.row.hash)">
            {{ scope.row.hash }}
          </el-link>
        </template>
      </el-table-column>
      <el-table-column label="支付状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 1">支付中</el-tag>
          <el-tag v-else-if="scope.row.status === 2" type="success">成功</el-tag>
          <el-tag v-else-if="scope.row.status === 3" type="danger">失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="处理状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.handle === 1" type="warning">待同步</el-tag>
          <el-tag v-if="scope.row.handle === 2">待处理</el-tag>
          <el-tag v-if="scope.row.handle === 3" type="success">已处理</el-tag>
          <el-tooltip v-if="scope.row.handle === 5 || scope.row.handle === 4" placement="top">
            <div slot="content">{{ scope.row.message }}</div>
            <el-tag v-if="scope.row.handle === 4">待人工确认</el-tag>
            <el-tag v-if="scope.row.handle === 5" type="danger">交易无效</el-tag>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="时间" align="center" prop="updateTime" width="200"/>
      <el-table-column label="操作" align="center" class-name="small-padding">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-if="(scope.row.handle === 2 && scope.row.status === 2) || scope.row.handle === 4"
          >处理
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

    <!-- 添加或修改TRON交易对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="状态" prop="handle">
          <el-radio v-model="form.handle" label="2">交易放行</el-radio>
          <el-radio v-model="form.handle" label="5">交易取消</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {
  listTransaction,
  delTransaction, handleTransaction, getTransaction, updateTransaction,
} from "@/api/tron/transaction";
import {copySuccess, openNewWindow} from "@/utils/ruoyi";

export default {
  name: "Transaction",
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
      // TRON交易表格数据
      transactionList: [],
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
        fromAddress: null,
        toAddress: null,
        amount: null,
        type: null,
        hash: null,
        status: null,
        handle: null
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
    copySuccess,
    openNewWindow,
    /** 查询TRON交易列表 */
    getList() {
      this.loading = true;
      listTransaction(this.queryParams).then(response => {
        this.transactionList = response.rows;
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
        fromAddress: null,
        toAddress: null,
        amount: null,
        type: null,
        hash: null,
        status: null,
        handle: null,
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
      this.queryParams = {
        fromAddress: null,
        toAddress: null,
        amount: null,
        type: null,
        hash: null,
        status: null,
        handle: null,
      };
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.form = row;
      this.open = true;
      this.title = "处理TRON交易";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateTransaction(this.form).then(response => {
            this.$modal.msgSuccess("处理提交");
            this.open = false;
            this.getList();
          });
        }
      });
    },
  }
};
</script>
