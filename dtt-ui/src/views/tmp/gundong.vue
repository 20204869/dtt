<template>
<div>
<div>
<h3 class="title" align="center">项目负责人：任鹏远 电话：13285650981</h3>
</div>
 <div id="box" ref="box">
    <div class="marquee-box" ref="marquee" @mouseover="menter" @mouseleave="mleave">
      <p ref="cmdlist" id="pWidth">
        <!---<img style="width: 200px;height: 200px" src="../assets/logo.png" alt="">欢迎咨询 --->
      </p>
    </div>
  </div>
 </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      value: 0,
      timer: '',//计时器
      pwidth:100,//公告文本的宽度
      windowWidth:document.documentElement.clientWidth,// 设备屏幕的宽度
      }
    },

  mounted() {
     let element = this.$refs.cmdlist;
     this.pwidth = document.defaultView.getComputedStyle(element,'').width.split('px');
     this.timer = setInterval(this.clickCommend, 1);
  },
  watch:{
    value(newValue, oldValue) {
      let allWidth= parseInt(this.windowWidth)+parseInt(this.pwidth[0]);
      if(newValue <= -allWidth){
        this.$refs.cmdlist.style.marginLeft = this.windowWidth+"px";
        this.value = 0;
      }
    },
  },
  methods:{
    clickCommend(e) {
      let _this = this;
      this.$nextTick(() => {
        this.value -=1;
        this.$refs.cmdlist.style.marginLeft = _this.windowWidth+this.value+"px";
      });
    },
    menter(){
      clearInterval(this.timer);
    },
    mleave(){
      this.timer = setInterval(this.clickCommend, 1);
    },
  },
  beforeDestroy() {
    clearInterval(this.timer);
  }
}
</script>

<style scoped>
box {
  width: 100%;
  height: 50px;
  margin-top: 100px;
  position: relative;
}
.marquee-box  {
  position: relative;
  width: 100%;
  height: 100%;
  overflow:auto;
  background-color: #f8f8f8;
}
pWidth{
  width: 10%;
  height: 50px;
  padding: 0;
  margin: 0;
  line-height: 50px;
  display: block;
  word-break: keep-all;
  white-space: nowrap;
  overflow:hidden;
  font-family: 微软雅黑; fontSize:100px;
  background-color: #f8f8f8
}
::-webkit-scrollbar {
  width: 0 !important;
}
::-webkit-scrollbar {
  width: 0 !important;height: 0;
}
</style>
