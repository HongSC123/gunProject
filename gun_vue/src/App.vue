<template>
  <v-app>
    <HeaderPage />
    <v-main>
      <router-view />
    </v-main>
    <FooterPage />
  </v-app>
</template>

<script>
import { RouterView } from 'vue-router';
import FooterPage from '@/components/common/FooterPage.vue';
import HeaderPage from '@/components/common/HeaderPage.vue';

export default {
  name: 'App',
  components: {
    HeaderPage,
    FooterPage,
    RouterView
},
}
</script>

<style>

</style>
