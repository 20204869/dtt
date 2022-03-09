<template>
<div style="display: flex">
  <div id="left-bar">
     <el-input
       v-model="dbName"
       placeholder="请输入库"
       clearable
       size="small"
       prefix-icon="el-icon-search"
       style="margin-bottom: 20px"
     />
    <el-tree
       :data="dbTableOptions"
       :props="defaultProps"
       :expand-on-click-node="false"
       :filter-node-method="filterNode"
       ref="tree"
       default-expand-all
    />
  </div>
  <div id="content">
    <el-row>
      <el-button
          type="primary"
          size="small"
          @click="formaterSql(basicInfoForm.sqlMain)"
          >查询</el-button
        >
      <el-button
          type="primary"
          size="small"
          @click="formaterSql(basicInfoForm.sqlMain)"
          >美化</el-button
        >
      </el-row>
    <div class="sql-box">
      <SqlEditor
        ref="sqleditor"
        :value="basicInfoForm.sqlMain"
        @changeTextarea="changeTextarea($event)"
      />
    </div>
  </div>
</div>
</template>

<script>
import sqlFormatter from "sql-formatter";
import SqlEditor from "@/components/SqlEditor";

import { listDbTable } from "@/api/map/meta";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Query",
  components: {
    SqlEditor,
    Treeselect
  },
  data() {
    return {
    dbName: undefined,
    dbTableOptions: undefined,
    defaultProps: {
       children: "children",
       label: "label"
       },
      loading: true,
      basicInfoForm: {
        sqlMain: "",
      },
    };
  },
  created() {
     // this.getTreeselect();
    },
  methods: {
      // 筛选节点
     filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
    /** 查询库表列表 */
    getTreeselect() {
        listDbTable().then(response => {
          this.dbTableOptions = response.dbTable;
          console.log(response.dbTable)
        });
      },
    changeTextarea(val) {
      this.$set(this.basicInfoForm, "sqlMain", val);
    },
    formaterSql(val) {
      let dom = this.$refs.sqleditor;
      dom.editor.setValue(sqlFormatter.format(dom.editor.getValue()));
    },
  },
};
</script>
<style>
    * {
        margin: 0;
        padding: 0;
    }
    #left-bar {
        height: 100vh;
        width: 300px;
    }
    #content {
        height: 100vh;
        flex-grow: 1;
    }
    body {
        flex-direction: row;
        flex-wrap: nowrap;
    }
.sql-box{
  width:60%;
  border: 1px solid #ddd;
}
</style>
