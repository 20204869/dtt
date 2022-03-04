<template>
  <div>
    <svg-icon icon-class="question" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'RuoYiDoc',
  data() {
    return {
      url: 'https://confluence.cangoonline.net/pages/viewpage.action?pageId=24709532'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
