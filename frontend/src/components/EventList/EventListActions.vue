<script lang="ts" setup>
import AppButton from '@/components/atoms/AppButton.vue'
import SettingsIcons from '@/components/icons/SettingsIcon.vue'

import { ref } from 'vue'
import Settings from '@/components/Settings/AppSettings.vue'

const showSettings = ref(false)

const openSettings = () => {
  showSettings.value = true
}

const closeSettings = () => {
  showSettings.value = false
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
  </div>
  <Teleport to="#app">
    <Settings v-if="showSettings" @close="closeSettings" />
  </Teleport>
</template>
