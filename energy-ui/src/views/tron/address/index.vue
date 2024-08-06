<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型">
        <el-select v-model="queryParams.type" placeholder="类型" clearable>
          <el-option label="请选择状态" value=""></el-option>
          <el-option label="固定" value="2"></el-option>
          <el-option label="共享" value="1"></el-option>
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

    <el-table v-loading="loading" :data="addressList" @sort-change="sortChange">
      <el-table-column label="地址" align="left" width="380">
        <template slot-scope="scope">
          <el-link @click="openNewWindow('https://tronscan.org/#/address/'+scope.row.address)">
            {{ scope.row.address }}
          </el-link>
          &nbsp;
          <el-button
            size="mini"
            type="text"
            icon="el-icon-copy-document"
            v-clipboard:copy="scope.row.address"
            v-clipboard:success="copySuccess"
          >
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-refresh-right"
                     @click="handleSync(scope.row.address)"></el-button>
        </template>
      </el-table-column>
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <span v-if="scope.row.type === 1">共享</span>
          <span v-if="scope.row.type === 2">固定({{ scope.row.userId }})</span>
        </template>
      </el-table-column>
      <el-table-column label="TRX" align="center" prop="amtTrx" sortable="custom">
        <template slot-scope="scope">
          {{ scope.row.amtTrx }}
          <el-button
            size="mini"
            type="text"
            v-if="scope.row.amtTrx>5"
            @click="handleCollect(scope.row.address,1)"
          >归集
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="USDT" align="center" prop="amtUsdt" sortable>
        <template slot-scope="scope">
          {{ scope.row.amtUsdt }}
          <el-button
            size="mini"
            type="text"
            v-if="scope.row.amtUsdt>0"
            @click="handleCollect(scope.row.address,2)"
          >归集
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="启用" align="center" prop="enable">
        <template slot-scope="scope">
          <span v-if="scope.row.enable === 1">
            <el-button
              size="mini"
              type="text"
              @click="handleActivate(scope.row)"
            >激活地址
          </el-button>
          </span>
          <span v-if="scope.row.enable === 2" style="color: green">启用中</span>
          <span v-if="scope.row.enable === 3">已启用</span>
        </template>
      </el-table-column>
      <el-table-column label="启用时间" align="center" prop="enableTime" width="180"/>
      <el-table-column label="启用Hash" align="center" prop="enableHash" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-link type="primary" @click="openNewWindow('https://tronscan.org/#/transaction/'+scope.row.enableHash)">
            {{ scope.row.enableHash }}
          </el-link>
        </template>
      </el-table-column>
      <el-table-column label="使用状态" align="center" prop="used">
        <template slot-scope="scope">
          <div v-if="scope.row.type === 1">
            <span v-if="scope.row.used === 1">未使用</span>
            <span v-if="scope.row.used === 2" style="color:#1787ff;">使用中</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="使用时间" align="center" prop="usedTime" width="180"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />


    <!-- 添加或修改归集批次对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="类型" prop="type">
          <el-select
            v-model="form.type"
            placeholder="类型"
            clearable
            style="width: 240px"
          >
            <el-option
              v-for="dict in dict.type.sweep_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
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
  listAddress,
  getAddress,
  delAddress,
  addAddress,
  updateAddress,
  activate,
  sweep,
  syncBalance
} from "@/api/tron/address";
import {copySuccess, openNewWindow} from "@/utils/ruoyi";

export default {
  name: "Address",
  dicts: ['sweep_type'],
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
      // TRON地址表格数据
      addressList: [],
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
        address: null,
        type: null,
        userId: null,
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
    /** 查询TRON地址列表 */
    getList() {
      this.loading = true;
      listAddress(this.queryParams).then(response => {
        this.addressList = response.rows;
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
        address: null,
        privateKey: null,
        enable: null,
        enableTime: null,
        enableHash: null,
        occupy: null,
        occupyTime: null,
        occupyUserId: null
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
        address: null,
        type: null,
        userId: null,
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
      this.title = "添加TRON地址";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAddress(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改TRON地址";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAddress(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAddress(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除TRON地址编号为"' + ids + '"的数据项？').then(function () {
        return delAddress(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 激活账户 */
    handleActivate(row) {
      const data = {
        'address': row.address
      };
      activate(data).then(response => {
        this.$modal.msgSuccess("操作已提交，请稍后查看");
        setTimeout(() => {
          this.getList();
        }, 1000)
      });
    },
    /** 激活账户 */
    handleCollect(address, type) {
      const data = {
        'address': address,
        'type': type
      };
      sweep(data).then(response => {
        this.$modal.msgSuccess("操作已提交，请稍后查看");
        setTimeout(() => {
          this.getList();
        }, 1000)
      });
    },
    /** 激活账户 */
    handleSync(address) {
      const data = {
        'address': address
      };
      syncBalance(data).then(response => {
        this.$modal.msgSuccess("操作已提交，请稍后查看");
        setTimeout(() => {
          this.getList();
        }, 1000)
      });
    },
    sortChange(column) {
      if (column.order) {
        this.queryParams.isAsc = column.order
        this.queryParams.orderByColumn = column.prop
      } else {
        this.queryParams.orderByColumn = "id"
        this.queryParams.isAsc = "descending"
      }
      this.getList();
    },
  },
};
</script>
