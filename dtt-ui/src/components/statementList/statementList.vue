<template>
  <div class="statement-list-model">
    <div class="select-listpp"
         v-for="(item,$index) in localStatementList"
         :key="item.id"
         @click="_getIndex($index)">
      <el-input
        :disabled="isDetails"
        type="textarea"
        size="small"
        resize="none"
        :autosize="{minRows:1}"
        :placeholder="请输入非查询SQL语句"
        v-model="localStatementList[$index]"
        @blur="_verifProp()"
        style="width: 525px;">
      </el-input>
      <span class="lt-add">
        <a href="javascript:" style="color:red;" @click="!isDetails && _removeStatement($index)" >
          <em class="el-icon-delete" :class="_isDetails" data-toggle="tooltip" :title="删除" ></em>
        </a>
      </span>
      <span class="add" v-if="$index === (localStatementList.length - 1)">
        <a href="javascript:" @click="!isDetails && _addStatement()" >
          <em class="el-icon-circle-plus-outline" :class="_isDetails" data-toggle="tooltip" :title="新增"></em>
        </a>
      </span>
    </div>
    <span class="add" v-if="!localStatementList.length">
      <a href="javascript:" @click="!isDetails && _addStatement()" >
        <em class="el-icon-circle-plus-outline" :class="_isDetails" data-toggle="tooltip" :title="新增"></em>
      </a>
    </span>
  </div>
</template>
<script>
  import _ from 'lodash'

  export default {
    name: 'user-def-statements',
    data () {
      return {
        // Increased data
        localStatementList: [],
        // Current execution index
        localStatementIndex: null
      }
    },
    props: {
      statementList: Array
    },
    methods: {
      /**
       * Current index
       */
      _getIndex (index) {
        this.localStatementIndex = index
      },
      /**
       * delete item
       */
      _removeStatement (index) {
        this.localStatementList.splice(index, 1)
        this._verifProp('value')
      },
      /**
       * add
       */
      _addStatement () {
        this.localStatementList.push('')
      },
      /**
       * blur verification
       */
      _handleValue () {
        this._verifProp('value')
      },
      /**
       * Verify that the value exists or is empty
       */
      _verifProp (type) {
        let arr = []
        let flag = true
        _.map(this.localStatementList, v => {
          arr.push(v)
          if (!v) {
            flag = false
          }
        })
        if (!flag) {
          if (!type) {
            this.$message.warning("语句不能为空！")
          }
          return false
        }
        this.$emit('on-statement-list', _.cloneDeep(this.localStatementList))
        return true
      }
    },
    watch: {
      // Monitor data changes
      statementList () {
        this.localStatementList = this.statementList
      }
    },
    created () {
      this.localStatementList = this.statementList
    },
    mounted () {
    },
    components: { }
  }
</script>

<style lang="scss" rel="stylesheet/scss">
  .statement-list-model {
    .select-listpp {
      margin-bottom: 6px;
      .lt-add {
        padding-left: 4px;
        a {
          .iconfont {
            font-size: 18px;
            vertical-align: middle;
            margin-bottom: -2px;
            display: inline-block;
          }
        }
      }
    }
    .add {
      a {
        .iconfont {
          font-size: 16px;
          vertical-align: middle;
          display: inline-block;
          margin-top: -5px;
        }
      }
    }
  }
</style>
