<script lang="ts" setup>
import TicketsIcon from '@/components/icons/TicketsIcon.vue'
import ArrowIcon from '@/components/icons/ArrowIcon.vue'
</script>
<template>
  <div class="flex flex-col justify-center items-center relative">
    <div class="flex gap-2 items-center absolute -top-1.5">
      <TicketsIcon />
      <span class="text-sm leading-none">2 квитки</span>
    </div>
    <ArrowIcon />
  </div>
</template>
