<template>
  <div class="app-container">
    <h4 class="form-header h4">基本信息</h4>
    <el-form ref="form" :model="form" label-width="80px">
      <el-row>
        <el-col :span="8" :offset="2">
          <el-form-item label="模板名" prop="templateName">
            <el-input v-model="form.templateName" disabled />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <h4 class="form-header h4">用户信息</h4>
    <el-table v-loading="loading" :row-key="getRowKey" @row-click="clickRow" ref="table" @selection-change="handleSelectionChange" :data="users.slice((pageNum-1)*pageSize,pageNum*pageSize)">
      <el-table-column label="序号" type="index" align="center">
        <template slot-scope="scope">
          <span>{{(pageNum - 1) * pageSize + scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column type="selection" :reserve-selection="true" width="55"></el-table-column>
      <el-table-column label="编号" align="center" prop="userId" />
      <el-table-column label="用户账号" align="center" prop="userName" />
      <el-table-column label="姓名" align="center" prop="nickName" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="pageNum" :limit.sync="pageSize" />

    <el-form label-width="100px">
      <el-form-item style="text-align: center;margin-left:-120px;margin-top:30px;">
        <el-button type="primary" @click="submitForm()">提交</el-button>
        <el-button @click="close()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getUserTemplate,updateUserTemplate } from "@/api/extract/conf";

export default {
  name: "UserTemplate",
  data() {
    return {
       // 遮罩层
      loading: true,
      // 分页信息
      total: 0,
      pageNum: 1,
      pageSize: 20,
      // 选中角色编号
      userIds:[],
      // 用户信息信息
      users: [],
      // 用户信息
      form: {}
    };
  },
  created() {
    const id = this.$route.params && this.$route.params.id;
    if (id) {
      this.loading = true;
      getUserTemplate(id).then((response) => {
        this.form = response.conf;
        this.users = response.users;
        this.total = this.users.length;
        this.$nextTick(() => {
          this.users.forEach((row) => {
            if (row.flag) {
              this.$refs.table.toggleRowSelection(row);
            }
          });
        });
        this.loading = false;
      });
    }
  },
  methods: {
    /** 单击选中行数据 */
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.userIds = selection.map((item) => item.userId);
    },
    // 保存选中的数据编号
    getRowKey(row) {
      return row.roleId;
    },
    /** 提交按钮 */
    submitForm() {
      const id = this.form.id;
      const userIds = this.userIds.join(",");
      updateUserTemplate({ id: id, userIds: userIds }).then((response) => {
        this.$modal.msgSuccess("分配成功");
        this.close();
      });
    },
    /** 关闭按钮 */
    close() {
      const obj = { path: "/extract/conf" };
      this.$tab.closeOpenPage(obj);
    },
  },
};
</script>
