<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="归集批次号" prop="sweepNo">
        <el-input
          v-model="queryParams.sweepNo"
          placeholder="请输入归集批次号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务状态">
        <el-select v-model="queryParams.status" placeholder="状态" clearable>
          <el-option label="请选择状态" value=""></el-option>
          <el-option label="未导入" value="1"></el-option>
          <el-option label="未处理" value="2"></el-option>
          <el-option label="处理中" value="3"></el-option>
          <el-option label="处理完成" value="4"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['sweep:batch:add']"
        >创建归集
        </el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="batchList">
      <el-table-column label="任务批次" align="center" prop="sweepNo"/>
      <el-table-column label="任务名称" align="center" prop="title" min-width="200"/>
      <el-table-column label="任务类型" align="center" prop="type">
        <template slot-scope="scope">
          <span v-if="scope.row.type === 1">TRX</span>
          <span v-if="scope.row.type === 2">USDT</span>
        </template>
      </el-table-column>
      <el-table-column label="地址数量" align="center" prop="addressNumber"/>
      <el-table-column label="归集数量" align="center" prop="sweepNumber"/>
      <el-table-column label="成功数量" align="center" prop="successNumber"/>
      <el-table-column label="任务状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 1" type="warning">未导入</el-tag>
          <el-tag v-else-if="scope.row.status === 2" type="danger">未处理</el-tag>
          <el-tag v-else-if="scope.row.status === 3">处理中</el-tag>
          <el-tag v-else-if="scope.row.status === 4" type="success">处理完成</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-upload2"
            @click="handleAddAddress(scope.row)"
            v-if="scope.row.status === 1"
          >导入地址
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

    <!-- 添加或修改归集批次对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="代币类型" prop="type">
          <el-radio v-model="form.type" label="1" @input="updateNumber()">TRX</el-radio>
          <el-radio v-model="form.type" label="2" @input="updateNumber()">USDT</el-radio>
        </el-form-item>
        <el-form-item label="最小数量" prop="minAmount">
          <el-input
            type="number"
            v-model="form.minAmount"
            placeholder="请输入归集最小数量"
            style="width: 300px"
            :min="form.type==='1'?2:0"
          />
          <br>
          TRX：默认预留1个TRX作为手续费
          <br>实际归集数量为[ 余额 - {{ form.minAmount }} TRX]
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
import {addBatch, importAddress, listBatch, updateBatch} from "@/api/sweep/batch";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Batch",
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
      // 归集批次表格数据
      batchList: [],
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
        status: null,
      },
      // 表单参数
      form: {
        type: '1',
        minAmount: 5
      },
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询归集批次列表 */
    getList() {
      this.loading = true;
      listBatch(this.queryParams).then(response => {
        this.batchList = response.rows;
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
        type: '1',
        minAmount: 5
      }
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
        status: null,
      }
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新建归集任务";
    },
    /**
     * 导入地址
     */
    handleAddAddress(row) {
      /** 删除按钮操作 */
      this.$modal.confirm('确认一键导入？').then(function () {
        return importAddress(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("导入成功");
      }).catch(() => {
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBatch(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBatch(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    //选中代币类型，更新最低数量
    updateNumber() {
      if (this.form.type === '1' && (this.form.minAmount === '' || Number(this.form.minAmount) <= 5)) {
        this.form.minAmount = 5
      }
      if (this.form.type === '2' && (this.form.minAmount === '' || Number(this.form.minAmount) <= 0)) {
        this.form.minAmount = 0
      }
    },
  }
};
</script>
