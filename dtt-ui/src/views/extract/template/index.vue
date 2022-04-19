<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="关键字" prop="templateName">
            <el-input
              v-model="queryParams.templateName"
              placeholder="请输入关键字"
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
       <!-- <el-row :gutter="10" class="mb8">
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row> -->

        <el-table v-loading="loading" :data="templateList">
          <el-table-column label="编号" align="center" key="id" prop="id" width="100"/>
          <el-table-column label="模板名" align="center" key="templateName" prop="templateName" width="100"/>
          <el-table-column label="IT负责人" align="center" key="handler" prop="handler" width="100"/>
          <el-table-column label="需求方" align="center" key="demandSide" prop="demandSide"  width="150"/>
          <el-table-column label="备注" align="center" key="remark" prop="remark"/>
          <el-table-column
            label="操作"
            align="center"
            width="80"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope" >
              <el-col :span="1.5">
                <el-button
                  type="warning"
                  plain
                  icon="el-icon-download"
                  size="mini"
                  @click="handleExport(scope.row)"
                  v-hasPermi="['extract:template:export']"
                >导出</el-button>
              </el-col>
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
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getUserTemplates } from "@/api/extract/conf";
import {formatDate,formatDates} from '@/utils/dateFormate'

export default {
  name: "templates",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户表格数据
      templateList: null,
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 50,
        templateName: undefined,
        templateSql:undefined
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询模板列表 */
    getList() {
      this.loading = true;
      getUserTemplates(this.addDateRange(this.queryParams)).then(response => {
          this.templateList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
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
    /** 导出按钮操作*/
    handleExport(row) {
         this.queryParams.templateName = row.templateName;
         this.queryParams.templateSql = row.templateSql;
         this.download('/extract/conf/export/',{
           ...this.queryParams
         }, `${row.templateName}_${formatDates(new Date(),'yyyyMMdd')}.xlsx`)
    }
  }
};
</script>
