<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--上传文件数据-->
      <el-col>
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="文件名称" prop="fileName">
            <el-input
              v-model="queryParams.fileName"
              placeholder="请输入文件名称"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="业务类型" prop="confId">
                      <el-select
                        v-model="queryParams.confId"
                        placeholder="业务类型"
                        clearable
                        size="small"
                        style="width: 240px"
                      >
                        <el-option
                          v-for="item in confOptions"
                          :key="item.confId"
                          :label="item.fileType"
                          :value="item.confId"
                        />
                      </el-select>
          </el-form-item>
          <el-form-item label="上传时间">
            <el-date-picker
              v-model="dateRange"
              size="small"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <p style="font-size:18px;color:red">
        导入Excel要求：1.Excel有首行字段  2.Excel单元格内不允许有换行符  3.Excel不存在合并单元格  4.文件名要包含日期 例如XXX-20221212.xlsx 5.若导入的Excel存在增删列请通知大数据相关人员
        </p>
        <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['load:file:load']"
            >导入</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['load:file:delete']"
            >删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="fileList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="编号" align="center" key="fileId" prop="fileId" v-if="columns[0].visible" width="80"/>
          <el-table-column label="业务类型" align="center" prop="fileConf.fileType" v-if="columns[4].visible"/>
          <el-table-column label="文件名" align="center" key="fileName" prop="fileName" v-if="columns[1].visible" :show-overflow-tooltip="true"/>
          <el-table-column label="上传人" align="center" key="loadUser" prop="loadUser" v-if="columns[2].visible" :show-overflow-tooltip="true"/>
          <el-table-column label="上传时间" align="center" prop="loadTime" v-if="columns[3].visible" >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.loadTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['load:file:delete']"
              >删除</el-button>
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

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="业务类型">
           <el-select v-model="upload.confId" placeholder="请选择">
              <el-option
               v-for="item in confOptions"
                       :key="item.confId"
                       :label="item.fileType"
                       :value="item.confId"
               ></el-option>
               </el-select>
       </el-form-item>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls, .csv"
        :headers="upload.headers"
        :action="upload.url + '?confId=' + upload.confId"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <!--<div class="el-upload__tip text-center" slot="tip">
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>-->
      </el-upload>
    </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFile,delFile } from "@/api/load/load";
import { getConf } from "@/api/load/conf";
import { getToken } from "@/utils/auth";

export default {
  name: "File",
  dicts: ['load_business_type'],
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
      //配置信息
      confOptions: undefined,
      // 总条数
      total: 0,
      // 用户表格数据
      fileList: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 表单参数
      form: {},
      // 用户导入参数
      upload: {
        // 是否显示弹出层（导入）
        open: false,
        // 弹出层标题（导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 业务类型
        confId:"",
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/load/file/save"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 50,
        fileName:undefined,
        fileType:undefined
      },
      // 列信息
      columns: [
        { key: 0, label: `编号`, visible: true },
        { key: 1, label: `文件名`, visible: true },
        { key: 2, label: `上传人`, visible: true },
        { key: 3, label: `上传时间`, visible: true },
        { key: 4, label: `业务类型`, visible: true }
      ],
    };
  },
  watch: {
  },
  created() {
    this.getList();
    this.getConfList();
  },
  methods: {
    /** 查询上传文件列表 */
    getList() {
      this.loading = true;
      listFile(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.fileList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询配置信息 */
        getConfList() {
          getConf().then(response => {
            this.confOptions = response.data;
          });
        },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.fileId = data.id;
      this.getList();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.fileId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const fileIds = row.fileId || this.ids;
      this.$modal.confirm('是否确认删除文件编号为"' + fileIds + '"的数据项？').then(function() {
        return delFile(fileIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
        handleExport() {
          this.download('load/file/export', {
            ...this.queryParams
          }, `user_${new Date().getTime()}.xlsx`)
        },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "文件导入";

      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('load/file/importTemplate', {
        ...this.queryParams
      }, `file_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
