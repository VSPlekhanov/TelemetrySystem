<script setup lang="ts">
import { RouterView } from "vue-router";
import AuthenticatedLayout from "./layout/AuthenticatedLayout.vue";
import NotAuthenticatedLayout from "./layout/NotAuthenticatedLayout.vue";
import { store } from "./state";
</script>

<template>
  <AuthenticatedLayout v-if="store.isAuthenticated">
    <RouterView />
  </AuthenticatedLayout>
  <NotAuthenticatedLayout v-else>
    <RouterView />
  </NotAuthenticatedLayout>
</template>
