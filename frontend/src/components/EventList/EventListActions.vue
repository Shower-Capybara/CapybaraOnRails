<script lang="ts" setup>
import AppButton from '@/components/atoms/AppButton.vue'
import SettingsIcons from '@/components/icons/SettingsIcon.vue'
</script>
<template>
  <div class="flex flex-row justify-between items-center w-full">
    <div class="h-8 w-32">
      <AppButton text="Додати касу" />
    </div>
    <div class="h-8 w-32">
      <AppButton text="Додати вхід" />
    </div>
    <div class="cursor-pointer">
      <SettingsIcons />
    </div>
  </div>
</template>
