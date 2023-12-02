<script lang="ts" setup>
import AppButton from '@/components/atoms/AppButton.vue'
import SettingsIcons from '@/components/icons/SettingsIcon.vue'

import { ref } from 'vue'
import Settings from '@/components/Settings/Settings.vue'

const showSettings = ref(false)

const openSettings = () => {
  showSettings.value = true
}
</script>
<template>
  <div class="flex flex-row justify-between items-center w-full">
    <div class="h-8 w-32">
      <AppButton text="Додати касу" />
    </div>
    <div class="h-8 w-32">
      <AppButton text="Додати вхід" />
    </div>
    <div class="cursor-pointer" @click="openSettings">
      <SettingsIcons />
    </div>
    <Settings v-if="showSettings" />
  </div>
</template>
