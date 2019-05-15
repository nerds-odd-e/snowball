<template>
  <div id="app">
    <UICommon></UICommon>
  </div>
</template>

<script>
import UICommon from './components/ui_common.vue'

export default {
  name: 'app',
  components: {
    UICommon
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
