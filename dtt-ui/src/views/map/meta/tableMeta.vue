<template>
<div>
      <div class = "mytest">
      <el-button type="primary" @click="close()" >返回</el-button>
      <p><h1>{{form.tableName}}</h1></p>
      </div>
      <div class="btn_switch">
               <button class="btn_anniu" @click="change(0)" :class="{ newStyle:0===number}">字段详解</button>
               <button class="btn_anniu" @click="change(1)" :class="{ newStyle:1===number}">业务逻辑</button>
               <button class="btn_anniu" @click="change(2)" :class="{ newStyle:2===number}">血缘关系</button>
      </div>
      <div style="width:100%;height:5px;background:#ededee;"></div>
      <template v-if="0===number">
           <div>
              <el-table :data="cols" height="700" border :row-style="tableRowStyle" :header-cell-style="tableHeaderColor" style="width: 100%">
                <el-table-column label="字段" align="center" key="colName" prop="colName" v-if="columns[0].visible" />
                <el-table-column label="类型" align="center" key="colType" prop="colType" v-if="columns[1].visible" :show-overflow-tooltip="true" />
                <el-table-column label="注释" align="center" key="comment" prop="comment" v-if="columns[2].visible" :show-overflow-tooltip="true" />
              </el-table>
           </div>
      </template>
      <template v-else-if="1===number">
          <div>
            <textarea style="width:100% " rows="50 " disabled="disabled">{{datas[0].tableSql}}</textarea>
          </div>
      </template>
    <template v-else-if="2===number">
      <div class="dashboard-container" >
        <div id="main" ref="main" style="width:80%;height:800px;padding:30px" />
      </div>
    </template>
</div>
</template>

<script>
import { getTableDetail ,getTableDetails, getTableRelation } from "@/api/map/meta";
const echarts = require('echarts')

export default {
name: "Cols",
  data() {
    return {
      loading: true,
       number:0,
       form: {},
       cols: [],
       tableDetail:{},
       columns: [
           { key: 0, label: `字段`, visible: true },
           { key: 1, label: `类型`, visible: true },
           { key: 2, label: `注释`, visible: true }
       ],
      treeChart: '',
      arr: [],
      oneIndex: '',
      oneId: '',
      twoList: [],
      datas: []
    };
  },
  methods: {
      /** 关闭按钮 */
      close() {
        this.$store.dispatch("tagsView/delView", this.$route);
        this.$router.push({ path: "/map/meta" });
      },
    //设置表格行的样式
    tableRowStyle({row,rowIndex}){
        return 'background-color:pink;font-size:15px;'
      },
    //设置表头行的样式
    tableHeaderColor({row,column,rowIndex,columnIndex}){
    return 'background-color:#f0892e;color:#fff;font-wight:400;font-size:15px;text-align:center'
    },
    //切换按钮
    change:function(index){
       this.number=index;
    },
    clickFun(param) {
      const treeChart = echarts.init(document.getElementById('main'))
      if (param.type === 'click') {
        // 如果当前节点是叶子结点 return
        if (param.data.children === []) {
          return
        }
        // 如果是展开状态，点击关闭，清空当前节点的children
        if (param.data.collapsed === false) {
          param.data.children = []
          param.data.collapsed = true
          return
        }
          const arr = this.datas[0].children
          this.oneId = param.data.id
          const id = param.data.id
          this.oneIndex = arr.findIndex(item => item.id === param.data.id)
          for (var ix in arr) {
            // 点击当前点击的节点展开，其余节点收缩，清空其余节点的children
            if (arr[ix].id === id) {
              arr[ix].children = this.data[ix].children
              this.twoList = arr[ix].children
              arr[ix].collapsed = false
            } else {
              arr[ix].children = []
              arr[ix].collapsed = true
            }
          }
        treeChart.clear()
        this.drewTree()
      }
    },
    drewTree() {
      const treeChart = echarts.init(document.getElementById('main'));
      treeChart.setOption({
        tooltip: {
          trigger: 'item',
          triggerOn: 'mousemove'
        },
        series: [
          {
            type: 'tree',
            data: this.datas,
            initialTreeDepth: 1,
            top: '1%',
            left: '20%',
            bottom: '1%',
            right: '20%',
            symbolSize: 7,
            label: {
              normal: {
                position: 'left',
                verticalAlign: 'middle',
                align: 'right',
                fontSize: 15
              }
            },
            leaves: {
              label: {
                normal: {
                  position: 'right',
                  verticalAlign: 'middle',
                  align: 'left'
                }
              }
            },
            expandAndCollapse: true,
            animationDuration: 0,
            animationDurationUpdate: 0
          }
        ]
      })
     // treeChart.on('click', this.clickFun)
    }
  },
  mounted() {
    },
  updated(){
     this.drewTree();
   },
  created() {
      const tableId = this.$route.params && this.$route.params.tableId;
      const tableName = this.$route.params && this.$route.params.tableName;
      if (tableName) {
        this.loading = true;
        getTableDetails(tableName).then((response) => {
          this.form = response.table;
          this.cols = response.cols;
          this.$nextTick(() => {
               this.cols.forEach((row) => {
               if (row.flag) {
                 this.$refs.table.toggleRowSelection(row);
                 }
               });
            });
        getTableRelation(this.form.tableName).then((response) =>{
              this.datas = response.tableDetail;
          });
          this.loading = false;
        });
      };
  }
}
</script>
<style lang='scss' scoped>
.mytest{
  display:flex;
  flex-direction:row;
  justify-content:flex-start;
}
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
.text{
    width: 80%;
    border: 0px;
    font-size: 20px;
    border-color: white;
    background-color: bule;
}
.el-button--primary{
    color: #fff;
    background-color: bule;
    border-color: bule;
  }
.btn_anniu{
    width: 33%;
    padding: 20px;
    font-size: 18px;
    font-weight: bold;
    border: 0 solid #fff;
    color: #000;
    outline: none;
    background: #fff;
  }
 .newStyle{
    border-bottom: 5px solid #f0892e;
    color: #f0892e;
    font-size: 18px;
    font-weight: bold;
  }
</style>
