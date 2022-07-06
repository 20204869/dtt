<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--数据源-->
      <el-col>
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="用户账户" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入用户账户"
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
              v-hasPermi="['hive:config:add']"
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
              v-hasPermi="['hive:config:edit']"
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
              v-hasPermi="['hive:config:delete']"
            >删除</el-button>
          </el-col>
        </el-row>
        <el-table v-loading="loading" :data="hiveUserList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="编号" align="center" key="id" prop="id" />
          <el-table-column label="用户账户" align="center" key="userName" prop="userName" />
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
          <el-col>
            <el-form-item label="用户账户" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入用户账户" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
                  <el-col :span="24">
                    <el-form-item label="连接参数" prop="connectionParams">
                      <el-input
                      v-model="form.connectionParams"
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
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { ListHiveUser,addHiveUser,updateHiveUser ,getHiveUser ,delHiveUser ,connectTest} from "@/api/query/hiveuser";
import mTooltipsJSON from '@/components/tooltipsJSON/tooltipsJSON'

export default {
  name: "HiveUser",
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
      // 关系数据列表
      hiveUserList:[],
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
        userName: undefined
      },
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "用户名称不能为空", trigger: "blur" }
        ],
        connectionParams: [
        { required: true, message: "数据库连接信息不能为空", trigger: "blur" }
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
      ListHiveUser(this.addDateRange(this.queryParams)).then(response => {
          this.hiveUserList = response.rows;
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
        userName: undefined,
        connectionParams: undefined
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
      getHiveUser().then(response => {
        this.open = true;
        this.title = "新增关系";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getHiveUser(id).then(response => {
      console.log(response.data)
        this.form = response.data;
        this.open = true;
        this.title = "更新关系";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateHiveUser(this.form.id,this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
          addHiveUser(this.form).then(response => {
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
      const id = row.id || this.ids;
      this.$modal.confirm('是否确认删除编号为"' + id + '"的数据项？').then(function() {
        return delHiveUser(id);
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
