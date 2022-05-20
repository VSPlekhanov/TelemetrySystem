import { reactive } from "vue";
import { isAuthenticated } from "./main";

export const store = reactive({
  isAuthenticated: isAuthenticated(),
});
