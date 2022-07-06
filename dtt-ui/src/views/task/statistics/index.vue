<template>
<div style="display:flex">
  <div id="main" style="float:left;width:480px ;height:450px;"></div>
  <div ref="echarts" class="simpleDemo" style="float:left;width:600px ;height:500px;"></div>
  <div>
  <el-table :data="tableData" stripe style="width: 100%">
    <el-table-column prop="date"  label="业务系统"  width="120"> </el-table-column>
    <el-table-column prop="name" label="表总数" width="120"> </el-table-column>
    <el-table-column prop="address" label="已同步表总数" width="120"> </el-table-column>
    <el-table-column prop="test" label="占比" width="120"> </el-table-column>
  </el-table>
  </div>
</div>
</template>
<script>
  import echarts from 'echarts'
  export default {
    name: 'index',
    data() {
      return {
        chart: null,
        option:  {
            title: {
                text: '业务需求量对比'
            },
            tooltip: {},
            legend: {
                data:[]
            },
            xAxis: {
                data: ["保险","销售","财务","经销商","好车","审批"]
            },
            yAxis: {},
            series: {
                name: '销量',
                type: 'bar',
                data: [5, 20, 36, 10, 10, 20],
                itemStyle: {
                  color: function(params) {
                    let colorList = ['#00FFFF','#C0C0C0','#FFFF00','#66FF99','#000000','#FF0000']
                    return colorList[params.dataIndex]
                  }
                }
             },

        },
      tableData: [{
                date: '保险系统',
                name: '281',
                address: '45',
                test:"16%"
              },
              {
                date: '经销商调查',
                name: '34',
                address: '10',
                test:"30%"
              },
              {
                date: '销售运营',
                name: '296',
                address: '45',
                test:"15%"
              },
              {
                date: '审批系统',
                name: '1243',
                address: '145',
                test:"12%"
              },
              {
                date: '经纪人',
                name: '190',
                address: '0',
                test:"0%"
              },
              {
                date: '贷后催收',
                name: '445',
                address: '10',
                test:"0.02%"
              },
              {
                date: '抵押备案',
                name: '93',
                address: '36',
                test:"40%"
              },
              {
                date: '呼叫中心',
                name: '112',
                address: '0',
                test:"0%"
              }]
      }
    },
    mounted: function() {
      this.$nextTick(function() {
        this.getPie()
      });
      this.getPage();
    },
    methods: {
    getPage() {
            this.chart = echarts.init(this.$refs.echarts);
            // 使用刚指定的配置项和数据显示图表。
            this.chart.setOption(this.option);
          },
      getPie() {
        // 绘制图表
        var myChart = echarts.init(document.getElementById('main'))
        // 指定图表的配置项和数据
        var option = {
          //标题
          title: {
            text: '数据分布',
            x: 'center' ,              //标题位置
            // textStyle: { //标题内容的样式
            //   color: '#000',
            //   fontStyle: 'normal',
            //   fontWeight: 100,
            //   fontSize: 16 //主题文字字体大小，默认为18px
            // },
          },
          // stillShowZeroSum: true,
          //鼠标划过时饼状图上显示的数据
          tooltip: {
            trigger: 'item',
            formatter: '{a}<br/>{b}:{c} ({d}%)'
          },
          //图例
          legend: {//图例  标注各种颜色代表的模块
            // orient: 'vertical',//图例的显示方式  默认横向显示
            bottom: 10,//控制图例出现的距离  默认左上角
            left: 'center',//控制图例的位置
            // itemWidth: 16,//图例颜色块的宽度和高度
            // itemHeight: 12,
            textStyle: {//图例中文字的样式
              color: '#000',
              fontSize: 16
            },
            data: ['未使用', '已同步表', '未同步表']//图例上显示的饼图各模块上的名字
          },
          //饼图中各模块的颜色
          color: ['#32dadd', '#b6a2de', '#5ab1ef'],
          // 饼图数据
          series: {
            // name: 'bug分布',
            type: 'pie',             //echarts图的类型   pie代表饼图
            radius: '70%',           //饼图中饼状部分的大小所占整个父元素的百分比
            center: ['50%', '50%'],  //整个饼图在整个父元素中的位置
            // data:''               //饼图数据
            data: [                  //每个模块的名字和值
              { name: '未使用', value: 10 },
              { name: '已同步表', value: 20},
              { name: '未同步表', value: 70 }
            ],
            itemStyle: {
              normal: {
                label: {
                  show: true,//饼图上是否出现标注文字 标注各模块代表什么  默认是true
                  // position: 'inner',//控制饼图上标注文字相对于饼图的位置  默认位置在饼图外
                },
                labelLine: {
                  show: true//官网demo里外部标注上的小细线的显示隐藏    默认显示
                }
              }
            },
          }

        }
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option)
      }
    }

  }
</script>

<style scoped>
.simpleDemo {
  width: 600px;
  height:400px;
  margin: 0 auto;
}
a {
  color: #00CC66	;
}
.router-link-active {
  text-decoration: none;
}
</style>
