<script setup lang="ts">
import { API } from "@/api/api";
import { isAuthenticated } from "@/main";
import { store } from "@/state";
import { reactive } from "vue";
import type { LoginFormData } from "./types";

const emit = defineEmits(["login"]);

const formData = reactive<LoginFormData>({
  username: "",
  password: "",
});

function onFinish(values: LoginFormData) {
  API.auth.login(values.username, values.password).then(() => {
    store.isAuthenticated = isAuthenticated();
    emit("login");
  });
}
</script>

<template>
  <a-form :model="formData" autocomplete="off" @finish="onFinish">
    <a-form-item
      label="Username"
      name="username"
      :rules="[{ required: true, message: 'Please input your username!' }]"
    >
      <a-input v-model:value="formData.username" />
    </a-form-item>

    <a-form-item
      label="Password"
      name="password"
      :rules="[{ required: true, message: 'Please input your password!' }]"
    >
      <a-input-password v-model:value="formData.password" />
    </a-form-item>

    <a-form-item :wrapper-col="{ offset: 12, span: 24 }">
      <a-button type="primary" html-type="submit">Submit</a-button>
    </a-form-item>
  </a-form>
</template>
