<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="20" :xs="24">
        <el-form :inline="true"  label-width="68px">
          <el-form-item>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">血缘关系更新按钮</el-button>
          </el-form-item>
        </el-form>
       </el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  name: "Buttons",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 是否显示弹出层
      open: false
    };
  },
  methods: {
    /** 血缘关系更新按钮 */
    resetQuery() {

    }
  }
};
</script>
