<template>
    <div>
      <div class ="mytest">
          <el-button type="primary" @click="close()" >返回</el-button>
          <p><h1>
          {{Cols[0].tableName}}
          </h1></p>
      </div>
           <div>
              <el-table :data="Cols" height="800" border :row-style="tableRowStyle" :header-cell-style="tableHeaderColor" style="width: 100%">
                <el-table-column label="表字段" align="center" key="colName" prop="colName" v-if="columns[0].visible" />
                <el-table-column label="注释" align="center" key="colComment" prop="colComment" v-if="columns[1].visible" :show-overflow-tooltip="true" />
              </el-table>
           </div>
    </div>
</template>

<script>
import { getCols} from "@/api/map/business";

export default {
name: "Cols",
  data() {
    return {
      loading: true,
       number:0,
       Cols:[],
       columns: [
           { key: 0, label: `表字段`, visible: true },
           { key: 1, label: `注释`, visible: true }
       ],
    };
  },
  methods: {
      /** 关闭按钮 */
      close() {
        this.$store.dispatch("tagsView/delView", this.$route);
        this.$router.push({ path: "/map/business" });
      },
    //设置表格行的样式
    tableRowStyle({row,rowIndex}){
        return 'background-color:pink;font-size:15px;'
      },
    //设置表头行的样式
    tableHeaderColor({row,column,rowIndex,columnIndex}){
    return 'background-color:#f0892e;color:#fff;font-wight:400;font-size:15px;text-align:center'
    },
  },
  mounted() {
    },
  updated(){
   },
  created() {
      const tableName = this.$route.params && this.$route.params.tableName;
      console.log(tableName)
      if (tableName) {
        this.loading = true;
        getCols(tableName).then((response) => {
          this.Cols = response.business;
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
