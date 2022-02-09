<template>

  <div class="page-box">
    <div class="item">
      <div class="left">
        <img src="../assets/images/index_datamap.png"></img>
        <div class="text">
          <span>数据地图</span>
          <span>• 全业务系统【表&字段】定义查询</span>
          <span>• 各业务【指标&口径】定义查询</span>
          <span>• 【接口&报表】定义查询</span>
        </div>
      </div>
      <div class="left">
        <img src="../assets/images/index_search.png"></img>
        <div class="text">
          <span>数据查询</span>
          <span>• 大数据HIVE数据库全表查询(HUE)</span>
          <span>• 自主制做hiveSQL数据处理</span>
        </div>
      </div>
    </div>
    <div class="item">
      <div class="left">
        <img src="../assets/images/index_task.png"></img>
        <div class="text">
          <span>调度中心</span>
          <span>• 自主制定数据处理执行任务(Dolphin)</span>
          <span>• 任务执行情况监控</span>
        </div>
      </div>
      <div class="left">
        <img src="../assets/images/index_report.png"></img>
        <div class="text">
          <span>数据报表</span>
          <span>• TableauBI报表(统计分析)</span>
          <span>• 原数据中心报表(明细列表)</span>
        </div>
      </div>
    </div>
    <div class="item">
      <div class="left">
        <img src="../assets/images/index_assets.png"></img>
        <div class="text">
          <span>数据资产</span>
          <span>• 同步数据现状盘点</span>
        </div>
      </div>
      <div class="left">
        <img src="../assets/images/index_monitor.png"></img>
        <div class="text">
          <span>监控</span>
          <span>• 定时任务执行监控</span>
          <span>• 接口使用监控</span>
          <span>• 资源使用监控</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
  .page-box {
    display: flex;
    margin-left: 200px;
    flex-direction: column;
    width: 100%;
  }
  .item {
    margin-top: 80px;
    width: 100%;
    display: flex;
  }
  .item img {
    width: 130px;
    height: 130px;
  }
  .left {
    display: flex;
    justify-content: left;
    width: 50%;
  }
  .text {
    display: flex;
    flex-direction: column;
  }
  .text span:nth-child(1) {
    font-size: 30px;
    margin-left: 10px;
  }
  .text span:nth-child(2) {
    font-size: 16px;
    margin-left: 40px;
    margin-top: 10px;
  }
  .text span:nth-child(3) {
    font-size: 16px;
    margin-left: 40px;
    margin-top: 10px;
  }
  .text span:nth-child(4) {
    font-size: 16px;
    margin-left: 40px;
    margin-top: 10px;
  }
  .text span:nth-child(5) {
    font-size: 16px;
    margin-left: 40px;
    margin-top: 10px;
  }
</style>
