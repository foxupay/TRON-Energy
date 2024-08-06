<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="用户编号" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户编号"
          clearable
          @keyup.enter.native="handleQuery"
          style="width: 150px;"
        />
      </el-form-item>
      <el-form-item label="能量通道" prop="channel">
        <el-input
          v-model="queryParams.channel"
          placeholder="请输入通道标识"
          clearable
          @keyup.enter.native="handleQuery"
          style="width: 150px;"
        />
      </el-form-item>
      <el-form-item label="订单号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单号"
          clearable
          @keyup.enter.native="handleQuery"
          style="width: 150px;"
        />
      </el-form-item>
      <el-form-item label="接收方地址" prop="receiveAddress">
        <el-input
          v-model="queryParams.receiveAddress"
          placeholder="请输入接收方地址"
          clearable
          @keyup.enter.native="handleQuery"
          style="width: 150px;"
        />
      </el-form-item>
      <el-form-item label="支付状态">
        <el-select v-model="queryParams.payStatus" placeholder="状态" clearable style="width: 150px;">
          <el-option label="请选择状态" value=""></el-option>
          <el-option label="未支付" value="1"></el-option>
          <el-option label="支付成功" value="2"></el-option>
          <el-option label="支付取消" value="3"></el-option>
          <el-option label="已退款" value="4"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="代理状态">
        <el-select v-model="queryParams.leaseStatus" placeholder="状态" clearable style="width: 150px;">
          <el-option label="请选择状态" value=""></el-option>
          <el-option label="未代理" value="1"></el-option>
          <el-option label="安排中" value="2"></el-option>
          <el-option label="代理成功" value="3"></el-option>
          <el-option label="代理失败" value="4"></el-option>
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

    <el-table v-loading="loading" :data="tradeList" @selection-change="handleSelectionChange">
      <el-table-column fixed label="用户编号" align="center" prop="userId" show-overflow-tooltip/>
      <el-table-column label="通道标识" align="center" prop="channel" width="200"/>
      <el-table-column label="订单号" align="center" prop="orderNo" width="200"/>
      <el-table-column label="接收者" align="left" width="400">
        <template slot-scope="scope">
          <el-link @click="openNewWindow('https://tronscan.org/#/address/'+scope.row.receiveAddress)">
            {{ scope.row.receiveAddress }}
          </el-link>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-copy-document"
            v-clipboard:copy="scope.row.receiveAddress"
            v-clipboard:success="copySuccess"
          >
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="资源数量" align="center" prop="resourceValue"/>
      <el-table-column label="能量单价" align="center" width="150">
        <template slot-scope="scope">
          {{ scope.row.priceInSun }} SUN
        </template>
      </el-table-column>
      <el-table-column label="租赁时长" align="center" prop="leaseDuration">
        <template slot-scope="scope">
          {{ scope.row.leaseDuration }} {{ scope.row.leaseDurationType === 1 ? '小时' : '天' }}
        </template>
      </el-table-column>
      <el-table-column label="支付金额" align="center" width="300">
        <template slot-scope="scope">
          {{ scope.row.payTrx }} TRX / {{ scope.row.payUsdt }} USDT
        </template>
      </el-table-column>
      <el-table-column label="收款地址" align="center" width="350">
        <template slot-scope="scope" v-if="scope.row.payAddress">
          <el-link @click="openNewWindow('https://tronscan.org/#/address/'+scope.row.payAddress)">
            {{ scope.row.payAddress }}
          </el-link>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-copy-document"
            v-clipboard:copy="scope.row.payAddress"
            v-clipboard:success="copySuccess"
          >
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="支付方式" align="center" prop="payWay">
        <template slot-scope="scope">
          {{
            scope.row.payWay === 1 ? '账户余额' : scope.row.payWay === 2 ? '地址余额' : scope.row.payWay === 3 ? '链上支付' : '未知'
          }}
        </template>
      </el-table-column>
      <el-table-column label="支付时间" align="center" prop="payTime" width="200"/>
      <el-table-column label="代理时间" align="center" prop="leaseTime" width="200"/>
      <el-table-column label="代理Hash" align="center" prop="hash" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-link type="primary" @click="openNewWindow('https://tronscan.org/#/transaction/'+scope.row.frozenTxId)">
            {{ scope.row.frozenTxId }}
          </el-link>
        </template>
      </el-table-column>
      <el-table-column label="到期时间" align="center" prop="expireTime" width="200"/>
      <el-table-column fixed="right" label="支付状态" align="center" prop="payStatus">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.payStatus === 1" type="warning">未支付</el-tag>
          <el-tag v-if="scope.row.payStatus === 2" type="success">支付成功</el-tag>
          <el-tag v-if="scope.row.payStatus === 3" type="info">支付取消</el-tag>
          <el-tag v-if="scope.row.payStatus === 4" type="danger">已退款</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="代理状态" align="center" prop="leaseStatus">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.leaseStatus === 1" type="info">未代理</el-tag>
          <el-tag v-if="scope.row.leaseStatus === 2">安排中</el-tag>
          <el-tag v-if="scope.row.leaseStatus === 3" type="success">代理成功</el-tag>
          <el-tooltip v-if="scope.row.leaseStatus === 4" placement="top" effect="light">
            <div slot="content">{{ scope.row.message }}</div>
            <el-tag type="danger">代理失败</el-tag>
          </el-tooltip>
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
  </div>
</template>

<script>
import {listTrade, getTrade, delTrade, addTrade, updateTrade} from "@/api/lease/trade";
import {copySuccess, openNewWindow} from "@/utils/ruoyi";

export default {
  name: "Trade",
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
      // 能量租用订单表格数据
      tradeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        orderNo: null,
        receiveAddress: null,
        payStatus: null,
        leaseStatus: null,
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
    /** 查询能量租用订单列表 */
    getList() {
      this.loading = true;
      listTrade(this.queryParams).then(response => {
        this.tradeList = response.rows;
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
        userId: null,
        orderNo: null,
        tradeNo: null,
        receiveAddress: null,
        leaseType: null,
        resourceValue: null,
        leaseDurationType: null,
        leaseDuration: null,
        priceInSun: null,
        payAmount: null,
        payTime: null,
        leaseTime: null,
        expireTime: null,
        frozenTxId: null,
        unfreezeTxId: null,
        payStatus: null,
        leaseStatus: null,
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
        userId: null,
        orderNo: null,
        receiveAddress: null,
        payStatus: null,
        leaseStatus: null,
      }
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
      this.title = "添加能量租用订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTrade(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改能量租用订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTrade(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTrade(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除能量租用订单编号为"' + ids + '"的数据项？').then(function () {
        return delTrade(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('lease/trade/export', {
        ...this.queryParams
      }, `trade_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
