<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--数据源-->
      <el-col>
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="关键字" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入关键字名称"
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

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['datasource:datasource:add']"
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
              v-hasPermi="['datasource:datasource:edit']"
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
              v-hasPermi="['datasource:datasource:delete']"
            >删除</el-button>
          </el-col>
        </el-row>
        <el-table v-loading="loading" :data="dataSourceList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="编号" align="center" key="id" prop="id" />
          <el-table-column label="数据源名称" align="center" key="name" prop="name" />
          <el-table-column label="类型" align="center" key="type" prop="type" />
          <el-table-column label="数据源参数" align="center" key="connectionParams" prop="connectionParams">
          <template slot-scope="scope">
            <div>
              <m-tooltips-JSON :connectionParams="scope.row.connectionParams" :id="scope.row.id">
                <span slot="reference">
                  <el-button size="small" type="text">点击查看</el-button>
                </span>
            </m-tooltips-JSON>
            </div>
          </template>
        </el-table-column>
          <el-table-column label="创建人" align="center" key="createBy" prop="createBy"/>
          <el-table-column label="更新人" align="center" key="updateBy" prop="updateBy"/>
          <el-table-column label="创建时间" align="center" prop="createTime">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="更新时间" align="center" prop="updateTime">
            <template slot-scope="scope">
             <span>{{scope.row.updateTime}}</span>
            </template>
          </el-table-column>
        <el-table-column label="备注" align="center" prop="remark" >
        <template slot-scope="scope">
            <span>{{scope.row.remark}}</span>
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
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="数据源名" prop="name">
              <el-input v-model="form.name" placeholder="请输入数据源名称" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
                      <el-form-item label="类型">
                        <el-select v-model="form.type" placeholder="请选择">
                          <el-option
                            v-for="dict in dict.type.sys_datasource_type"
                            :key="dict.value"
                            :label="dict.label"
                            :value="dict.label"
                          ></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="host" prop="host">
              <el-input v-model="form.host" placeholder="请输入IP地址" maxlength="60" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="端口号" prop="port">
              <el-input v-model="form.port" placeholder="请输入端口号" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户名称" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入用户名称" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" placeholder="请输入密码" type="password" maxlength="20" show-password/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
                      <el-form-item label="数据库" prop="database">
                        <el-input v-model="form.database" placeholder="请输入数据库" maxlength="20"/>
           </el-form-item>
           </el-col>
        </el-row>
        <el-row>
                  <el-col :span="24">
                    <el-form-item label="连接参数">
                      <el-input
                      v-model="form.other"
                      type="textarea"
                      :autosize="{minRows:2}"
                       size="small"
                      placeholder='请输入内容格式{"key1":"value1","key2":"value2"}'></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.note" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="success" @click="_testConnect()">测试连接</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { ListDataSource,addDataSource,updateDataSource ,getDataSource ,delDataSource ,connectTest} from "@/api/query/datasource";
import mTooltipsJSON from '@/components/tooltipsJSON/tooltipsJSON'

export default {
  name: "DataSource",
  dicts: ['sys_datasource_type'],
  components: {
      mTooltipsJSON
    },
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
      dataSourceList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      testLoading: false,
      // 日期范围
      dateRange: [],
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined
      },
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "用户名称不能为空", trigger: "blur" },
          { min: 2, max: 60, message: '用户名称长度必须介于 2 和 60 之间', trigger: 'blur' }
        ],
        database: [
          { required: true, message: "数据库不能为空", trigger: "blur" }
        ],
        name: [
           { required: true, message: "数据源名称不能为空", trigger: "blur" }
         ],
        host: [
               {
               pattern:/^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
               message:"请输入正确的IP地址",
               required: true,
               trigger: "blur" }
              ],
       port: [{ required: true, message: "端口号不能为空", trigger: "blur" }
           ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询数据源列表 */
    getList() {
      this.loading = true;
      ListDataSource(this.addDateRange(this.queryParams)).then(response => {
          this.dataSourceList = response.rows;
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
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.id = data.id;
      this.getList();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        name: undefined,
        type: undefined,
        host: undefined,
        password: undefined,
        port: undefined,
        database: undefined,
        userName: undefined,
        note: undefined,
        other: undefined
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      getDataSource().then(response => {
        this.open = true;
        this.title = "数据源新增";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getDataSource(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "更新数据源配置";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateDataSource(this.form.id,this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
          addDataSource(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
             });
          }
        }
      });
    },
    /**测试连接按钮*/
    _testConnect:function(){
     this.$refs["form"].validate(valid => {
             if (valid) {
                 connectTest(this.form).then(response => {
                   this.$modal.msgSuccess("连接成功");
                   this.open = true;
                   this.getList();
                 });
             }
           });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id || this.ids;
      this.$modal.confirm('是否确认删除编号为"' + id + '"的数据项？').then(function() {
        return delDataSource(id);
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
