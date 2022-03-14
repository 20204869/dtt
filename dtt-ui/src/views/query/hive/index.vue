<template>
<div>
    <div class="outer">
      <div class="right">
        <div style="display: flex;">
           <img @click="ChangeDown()" src="@/assets/images/left.png" />
           <h1>{{dbNameShow}}</h1>
        </div>
        <el-row>
         <el-col>
          <el-input
            v-model="name"
            placeholder="请输入库名"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
            />
        <div style="height:765px;width:350px;overflow:scroll;">
          <el-tree
            :data="DbOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
        </el-col>
       </el-row>
      </div>
      <div class="center" style="overflow:scroll;">
        <div style="padding: 10px 10px 0;margin-bottom: 20px">
          <el-button
            v-if="sqlExecuting"
            primary
            v-on:click="cancelExecutorSql"
            type="primary"
            plain
            size="medium"
            icon="el-icon-video-pause">取消执行</el-button>
          <el-tooltip
            v-else
            effect="dark"
            content="Ctrl+R、Ctrl+Enter"
            placement="top">
            <el-button
              v-on:click="doExecutorSql"
              size="medium"
              type="primary"
              icon="el-icon-video-play">执行</el-button>
          </el-tooltip>
          <el-button
            icon="el-icon-brush"
            type="primary"
            size="medium"
            @click="autoFormatSelection">SQL格式化</el-button>
          <el-dropdown style="margin-left: 10px" @command="handleCommand">
            <el-button size="medium" type="primary">
              <i class="el-icon-arrow-down el-icon--right el-icon-download"></i>下载
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="all-select">导出CSV</el-dropdown-item>
              <el-dropdown-item command="all-excel">导出Excel</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
       <!-- <div class="sql-box">
          <SqlEditor
          ref="sqleditor"
          :value="basicInfoForm.sqlMain"
          @changeTextarea="changeTextarea($event)"
          />
       </div>-->
      <div class="editor">
          <codemirror v-model="code" :options="cmOptions" @ready="onCmReady" ref="codemirror"></codemirror>
          <!-- 负责拖拽 -->
          <!-- 当鼠标指针移动到元素上方,并按下鼠标左键时,会发生 mousedown 事件 -->
          <div class="editor__drag">
            <div class="arrow-v" @mousedown="changeEditorHeight">
              <img src="@/assets/images/editor-drag.png" style="width: 1.5rem; height: 1.5rem; vertical-align: middle;" />
            </div>
          </div>
        </div>
      <div class="btn_switch">
               <button class="btn_anniu" @click="change(0)" :class="{ newStyle:0===number}">查询结果</button>
               <button class="btn_anniu" @click="change(1)" :class="{ newStyle:1===number}">历史查询</button>
      </div>
      <div style="width:100%;height:5px;background:#ededee;"></div>
      <template v-if="0===number">
           <span>查询结果</span>
      </template>
      <template v-else-if="1===number">
            <span>历史查询</span>
      </template>
      </div>
      <div class="left">

      <div style="height:880px;width:350px;overflow:scroll;">
          <el-tree
            :data="cols"
            :props="colsProps"
            :expand-on-click-node="false"
            ref="tree"
            default-expand-all
          />
        </div>
      </div>
  </div>
</div>
</template>
<script>
import { treeselect ,getTableList ,colList } from "@/api/map/meta";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import fileSaver from "file-saver";
import xlsx from "xlsx";

import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/theme/dracula.css';
import 'codemirror/mode/sql/sql.js' // 对于sql模式的支持
import 'codemirror/keymap/sublime.js'
import 'codemirror/addon/hint/show-hint.css'
import 'codemirror/addon/hint/show-hint.js'
import 'codemirror/addon/hint/sql-hint.js'

import sqlFormatter from "sql-formatter";
import SqlEditor from "@/components/SqlEditor";
const MIN_HEIGHT = 200
export default {
  name: "Table",
  components: {
    Treeselect,
    SqlEditor,
    codemirror
   },
  data() {
    return {
      number:0,
      code: "",
      cmOptions: {
        mode: "text/x-sql", //实现Java代码高亮
        lineNumbers: true,
        theme: "dracula",
        keyMap: "sublime",
        extraKeys: {"Ctrl":"autocomplete"},
        lineWrapping: true, //是否换行
        foldGutter: true, //是否折叠
        gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"], //添加行号栏，折叠栏
      },
      position_initail_y: 0,
      position_y: 0,
      //判断是否取消SQL执行
      sqlExecuting: false,
      basicInfoForm: {
            sqlMain: "",
          },
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 树选项
      DbOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 库名名称
      name: undefined,
      dbName: undefined,
      tableName: undefined,
      // 表单参数
      cols: undefined,
      dbNameShow:"Hive",
      defaultProps: {
        label: "dbName"
      },
      colsProps: {
       label: "colName"
      },
      // 查询参数
      queryParams: {
        dbId: undefined,
        tableId: undefined
      },
    };
  },
 computed: {
     codemirror() {
       return this.$refs.codemirror.codemirror
     },
   },
   mounted() {
     this.$nextTick(() => {
       this.codemirror.setSize('height', MIN_HEIGHT)
       // 获取当前编辑器的初始高度
       let height= this.$refs.codemirror.$el.offsetHeight
       console.log('编辑器初始高度：', height)
     })
   },
  watch: {
    // 根据名称筛选库
    name(val) {
      if (this.defaultProps.label === 'dbName') {
        this.DbOptions = this.$refs.tree.data.filter((item) =>{
          return item.dbName.indexOf(val) >= 0
         })
      } else if (this.defaultProps.label === 'tableName') {
        this.DbOptions = this.$refs.tree.data.filter((item) =>{
          return item.tableName.indexOf(val) >= 0
         })
      }
      //删除查询参数渲染数据
      if(val === '' || val === null || val === undefined){
        if (this.defaultProps.label === 'dbName'){
              treeselect().then(response => {
                  this.DbOptions = response.data;
                  this.dbNameShow = 'Hive';
              });
         } else if (this.defaultProps.label === 'tableName'){
             getTableList(this.queryParams.dbId).then(response => {
                   this.DbOptions = response.table;
                 });
         }
      }
    }
  },
  created() {
    this.getTreeselect();
  },
   methods: {
      //切换按钮
      change:function(index){
         this.number=index;
      },
   autoFormatSelection() {
         let str = sqlFormatter.format(this.codemirror.getValue(), {language: 'sql'});
         this.codemirror.setValue(str);
       },

    onCmReady(cm) {
      cm.on('keypress', () => {
          cm.showHint()
      })
    },
    getEditorHeight() {
      return this.$refs.codemirror.$el.offsetHeight
    },
    changeEditorHeight(event) {
      // 防止触发drag事件
      if (event.stopPropagation) event.stopPropagation();
      if (event.preventDefault) event.preventDefault();
      // 1. 记录当前坐标Y的位置，用于随时更新高度
      this.position_initail_y = event.y
      // 注册鼠标滑动事件
      document.body.addEventListener('mousemove', this.changeEditorMove)
      // mouseup会消失，具体表现就是，鼠标按下，抬起后，编辑器高度依旧跟着改变，如下摘录自简书
      // 触发了浏览器的 drag 操作，导致mouseup丢失。
      // 由于鼠标离开了操作的区域，触发了mouseleave导致mouseup丢失。
      document.body.addEventListener('mouseup', this.releaseMouse)
    },
    releaseMouse() {
      // 当鼠标抬起，此时不再计算实时的距离，清除刚才的事件
      document.body.removeEventListener('mousemove', this.changeEditorMove)
      document.body.removeEventListener('mouseup', this.releaseMouse)
    },
    changeEditorMove(event) {
      // 1. 记录鼠标最后停止的位置
      this.position_y = event.y
      // 2. 实时更新当前已经移动了多少
      const moveLength = Math.max(MIN_HEIGHT, MIN_HEIGHT + (this.position_y - this.position_initail_y))
      this.codemirror.setSize('height', moveLength)
    },
    getSelectedRange() {
        return { from: this.codemirror.getCursor(true), to: this.codemirror.getCursor(false) };
    },
    formatterSql() {
      let str = sqlFormatter.format(this.codemirror.getValue(), {language: 'sql'});
      this.codemirror.setValue(str);
    },
      //下载查询结果
    handleCommand(command) {
      //TODO 下载查询数据
      },
        //执行SQL
      doExecutorSql() {
           //TODO SQL执行接口
             },
      //取消执行SQL
      cancelExecutorSql() {
             this.sqlExecuting = false;
             //TODO 调后台接口
           },
      ChangeDown () {
        treeselect().then(response => {
          this.DbOptions = response.data;
          this.dbNameShow = 'Hive';
          this.defaultProps.label = 'dbName';
        });
       },
    changeTextarea(val) {
        this.$set(this.basicInfoForm, "sqlMain", val);
      },
      formaterSql(val) {
        let dom = this.$refs.sqleditor;
        dom.editor.setValue(sqlFormatter.format(dom.editor.getValue()));
      },
      /** 关闭按钮 */
        close() {
          this.$store.dispatch("tagsView/delView", this.$route);
          this.$router.push({ path: "/map/meta" });
        },
    /** 查询库列表 */
    getTreeselect() {
      treeselect().then(response => {
        this.DbOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.dbName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.dbId = data.dbId;
      this.dbNameShow = data.dbName
      if(data.tableName){

      }else {
        this.defaultProps.label = 'tableName';
        getTableList(data.dbId).then(response => {
          this.DbOptions = response.table;
        });
      }
      if(this.defaultProps.label === 'tableName'){
          if(data.tableId === undefined){

          }else{
           colList(data.tableId).then(response => {
               this.cols = response.cols;
             });
          }
      }
    },
  }
};
</script>

<style type="text/css">
.outer{
    display: flex;
    height: 100%;
    width: 100%;
}
.right{
  width: 350px;
}
.center{
flex: 1;
height:91vh;
}
.left{
    width: 350px;
    height: 91vh;
}
.sql-box{
  width:98%;
  border: 2px solid #ddd;
}


html,
body {
  margin: 0;
  padding: 0;
  font-size: 16px;
  font-family: Menlo, Monaco, Consolas, "Andale Mono", "lucida console",
    "Courier New", monospace;
}
header {
  width: 100%;
  height: 30px;
  background-color: #1d364a;
  line-height: 30px;
  color: #fff;
}
header > span {
  font-weight: 800;
  margin-left: 1.5rem;
}
.btn {
  display: inline-block;
  text-align: center;
  background-color: #23bede;
  padding: 10px 0;
  width: 110px;
  color: #fff;
  font-size: 16px;
  border-radius: 3px;
  border: none 0;
  cursor: pointer;
  line-height: normal;
  outline: 0;
  white-space: nowrap;
  margin: 0.2rem;
}
.editor__drag {
  background: #3d444c;
  user-select: none;
  cursor: row-resize;
  text-align: center;
  line-height: 20px;
}
.arrow-v {
  background-color: #161b1e;
  display: inline-block;
  width: 2rem;
  line-height: inherit;
}
.btn_anniu{
    width: 50%;
    padding: 10px;
    font-size: 15px;
    font-weight: bold;
    border: 0 solid #fff;
    color: #000;
    outline: none;
    background: #fff;
  }
</style>
