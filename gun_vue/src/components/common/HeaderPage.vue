<template>
  <div>
    <v-app-bar app color="white" dark>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>
        <img src="@/assets/logo.png" alt="Logo" height="40" />
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <template>
        <v-dialog v-model="dialog" width="500">
          <template v-slot:activator="{ on, attrs }">
            <v-icon v-bind="attrs" v-on="on">mdi-account-circle</v-icon>
          </template>
          <v-card>
            <v-card-title class="headline">My Page</v-card-title>
              <MyPage />
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="green darken-1" text @click="dialog = false">Close</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </template>
    </v-app-bar>
    <v-navigation-drawer v-model="drawer" app>
      <v-list-item-group>
        <v-list-item v-for="menu in menus" :key="menu.title" @click="handleClick">
          <v-list-item-title>{{ menu.title }}</v-list-item-title>
        </v-list-item>
      </v-list-item-group>
    </v-navigation-drawer>
  </div>
</template>

<script>
import MyPage from "@/components/member/MyPage.vue";
export default {
  components: {
    MyPage,
  },
  data: () => ({
    dialog: false,
    drawer: false,
    menus: [
      { title: "Menu 1" },
      { title: "Menu 2" },
      { title: "Menu 3" },
      { title: "Menu 4" },
    ],
  }),
  methods: {
    openDialog() {
      this.dialog = true;
    },
    handleClick(item) {
      console.log(item.title);
      this.drawer = false;
    },
  },
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
