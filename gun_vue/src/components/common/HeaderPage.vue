<template>
  <div>
    <v-app-bar app color="white" dark>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>
        <img src="@/assets/logo.png" alt="Logo" height="40">
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on">
            <v-icon>mdi-account-circle</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item @click="goToMyPage">
            <v-list-item-title>마이페이지</v-list-item-title>
          </v-list-item>
          <v-list-item @click="logout">
            <v-list-item-title>로그아웃</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>

    <v-navigation-drawer v-model="drawer" app>
      <v-list-item-group>
        <v-list-item v-for="item in items" :key="item.title" @click="handleClick">
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list-item-group>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  data: () => ({
    drawer: false,
    items: [
      { title: 'Menu 1' },
      { title: 'Menu 2' },
      { title: 'Menu 3' },
      { title: 'Menu 4' },
    ],
  }),
  methods: {
  handleClick(item) {
    console.log(item.title);
    this.drawer = false;
  },
}
};
</script>

<style scoped>
.header {
  background-color: #fff;
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 20px;
  color: #333;
  align-content: center;
}
</style>