<script setup lang="ts">
import { LoginForm } from "../LoginForm";
import router from "@/router";

function onLogin() {
  router.push({ name: "Index" });
}
</script>

<template>
  <LoginForm @login="onLogin" />
</template>
