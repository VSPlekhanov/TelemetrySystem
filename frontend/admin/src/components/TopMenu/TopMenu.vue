<script setup lang="ts">
import { API } from "@/api/api";
import { isAuthenticated } from "@/main";
import router from "@/router";
import { store } from "@/state";

function logout() {
  API.auth.logout().then(() => {
    store.isAuthenticated = isAuthenticated();
    router.push({ name: "Login" });
  });
}
</script>

<template>
  <a-row>
    <a-col :offset="23" :span="1">
      <a-button type="danger" @click="logout">Logout</a-button>
    </a-col>
  </a-row>
</template>
