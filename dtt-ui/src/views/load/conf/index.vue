<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col >
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="90px">
          <el-form-item label="业务系统" prop="business">
            <el-input
              v-model="queryParams.business"
              placeholder="请输入业务系统名称"
              clearable
              size="small"
              style="width: 200px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
         <el-form-item label="数据类型" prop="fileType">
             <el-input
               v-model="queryParams.fileType"
               placeholder="请输入数据类型名称"
               clearable
               size="small"
               style="width: 200px"
               @keyup.enter.native="handleQuery"
             />
           </el-form-item>
        <el-form-item label="HDFS路径" prop="hdfsPath">
             <el-input
               v-model="queryParams.hdfsPath"
               placeholder="请输入HDFS路径信息"
               clearable
               size="small"
               style="width: 200px"
               @keyup.enter.native="handleQuery"
             />
           </el-form-item>
        <el-form-item label="Hive表前缀" prop="tablePrefix">
             <el-input
               v-model="queryParams.tablePrefix"
               placeholder="请输入Hive表前缀信息"
               clearable
               size="small"
               style="width: 200px"
               @keyup.enter.native="handleQuery"
             />
           </el-form-item>
        <el-form-item label="Hive表名" prop="hiveTable">
             <el-input
               v-model="queryParams.hiveTable"
               placeholder="请输入Hive表信息"
               clearable
               size="small"
               style="width: 240px"
               @keyup.enter.native="handleQuery"
             />
           </el-form-item>
          <el-form-item label="创建时间">
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
        新增配置前操作：手动创建Hive表 【参考使用手册：https://confluence.cangoonline.net/display/cangoit/load-file】
        </p>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['load:conf:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['load:conf:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['load:conf:remove']"
            >删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>
        <el-table v-loading="loading" :data="confList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="编号" align="center" key="confId" prop="confId" v-if="columns[0].visible" width="60"/>
          <el-table-column label="业务系统" align="center" key="business" prop="business" v-if="columns[1].visible" :show-overflow-tooltip="true" width="100"/>
          <el-table-column label="数据类型" align="center" key="fileType" prop="fileType" v-if="columns[2].visible" :show-overflow-tooltip="true" width="160"/>
          <el-table-column label="Hive表前缀" align="center" key="tablePrefix" prop="tablePrefix" v-if="columns[3].visible" :show-overflow-tooltip="true" width="100"/>
          <el-table-column label="Hive表" align="center" key="hiveTable" prop="hiveTable" v-if="columns[4].visible" />
          <el-table-column label="HDFS路径" align="center" key="hdfsPath" prop="hdfsPath" v-if="columns[5].visible" />
          <el-table-column label="创建人" align="center" key="createBy" prop="createBy" v-if="columns[6].visible" width="80"/>
          <el-table-column label="更新人" align="center" key="updateBy" prop="updateBy" v-if="columns[7].visible" width="80"/>
          <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[8].visible" width="150">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="更新时间" align="center" prop="updateTime" v-if="columns[9].visible" width="160">
                      <template slot-scope="scope">
                        <span>{{ parseTime(scope.row.updateTime) }}</span>
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
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['load:conf:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['load:conf:remove']"
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

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="15">
            <el-form-item label="业务系统" prop="business">
              <el-input v-model="form.business" placeholder="请输入业务系统" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="15">
            <el-form-item label="数据类型" prop="fileType">
              <el-input v-model="form.fileType" placeholder="请输入数据类型" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
        <el-col :span="15">
            <el-form-item label="Hive表前缀" prop="tablePrefix">
              <el-input v-model="form.tablePrefix" placeholder="请输入hive表前缀" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
        <el-col :span="15">
            <el-form-item label="Hive表名" prop="hiveTable">
              <el-input v-model="form.hiveTable" placeholder="请输入hive表名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
        <el-col :span="15">
            <el-form-item label="HDFS路径" prop="hdfsPath">
              <el-input v-model="form.hdfsPath" placeholder="请输入HDFS路径"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFileConf, delFileConf, addFileConf, updateFileConf,getFileConf } from "@/api/load/conf";
export default {
  name: "fileConf",
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
      // 用户表格数据
      confList: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 50,
        business: undefined,
        fileType: undefined,
        tablePrefix: undefined,
        hdfsPath: undefined,
        hiveTable: undefined
      },
      // 列信息
      columns: [
        { key: 0, label: `编号`, visible: true },
        { key: 1, label: `业务系统`, visible: true },
        { key: 2, label: `数据类型`, visible: true },
        { key: 3, label: `Hive表前缀`, visible: true },
        { key: 4, label: `Hive表`, visible: true },
        { key: 5, label: `HDFS路径`, visible: true },
        { key: 6, label: `创建人`, visible: true },
        { key: 7, label: `更新人`, visible: true },
        { key: 8, label: `创建时间`, visible: true },
        { key: 9, label: `更新时间`, visible: true }
      ],
      // 表单校验
      rules: {
        business: [
          { required: true, message: "用户名称不能为空", trigger: "blur" }
        ],
        fileType: [
          { required: true, message: "用户昵称不能为空", trigger: "blur" }
        ],
        hiveTable: [
          { required: true, message: "用户密码不能为空", trigger: "blur" }
        ],
        hdfsPath: [
          { required: true, message: "用户密码不能为空", trigger: "blur" }
        ]
      }
    };
  },
  watch: {
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询配置列表 */
    getList() {
      this.loading = true;
      listFileConf(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.confList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        confId: undefined,
        business: undefined,
        fileType: undefined,
        hiveTable: undefined,
        tablePrefix: undefined,
        hdfsPath: undefined,
        remark: undefined
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
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.confId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      getFileConf().then(response => {
        this.open = true;
        this.title = "新增配置";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const confId = row.confId || this.ids;
      getFileConf(confId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改配置";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.confId != undefined) {
            updateFileConf(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFileConf(this.form).then(response => {
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
      const confIds = row.confId || this.ids;
      this.$modal.confirm('是否确认删除用户编号为"' + confIds + '"的数据项？').then(function() {
        return delFileConf(confIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
