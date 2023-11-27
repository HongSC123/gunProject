<template>
  <div id="app">
    <Header />
    <RouterView />
    <Footer />
  </div>
</template>

<script>
import { RouterView } from 'vue-router';
import Footer from '@/components/common/FooterPage.vue';
import Header from '@/components/common/HeaderPage.vue';

export default {
  name: 'App',
  components: {
    Footer,
    Header,
    RouterView
},
}
</script>

<style>

</style>
