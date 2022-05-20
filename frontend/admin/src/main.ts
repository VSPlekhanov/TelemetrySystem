import "ant-design-vue/dist/antd.css";
import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import antd from "ant-design-vue";

export function isAuthenticated() {
  return localStorage.getItem("accessToken") !== null;
}

const app = createApp(App);

app.use(router);
app.use(antd);

app.mount("#app");
