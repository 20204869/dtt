<!--one:
<template>
  <div class="nav-wrapper">
    <div class="logo-wrapper">
      <div class="img-wrapper">
        <img src="http://tpl-c913457.pic-tpl.websiteonline.cn/upload/f01_fxby.png" width="70px"
        height="45px">
      </div>
      <span>梦幻马卡龙</span>
    </div>
    <div class="nav-header">
      <ul>
        <li @click="switchTo('/')">
          <span :class="{on: '/' === $route.path}">首页</span>
        </li>
        <li @click="switchTo('/company')">
          <span :class="{on: '/company' === $route.path}">公司简介</span>
        </li>
        <li @click="switchTo('/news')">
          <span :class="{on: '/news' === $route.path}">新闻动态</span>
        </li>
        <li @click="switchTo('/sweets')">
          <span :class="{on: '/sweets' === $route.path}">美食甜品</span>
        </li>
        <li @click="switchTo('/users')">
          <span :class="{on: '/users' === $route.path}">用户留言</span>
        </li>
        <li @click="switchTo('/contact')">
          <span :class="{on: '/contact' === $route.path}">联系我们</span>
        </li>
      </ul>
    </div>
    <div class="nav-bottom">
      <span>流通事业部09年度营销会胜利召开</span>
      <span>总经理**在2020新品订货会上致欢迎词</span>
      <span>小食事业部正式转为流通事业部</span>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    switchTo (path) {
      this.$router.replace(path)
    }
  }
}
</script>

<style lang="scss" scoped>
.nav-wrapper {
  width: 80%;
  height: 165px;
  margin: 10px auto;
  .logo-wrapper {
    width: 17%;
    height: 125px;
    float: left;
    .img-wrapper {
      padding-top: 40px;
      padding-left: 80px;
    }
    span {
      font-size: 18px;
      color: rgb(246, 165, 159);
      padding-left: 70px;
    }
  }
  .nav-header {
    height: 125px;
    ul {
      height: 40px;
      display: flex;
      padding-top: 50px;
      li {
        width: 150px;
        display: inline-block;
        justify-content: space-around;
        text-align: center;
        line-height: 40px;
        .on{
          color:rgb(246, 165, 159)
        }
      }
      li:hover {
        color:rgb(246, 165, 159);
      }
    }
  }
  .nav-bottom {
    padding-left: 80px;
    line-height: 40px;
    color: aliceblue;
    font-size: 12px;
    background-color: #f6a59f;
  }
}
</style>
two:
<template>
  <div class="tool-bar">
    <div
      class="tool-bar-item"
      v-for="(item, index) in toolBarData"
      :key="index"
      @click="onChangeFragement(item, index)"
    >
      <img
        class="tool-bar-item-img"
        :src="[index === selectItemIndex ? item.hIcon : item.nIcon]"
      />
      <div
        class="tool-bar-item-name"
        :class="{'tool-bar-item-name-h' : index === selectItemIndex}"
      >
        {{item.name}}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      toolBarData: [
        {
          // 这个是 vue 里面配置的别名，只要路径正确即可
          nIcon: require("@img/home-n.svg"),
          hIcon: require("@img/home-h.svg"),
          name: "首页",
          componentName: "home",
        },
        {
          nIcon: require("@img/shopping-n.svg"),
          hIcon: require("@img/shopping-h.svg"),
          name: "购物车",
          componentName: "shopping",
        },
        {
          nIcon: require("@img/my-n.svg"),
          hIcon: require("@img/my-h.svg"),
          name: "我的",
          componentName: "my",
        },
      ],
      selectItemIndex: 0,
    }
  },
  methods: {
    onChangeFragement(item, index) {
      this.selectItemIndex = index
      this.$emit('onChangeFragement', item.componentName)
    }
  },
}
</script>

<style lang="scss" scoped>
.tool-bar {
  width: 100%;
  height: px2rem(46);
  display: flex;
  justify-content: space-around;
  background-color: #fff;
  box-shadow: 0 0 16px 0 rgba(0, 0, 0, 0.2);
  border-top: 1px solid #e5e5e5;

  &-item {
    text-align: center;
    padding: px2rem(4) px2rem(12);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    &-img {
      width: px2rem(22);
      height: px2rem(22);
    }

    &-name {
      font-size: 14px;
      margin-top: px2rem(4);

      &-h {
        color: #d81e06;
      }
    }
  }
}
</style>
three：
<template>
  <div class="main">
    <component :is="curentComponent"></component>
    <toolBar @onChangeFragement="onChangeFragement"></toolBar>
  </div>
</template>

<script>
import toolBar from '@c/currency/ToolBar.vue'
export default {
  components: {
    toolBar,
    'home': () => import('@c/Home'),
    'shopping': () => import('@c/Shopping'),
    'my': () => import('@c/My')
  },
  data() {
    return {
      curentComponent: 'home'
    }
  },
  methods: {
    onChangeFragement(componentName) {
      this.curentComponent =componentName
    }
  },
}
</script>

<style lang="scss" scoped>
.main {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>
-->
