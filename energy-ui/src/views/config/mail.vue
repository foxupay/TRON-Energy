<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <div class="grid-content bg-purple-light"></div>
      </el-col>
      <el-col :span="8">
        <div class="grid-content bg-purple-light">
          <el-form ref="form" :model="form" :rules="rules" label-width="100px" style="width: 600px;">
            <el-form-item label="SMTP地址" prop="host" style="width: 500px;">
              <el-input v-model="form.host" placeholder="请输入邮件服务器的SMTP地址，可选，默认为smtp."/>
            </el-form-item>
            <el-form-item label="SMTP端口" prop="port" style="width: 500px;">
              <el-input v-model="form.port" placeholder="请输入邮件服务器的SMTP端口，可选，默认25"/>
            </el-form-item>
            <el-form-item label="发件人" prop="from" style="width: 500px;">
              <el-input v-model="form.from" placeholder="请输入发件人"/>
            </el-form-item>
            <el-form-item label="用户名" prop="user" style="width: 500px;">
              <el-input v-model="form.user" placeholder="请输入用户名，默认为发件人邮箱前缀"/>
            </el-form-item>
            <el-form-item label="密码" prop="pass" style="width: 500px;">
              <el-input v-model="form.pass" placeholder="请输入密码/授权码"/>
            </el-form-item>
            <el-form-item label="SSL安全连接" prop="sslEnable" style="width: 500px;">
              <el-switch
                v-model="form.sslEnable"
                active-color="#13ce66"
                inactive-color="#ff4949"
                :active-value="1"
                :inactive-value="2">
              </el-switch>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer" style="text-align: center">
            <el-button type="primary" @click="submitForm">保 存</el-button>
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="grid-content bg-purple-light"></div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import {listMail, addMail, updateMail} from "@/api/config/mail";

export default {
  name: "Mail",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 邮件配置表格数据
      mailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        host: null,
        port: null,
        from: null,
        user: null,
        pass: null,
        sslEnable: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询邮件配置列表 */
    getList() {
      this.loading = true;
      listMail(this.queryParams).then(response => {
        this.mailList = response.rows;
        if (this.mailList.length > 0) {
          this.form = this.mailList[0];
        }
        this.loading = false;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMail(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMail(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

  }
};
</script>
<style>
.el-row {
  margin-bottom: 20px;

  &:last-child {
    margin-bottom: 0;
  }
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.row-bg {
  padding: 10px 0;
}
</style>
