<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="80px">
      <el-row>
        <el-col :span="8" :offset="2">
          <el-form-item label="姓名" prop="nickName">
            <el-input v-model="form.nickName" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="8" :offset="2">
          <el-form-item label="账号" prop="userName">
            <el-input  v-model="form.userName" disabled />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <h4 class="form-header h4">Tableau</h4>
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="project" prop="projectName">
            <el-input
              v-model="queryParams.projectName"
              placeholder="请输入项目名"
              clearable
              size="small"
              style="width: 200px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
    <el-table v-loading="loading" :row-key="getRowKey" @row-click="clickRow" ref="table" @selection-change="handleSelectionChange" :data="tableaus.slice((pageNum-1)*pageSize,pageNum*pageSize)">
      <el-table-column label="序号" type="index" align="center">
        <template slot-scope="scope">
          <span>{{(pageNum - 1) * pageSize + scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column type="selection" :reserve-selection="true" width="55"></el-table-column>
      <el-table-column label="workbook(工作簿)编号" align="center" prop="workbookId"/>
      <el-table-column label="workbook(工作簿)名称" align="center" prop="workbookName"/>
      <el-table-column label="project(项目)编号" align="center" prop="projectId"/>
      <el-table-column label="project(项目)名称" align="center" prop="projectName"/>
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
import { getAuthTableau, updateAuthTableau } from "@/api/system/user";

export default {
  name: "AuthTableau",
  data() {
    return {
       // 遮罩层
      loading: true,
      // 分页信息
      total: 0,
      pageNum: 1,
      pageSize: 30,
      // 显示搜索条件
      showSearch: true,
      // 选中tableau
      workbookIds:[],
      projectIds:[],
      // 角色信息
      tableaus: [],
      // 用户信息
      form: {},
      // 查询参数
      queryParams: {
        projectName: undefined
      },
    };
  },
  created() {
    const userId = this.$route.params && this.$route.params.userId;
    if (userId) {
      this.loading = true;
      getAuthTableau(userId,this.addDateRange(this.queryParams)).then((response) => {
        this.form = response.user;
        this.tableaus = response.tableaus;
        this.total = this.tableaus.length;
        this.$nextTick(() => {
          this.tableaus.forEach((row) => {
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
    /** 查询tableau 列表 */
     getList() {
      const userId = this.$route.params && this.$route.params.userId;
          if (userId) {
            this.loading = true;
            getAuthTableau(userId,this.addDateRange(this.queryParams)).then((response) => {
              this.form = response.user;
              this.tableaus = response.tableaus;
              this.total = this.tableaus.length;
              this.$nextTick(() => {
                this.tableaus.forEach((row) => {
                  if (row.flag) {
                    this.$refs.table.toggleRowSelection(row);
                  }
                });
              });
              this.loading = false;
            });
          }
     },
    /** 重置按钮操作 */
     resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
    /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();

      },
    /** 单击选中行数据 */
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.workbookIds = selection.map((item) => item.workbookId);
      this.projectIds = selection.map((item) => item.projectId);
    },
    // 保存选中的数据编号
    getRowKey(row) {
      return row.workbookId;
    },
    /** 提交按钮 */
    submitForm() {
      const userId = this.form.userId;
      const workbookIds = this.workbookIds.join(",");
      const projectIds = this.projectIds.join(",");
      updateAuthTableau({ userId: userId, workbookIds: workbookIds,projectIds:projectIds }).then((response) => {
        this.$modal.msgSuccess("授权成功");
        this.close();
      });
    },
    /** 关闭按钮 */
    close() {
      const obj = { path: "/system/user" };
      this.$tab.closeOpenPage(obj);
    },
  },
};
</script>
