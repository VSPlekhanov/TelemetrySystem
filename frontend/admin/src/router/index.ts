import { IndexPage } from "@/components/IndexPage";
import { LoginPage } from "@/components/LoginPage";
import { isAuthenticated } from "@/main";
import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "Index",
      component: IndexPage,
    },
    {
      path: "/login",
      name: "Login",
      component: LoginPage,
    },
  ],
});

router.beforeEach(async (to) => {
  if (!isAuthenticated() && to.name !== "Login") {
    return { name: "Login" };
  }

  if (isAuthenticated() && to.name === "Login") {
    return { name: "Index" };
  }
});

export default router;
