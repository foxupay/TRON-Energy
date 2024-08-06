<template>
  <div id="app">
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive" />
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive" />
  </div>
</template>

<script>
	
export default {
  name: 'App',
}
</script>

<style scoped>
#app{height: 100%;}
</style>
