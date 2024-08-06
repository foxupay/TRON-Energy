<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="批次号" prop="sweepNo">
        <el-input
          v-model="queryParams.sweepNo"
          placeholder="请输入批次号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="归集编号" prop="sweepId">
        <el-input
          v-model="queryParams.sweepId"
          placeholder="请输入归集编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="归集地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入归集地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="归集状态">
        <el-select v-model="queryParams.status" placeholder="状态" clearable>
          <el-option label="请选择状态" value=""></el-option>
          <el-option label="未归集" value="1"></el-option>
          <el-option label="归集中" value="2"></el-option>
          <el-option label="归集成功" value="3"></el-option>
          <el-option label="归集失败" value="4"></el-option>
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

    <el-table v-loading="loading" :data="logList">
      <el-table-column label="批次号" align="center" prop="sweepNo" width="200"/>
      <el-table-column label="归集编号" align="center" prop="sweepId" width="200"/>
      <el-table-column label="归集地址" align="left" width="330">
        <template slot-scope="scope">
          <el-link @click="openNewWindow('https://tronscan.org/#/address/'+scope.row.address)">
            {{ scope.row.address }}
          </el-link>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-copy-document"
            v-clipboard:copy="scope.row.address"
            v-clipboard:success="copySuccess"
          >
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="代币类型" align="center" prop="type">
        <template slot-scope="scope">
          <span v-if="scope.row.type === 1">TRX</span>
          <span v-if="scope.row.type === 2">USDT</span>
        </template>
      </el-table-column>
      <el-table-column label="余额数量" align="center" prop="balance"/>
      <el-table-column label="归集数量" align="center" prop="amount"/>
      <el-table-column label="归集状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 1" type="warning">未归集</el-tag>
          <el-tag v-else-if="scope.row.status === 2">归集中</el-tag>
          <el-tag v-else-if="scope.row.status === 3" type="success">归集成功</el-tag>
          <el-tooltip v-if="scope.row.status === 4" placement="top" effect="light">
            <div slot="content">{{ scope.row.message }}</div>
            <el-tag type="danger">归集失败</el-tag>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="Hash" align="center" prop="hash" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-link type="primary" @click="openNewWindow('https://tronscan.org/#/transaction/'+scope.row.hash)">
            {{ scope.row.hash }}
          </el-link>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="150"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="150"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {listLog, getLog, delLog, addLog, updateLog} from "@/api/sweep/log";
import {copySuccess, openNewWindow} from "@/utils/ruoyi";

export default {
  name: "Log",
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
      // 归集记录表格数据
      logList: [],
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
        sweepNo: null,
        sweepId: null,
        address: null,
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
    copySuccess,
    openNewWindow,
    /** 查询归集记录列表 */
    getList() {
      this.loading = true;
      listLog(this.queryParams).then(response => {
        this.logList = response.rows;
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
        sweepNo: null,
        sweepId: null,
        privateKey: null,
        address: null,
        type: null,
        balance: null,
        amount: null,
        status: null,
        message: null,
        hash: null,
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
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        orderByColumn: "id",
        isAsc: 'descending',
        sweepNo: null,
        sweepId: null,
        address: null,
        status: null,
      }
      this.resetForm("queryForm");
      this.handleQuery();
    },
  }
};
</script>
