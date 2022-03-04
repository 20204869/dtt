<template>
    <div>
        <logo-select  @getindex='getIndex'></logo-select>
        <div class="search-input">
            <input type="text" v-model="searchValue">
            <span class="search-reset" @click="clearInput()">&times;</span>
            <button class="search-btn" @click="search()">搜一下</button>
        </div>
          <div>
              <el-table :data="tables" height="600" border :row-style="tableRowStyle" :header-cell-style="tableHeaderColor" style="width: 100%">
                <el-table-column label="库*表" align="center" key="tableName" prop="tableName" v-if="columns[0].visible" />
                <el-table-column label="注释" align="center" key="tableComment" prop="tableComment" v-if="columns[1].visible" :show-overflow-tooltip="true" />
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                       <router-link :to="'/map/business-col/businessCol/' + scope.row.tableName" class="link-type">
                         <span>表字段详情</span>
                       </router-link>
                  </template>
                      </el-table-column>
              </el-table>
           </div>
    </div>
</template>

<script type="text/javascript">
import logoSelect from './logo-select.vue';
import { getBusiness} from "@/api/map/business";

export default {
    //注册组件
    components: {
        'logo-select': logoSelect
    },
    data: function() {
        return {
            loading: true,
            searchValue: '',
            tables:[],
             columns: [
                 { key: 0, label: `库*表`, visible: true },
                 { key: 1, label: `注释`, visible: true }
             ]
        }
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
        // &event是实参，表示event对象
        search: function() {
            //打开对应的搜索界面
            console.log('搜索关键字：'+this.searchValue)
           // this.$router.push("/map/business-table/businessTable/" + this.searchValue);
          if (this.searchValue) {
            this.loading = true;
            getBusiness(this.searchValue).then((response) => {
              this.tables = response.business;
              this.loading = false;
            });
          };
        },
        clearInput: function() {
            this.searchValue = '';
        },
        getIndex: function(index) {
            this.searchIndex = index;
        }
    },
    created() {}
}
</script>

<style type="text/css">
.search-input {
    height: 45px;
    width: 600px;
    margin: 0 auto;
    margin-top: 10px;
    position: relative;
}

.search-input input {
    border: 1px solid #e4e4e4;
    box-sizing: border-box;
    width: 500px;
    height: 45px;
    font-size: 18px;
    float: left;
    padding-left: 10px;
    padding-right: 10px;
    overflow: hidden;
}

.search-btn {
    height: 45px;
    width: 100px;
    border: 1px solid mediumseagreen;
    background-color: mediumseagreen;
    color: white;
    font-size: 16px;
    font-weight: bold;
    float: left;
}

.search-btn {
    cursor: pointer
}

.search-select {
    position: absolute;
    top: 45px;
    width: 500px;
    box-sizing: border-box;
    z-index: 999;
}

.search-select li {
    border: 1px solid #d4d4d4;
    ;
    border-top: none;
    border-bottom: none;
    background-color: #fff;
    width: 100%
}

.search-select-option {
    box-sizing: border-box;
    padding: 7px 10px;
}

.selectback {
    background-color: #eee !important;
    cursor: pointer
}

input::-ms-clear {
    display: none
}

.search-reset {
    width: 21px;
    height: 21px;
    position: absolute;
    display: block;
    line-height: 21px;
    text-align: center;
    cursor: pointer;
    font-size: 20px;
    right: 110px;
    top: 12px
}

.search-select-list {
    transition: all 0.5s
}

.itemfade-enter,
.itemfade-leave-active {
    opacity: 0;
}

.itemfade-leave-active {
    position: absolute;
}

.selectback {
    background-color: #eee !important;
    cursor: pointer
}
.search-select ul{margin:0;text-align: left; }
</style>
