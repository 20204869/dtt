<template>
  <div>
    <div class="outer">
      <div class="right">
        <div style="display:flex;">
            <img @click="ChangeDown()" src="@/assets/images/before.png" />
             <h2>{{tableauShow}}</h2>
        </div>
        <el-row>
         <el-col>
          <el-input
            v-model="name"
            placeholder="请输入关键字"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
            />
        <div style="height:800px;overflow:scroll;">
          <el-tree
            :data="TableauOptions"
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
      <div class="center" style="height:100%;overflow:scroll;overflow-x: scroll;overflow-y: scroll;overflow: hidden;position: relative;">
          <iframe :src="viewUrl" width="100%" height="898"></iframe>
      </div>
  </div>
</div>
</template>
<script>
import { allProject ,workbookByProject ,viewByWorkbookId ,tableauViewUrl} from "@/api/tableau/tableau";

export default {
  name: "Tableau",
  data() {
      return {
        // 遮罩层
        loading: true,
        // 显示搜索条件
        showSearch: true,
        // tableau project/workbook/view查询结果
        TableauOptions: undefined,
        // 是否显示弹出层
        open: false,
        name: undefined,
        tableauShow:"projects",
        //view url
        viewUrl:undefined,
        defaultProps: {
          label: "projectName"
        },
        // 查询参数
        queryParams: {
          projectId: undefined,
          workbookId: undefined
        },
      };
    },
  watch: {
    // 根据名称筛选库
    name(val) {
      if (this.defaultProps.label === 'projectName') {
        this.TableauOptions = this.$refs.tree.data.filter((item) =>{
          return item.projectName.indexOf(val) >= 0
         })
      } else if (this.defaultProps.label === 'workbookName') {
        this.TableauOptions = this.$refs.tree.data.filter((item) =>{
          return item.workbookName.indexOf(val) >= 0
         })
      }
      //删除查询参数渲染数据
      if(val === '' || val === null || val === undefined){
        if (this.defaultProps.label === 'projectName'){
              allProject().then(response => {
                  this.TableauOptions = response.project;
                  this.tableauShow = 'projects';
              });
         } else if (this.defaultProps.label === 'workbookName'){
             workbookByProject(this.queryParams.projectId).then(response => {
                   this.TableauOptions = response.workbook;
                 });
         }
      }
    }
 },
  created() {
    this.getAllProject();
  },
  methods: {
     ChangeDown () {
        allProject().then(response => {
            this.TableauOptions = response.project;
            this.tableauShow = 'projects';
            this.defaultProps.label = 'projectName';
          });
       },
    /** 查询tableau 项目列表 */
    getAllProject() {
      allProject().then(response => {
        this.TableauOptions = response.project;
      });
    },
    // 筛选节点
        filterNode(value, data) {
          if (!value) return true;
          return data.projectName.indexOf(value) !== -1;
        },
    // 节点单击事件
    handleNodeClick(data) {
        this.queryParams.projectId = data.projectId;
        this.tableauShow = data.projectName
        if(data.workbookName){

         }else {
              this.defaultProps.label = 'workbookName';
              workbookByProject(data.projectId).then(response => {
                this.TableauOptions = response.workbook;
              });
            }
            if(this.defaultProps.label === 'workbookName'){
                if(data.workbookId === undefined || data.workbookId === null ){

                }else{
                  tableauViewUrl(data.workbookId).then(response => {
                     this.viewUrl = response.viewUrl;
                     this.tableauShow = "workbooks"
                     console.log(response.viewUrl)
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
  width: 200px;
}
.center{
flex: 1;
height:91vh;
}

</style>
