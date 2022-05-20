<script setup lang="ts">
import { onMounted, ref } from "vue";
import { API } from "@/api";

const data = ref<any[]>([]);

onMounted(async () => {
  const rawData = await API.event.list();
  data.value = rawData;
});
</script>

<template>
  <a-typography-title>You are in</a-typography-title>
</template>
