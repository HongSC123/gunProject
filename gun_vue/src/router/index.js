import { createRouter, createWebHistory } from "vue-router";
import MainPage from "@/components/MainPage.vue";

const baseRoutes = [
  {
    path: "/",
    name: "MainPage",
    component: MainPage,
  },
];

const routes = [...baseRoutes];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;