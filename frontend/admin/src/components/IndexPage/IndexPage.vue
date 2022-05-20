<script setup lang="ts">
import { onMounted, ref } from "vue";
import { API } from "@/api";
import type { Event } from "@/types";

const data = ref<Event[]>([]);

const columns = [
  {
    title: "User",
    dataIndex: ["user", "name"],
  },
  {
    title: "Date",
    dataIndex: "createdAt",
    customRender: ({ value }: { value: Date }) => value.toUTCString(),
    sorter: (d1: Event, d2: Event) =>
      d2.createdAt.getTime() - d1.createdAt.getTime(),
    defaultSortOrder: "ascend",
  },
  {
    title: "Type",
    dataIndex: "type",
  },
  {
    title: "Additional info",
    dataIndex: "additional",
  },
];

onMounted(async () => {
  data.value = await API.event.list();
});
</script>

<template>
  <a-table :dataSource="data" :columns="columns"></a-table>
</template>
