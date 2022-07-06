<template>
  <div class="app-container">
    <el-row >
      <!--任务数据-->
      <el-col :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="任务名称" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入任务名称"
              clearable
              size="small"
              style="width: 200px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="运行状态" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="任务状态"
              clearable
              size="small"
              style="width: 200px"
            >
              <el-option
                v-for="dict in dict.type.sys_task_run_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="业务分类" prop="status">
                 <el-select
                   v-model="queryParams.status"
                   placeholder="业务分类"
                   clearable
                   size="small"
                   style="width: 200px"
                 >
                 <el-option
                     v-for="dict in dict.type.sys_business_type"
                     :key="dict.value"
                     :label="dict.label"
                     :value="dict.value"
                   />
                 </el-select>
           </el-form-item>
           <el-form-item label="数据层级" prop="status">
                  <el-select
                    v-model="queryParams.status"
                    placeholder="数据层级"
                    clearable
                    size="small"
                    style="width: 200px"
                  >
                  <el-option
                      v-for="dict in dict.type.sys_data_level"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
            </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="dateRange"
              size="small"
              style="width: 200px"
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

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
          <el-dropdown size="mini"  @command="(command) => handleNewCommand(command)" trigger="click">
              <el-button
               type="primary"
               plain
              icon="el-icon-plus"
              size="mini"
             >新增</el-button>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="Hive" icon="el-icon-circle-check">Hive SQL</el-dropdown-item>
                <el-dropdown-item command="Spark" icon="el-icon-circle-check">Spark任务</el-dropdown-item>
                <el-dropdown-item command="Shell" icon="el-icon-circle-check">Shell脚本</el-dropdown-item>
                <el-dropdown-item command="Python" icon="el-icon-circle-check">Python脚本</el-dropdown-item>
                <el-dropdown-item command="HDFS2MYSQL" icon="el-icon-circle-check">HDFS2MYSQL</el-dropdown-item>
                <el-dropdown-item command="MYSQL2HDFS" icon="el-icon-circle-check">MYSQL2HDFS</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          </el-col>
          <el-col :span="1.5">
          <el-button
              type="primary"
              plain
              icon="el-icon-copy-document"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:user:add']"
            >复制</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdateOwner"
              v-hasPermi="['system:user:edit']"
            >更改任务owner</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:user:edit']"
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
              v-hasPermi="['system:user:remove']"
            >删除</el-button>
          </el-col>

          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="编号" align="center" key="userId" prop="userId" v-if="columns[0].visible" />
          <el-table-column label="业务分类" align="center" key="userName" prop="userName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="数据层级" align="center" key="nickName" prop="nickName" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="名称" align="center" key="userName" prop="userName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="任务类型" align="center" key="nickName" prop="nickName" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="执行时间" align="center" key="deptName" prop="dept.deptName" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="状态" align="center" key="status" v-if="columns[5].visible">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="运行状态" align="center" key="deptName" prop="dept.deptName" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[6].visible">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope" >
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:user:edit']"
              >修改</el-button>
              <el-button
                   size="mini"
                   type="text"
                   icon="el-icon-video-play"
                   @click="handleUpdate(scope.row)"
                   v-hasPermi="['system:user:edit']"
              >执行</el-button>
              <el-button
                 type="text"
                 size="mini"
                 icon="el-icon-circle-close"
                 @click="handleUpdate(scope.row)"
                 v-hasPermi="['system:user:edit']"
                 >取消</el-button>
              <el-button
                type="text"
                size="mini"
                icon="el-icon-video-pause"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:user:edit']"
                >停止</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:user:remove']"
              >删除</el-button>
              <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['system:user:resetPwd', 'system:user:edit']">
                 <span class="el-dropdown-link">
                   <i class="el-icon-d-arrow-right el-icon--right"></i>详情
                 </span>
                 <el-dropdown-menu slot="dropdown">
                   <el-dropdown-item command="handleAuthRole" icon="el-icon-circle-check"
                     v-hasPermi="['system:user:edit']">任务详情</el-dropdown-item>
                 </el-dropdown-menu>
               </el-dropdown>
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
     <!-- 更改任务owner-->
     <el-dialog :title="owner.title" :visible.sync="owner.open" width="600px" append-to-body>
        <el-row >
            <!--用户数据-->
            <el-col :xs="24">
              <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
                <el-form-item label="用户" prop="userName">
                  <el-input
                    v-model="queryParams.userName"
                    placeholder="请输入用户名称"
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
              <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="50" align="center" />
                <el-table-column label="编号" align="center" key="userId" prop="userId"/>
                <el-table-column label="账号" align="center" key="userName" prop="userName" />
                <el-table-column label="姓名" align="center" key="nickName" prop="nickName" />
                <el-table-column label="部门" align="center" key="userName" prop="userName" />
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
       <div slot="footer" class="dialog-footer">
         <el-button type="primary" @click="submitFileForm">确 定</el-button>
         <el-button @click="owner.open = false">取 消</el-button>
       </div>
     </el-dialog>

    <!-- 添加或修改hive SQL配置对话框 -->
     <el-dialog :title="hive.title" :visible.sync="hive.open" width="750px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col >
            <el-form-item label="任务名称" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请输入任务名称" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col >
            <el-form-item label="corn" prop="corn">
                <el-input v-model="form.corn" placeholder="请输入CRON 表达式">
                  <template slot="append">
                    <el-button type="primary" @click="handleShowCron">
                      生成表达式
                      <i class="el-icon-time el-icon--right"></i>
                    </el-button>
                  </template>
                </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="重试次数" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入账号" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="重试间隔" prop="password">
              <el-input v-model="form.password" placeholder="请输入用户密码" type="password" maxlength="20" show-password/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="业务类型">
              <el-select v-model="form.postIds" placeholder="请选择">
                <el-option
                    v-for="dict in dict.type.sys_business_type"
                     :key="dict.value"
                     :label="dict.label"
                     :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据层级">
              <el-select v-model="form.roleIds" multiple placeholder="请选择">
                <el-option
                   v-for="dict in dict.type.sys_data_level"
                     :key="dict.value"
                     :label="dict.label"
                     :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
           <el-col :span="24">
             <el-form-item label="SQL">
               <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
             </el-form-item>
           </el-col>
        </el-row>
        <el-row>
           <el-col :span="24">
            <el-form-item label="前置SQL">
                <m-statement-list
                  @on-statement-list="_onPreStatements"
                  :statement-list="preStatements">
                </m-statement-list>
            </el-form-item>
           </el-col>
        </el-row>
        <el-row>
           <el-col :span="24">
             <el-form-item label="后置SQL">
                <m-statement-list
                  @on-statement-list="_onPostStatements"
                  :statement-list="postStatements">
                </m-statement-list>
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
        <el-button @click="hive.open = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="Cron表达式生成器" :visible.sync="openCron" append-to-body class="scrollbar" destroy-on-close>
       <crontab @hide="openCron=false" @fill="crontabFill" :expression="expression"></crontab>
    </el-dialog>

    <!-- 添加或修改脚本配置对话框 -->
     <el-dialog :title="script.title" :visible.sync="script.open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col >
            <el-form-item label="任务名称" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请输入任务名称" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col >
            <el-form-item label="corn" prop="corn">
                <el-input v-model="form.corn" placeholder="请输入CRON 表达式">
                  <template slot="append">
                    <el-button type="primary" @click="handleShowCron">
                      生成表达式
                      <i class="el-icon-time el-icon--right"></i>
                    </el-button>
                  </template>
                </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="重试次数" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入账号" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="重试间隔" prop="password">
              <el-input v-model="form.password" placeholder="请输入用户密码" type="password" maxlength="20" show-password/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="业务类型">
              <el-select v-model="form.postIds" placeholder="请选择">
                <el-option
                    v-for="dict in dict.type.sys_business_type"
                     :key="dict.value"
                     :label="dict.label"
                     :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据层级">
              <el-select v-model="form.roleIds" multiple placeholder="请选择">
                <el-option
                   v-for="dict in dict.type.sys_data_level"
                     :key="dict.value"
                     :label="dict.label"
                     :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
           <el-col :span="24">
             <el-form-item label="脚本">
               <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
             </el-form-item>
           </el-col>
        </el-row>
        <el-row>
           <el-col :span="24">
             <el-form-item label="自定义参数">
               <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
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
        <el-button @click="script.open = false">取 消</el-button>
      </div>
    </el-dialog>

   <!-- 添加或修改spark配置对话框 -->
     <el-dialog :title="spark.title" :visible.sync="spark.open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col >
            <el-form-item label="任务名称" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请输入任务名称" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col >
            <el-form-item label="corn" prop="corn">
                <el-input v-model="form.corn" placeholder="请输入CRON 表达式">
                  <template slot="append">
                    <el-button type="primary" @click="handleShowCron">
                      生成表达式
                      <i class="el-icon-time el-icon--right"></i>
                    </el-button>
                  </template>
                </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="重试次数" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入账号" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="重试间隔" prop="password">
              <el-input v-model="form.password" placeholder="请输入用户密码" type="password" maxlength="20" show-password/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="业务类型">
              <el-select v-model="form.postIds" placeholder="请选择">
                <el-option
                    v-for="dict in dict.type.sys_business_type"
                     :key="dict.value"
                     :label="dict.label"
                     :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据层级">
              <el-select v-model="form.roleIds" multiple placeholder="请选择">
                <el-option
                   v-for="dict in dict.type.sys_data_level"
                     :key="dict.value"
                     :label="dict.label"
                     :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
           <el-col :span="24">
             <el-form-item label="主函数">
               <el-input v-model="form.remark" type="textarea" placeholder="请填写主函数"></el-input>
             </el-form-item>
           </el-col>
        </el-row>
        <el-row>
           <el-col :span="24">
             <el-form-item label="jar包">
               <el-input v-model="form.remark" type="textarea" placeholder="请选择jar包路径"></el-input>
             </el-form-item>
           </el-col>
        </el-row>
        <el-row>
           <el-col :span="12">
             <el-form-item label="Driver内核数">
               <el-input v-model="form.remark" type="textarea" placeholder="1"></el-input>
             </el-form-item>
           </el-col>
          <el-col :span="12">
             <el-form-item label="Driver内存数">
               <el-input v-model="form.remark" type="textarea" placeholder="512M"></el-input>
             </el-form-item>
           </el-col>
        </el-row>
        <el-row>
           <el-col :span="12">
             <el-form-item label="Executor数量">
               <el-input v-model="form.remark" type="textarea" placeholder="2"></el-input>
             </el-form-item>
           </el-col>
          <el-col :span="12">
             <el-form-item label="Executor内存书">
               <el-input v-model="form.remark" type="textarea" placeholder="2G"></el-input>
             </el-form-item>
           </el-col>
        </el-row>
        <el-row>
           <el-col :span="12">
             <el-form-item label="Executor内核数">
               <el-input v-model="form.remark" type="textarea" placeholder="2"></el-input>
             </el-form-item>
           </el-col>
        </el-row>
        <el-row>
           <el-col :span="24">
             <el-form-item label="命令行参数">
               <el-input v-model="form.remark" type="textarea" placeholder="请输入命令行参数"></el-input>
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
        <el-button @click="spark.open = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser, resetUserPwd, changeUserStatus } from "@/api/system/user";
import { getToken } from "@/utils/auth";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import Crontab from '@/components/Crontab'
import mStatementList from '@/components/statementList/statementList'

export default {
  name: "User",
  dicts: ['sys_normal_disable', 'sys_user_sex','sys_task_run_status','sys_business_type','sys_data_level'],
  components: { Treeselect ,Crontab ,mStatementList},
  data() {
    return {
      // 是否显示Cron表达式弹出层
      openCron: false,
      // 传入的表达式
      expression: "",
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
      userList: null,
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      // 默认密码
      initPassword: undefined,
      // 日期范围
      dateRange: [],
      // 岗位选项
      postOptions: [],
      // 角色选项
      roleOptions: [],
      //前置SQL
      preStatements:[],
      //后置SQL
      postStatements:[],
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      //任务owner参数
      owner:{
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: "",
      },
      //hive SQL参数
      hive:{
        // 是否显示弹出层
        open: false,
        // 弹出层标题
        title: "",
      },
      spark:{
        // 是否显示弹出层
        open: false,
        // 弹出层标题
        title: "",
       },
      //shell、python脚本 参数
      script:{
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: "",
      },
      //数据同步参数
      sync:{
       // 是否显示弹出层
       open: false,
       // 弹出层标题
       title: "",
       },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 50,
        userName: undefined,
       // phoneNumber: undefined,
        status: undefined,
        deptId: undefined
      },
      // 列信息
      columns: [
        { key: 0, label: `用户编号`, visible: true },
        { key: 1, label: `用户名称`, visible: true },
        { key: 2, label: `用户昵称`, visible: true },
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `创建时间`, visible: true }
      ],
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "用户名称不能为空", trigger: "blur" },
          { min: 2, max: 20, message: '用户名称长度必须介于 2 和 20 之间', trigger: 'blur' }
        ],
        nickName: [
          { required: true, message: "用户昵称不能为空", trigger: "blur" }
        ],
        password: [
          { required: true, message: "用户密码不能为空", trigger: "blur" },
          { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' }
        ],
        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phoneNumber: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getConfigKey("sys.user.initPassword").then(response => {
      this.initPassword = response.msg;
    });
  },
  methods: {
      /**后置SQL数据*/
       _onPostStatements (a) {
          this.postStatements = a
        },
      /**返回前置SQL数据*/
      _onPreStatements (a) {
        this.preStatements = a
      },
    /** cron表达式按钮操作 */
     handleShowCron() {
        this.expression = this.form.corn;
        console.log(this.form.corn)
        this.openCron = true;
      },
     /** 确定后回传值 */
     crontabFill(value) {
       this.form.cron = value;
     },
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.handleQuery();
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.userName + '"用户吗？').then(function() {
        return changeUserStatus(row.userId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
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
        userId: undefined,
        deptId: undefined,
        userName: undefined,
        nickName: undefined,
        password: undefined,
        phoneNumber: undefined,
        email: undefined,
        sex: undefined,
        corn: undefined,
        status: "0",
        remark: undefined,
        postIds: [],
        roleIds: []
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
      this.ids = selection.map(item => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /**新增 动作触发*/
    handleNewCommand(command){
      switch (command) {
            case "Hive":
              this.handleHive();
              break;
            case "Spark":
              this.handleSpark();
              break;
            case "Shell":
              this.handleScript()
              break;
            case "Python":
               this.handleScript()
               break;
            case "HDFS2MYSQL":
               this.handleScript()
               break;
            case "MYSQL2HDFS":
               this.handleScript()
            default:
              break;
          }
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "Hive":
          this.handleHive(row);
          break;
        case "handleAuthRole":
          this.handleAuthRole(row);
          break;
        case "Spark":
          this.handleSpark(row);
          break;
        case "Shell":
          this.handleScript(row)
          break;
        case "Python":
           this.handleScript(row)
           break;
        case "HDFS2MYSQL":
           this.handleScript(row)
           break;
        case "MYSQL2HDFS":
           this.handleScript(row)
        default:
          break;
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      getUser().then(response => {
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.open = true;
        this.title = "添加用户";
        this.form.password = this.initPassword;
      });
    },
    /** 更改任务owner */
    handleUpdateOwner(row) {
     this.owner.title = "修改任务owner";
     this.owner.open = true;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const userId = row.userId || this.ids;
      getUser(userId).then(response => {
        this.form = response.data;
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.form.postIds = response.postIds;
        this.form.roleIds = response.roleIds;
        this.open = true;
        this.title = "修改用户";
        this.form.password = "";
      });
    },
    /** hive 弹窗 */
    handleHive() {
      this.hive.open = true;
      this.hive.title = "Hive SQL"
    },
    /** 分配角色操作 */
    handleAuthRole: function() {
      const userId = row.userId;
      this.$router.push("/system/user-auth/role/" + userId);
    },
    /** Spark 弹窗 */
    handleSpark: function() {
        this.spark.open = true;
        this.spark.title = "Spark任务"
    },
    /**shell、python脚本弹框*/
    handleScript: function() {
        this.script.open = true;
        this.script.title = "脚本任务"
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.userId != undefined) {
            updateUser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addUser(this.form).then(response => {
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
      const userIds = row.userId || this.ids;
      this.$modal.confirm('是否确认删除用户编号为"' + userIds + '"的数据项？').then(function() {
        return delUser(userIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('system/user/importTemplate', {
      }, `user_template_${new Date().getTime()}.xlsx`)
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
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
