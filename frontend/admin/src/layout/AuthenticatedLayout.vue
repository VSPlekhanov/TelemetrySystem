<script setup lang="ts">
import { TopMenu } from "@/components/TopMenu";
</script>
<template>
  <a-layout>
    <a-layout-header><TopMenu /></a-layout-header>
    <a-layout-content><slot></slot></a-layout-content>
  </a-layout>
</template>
