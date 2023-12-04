<script lang="ts" setup>
import AppButton from '@/components/atoms/AppButton.vue'
import TextInput from '@/components/atoms/AppInput.vue'
// #TODO: Fix layout(see Figma desing) +++
// #TODO: Use custom component for buttons(@/components/atoms/AppButton.vue) +++
// #TODO: Create and use custom component for input (text input and select input should be 2 different components, +++
//        also keep in mind that select component is used on welcome screen for station selection) +++
// #TODO: Add close button and handle close event(see Figma desing) +++

import { defineEmits } from 'vue'

const emit = defineEmits(['input'])

const closeSettings = () => {
  emit('close')
}
</script>
<template>
  <div
    class="fixed top-0 left-0 bottom-0 right-0 flex justify-center items-center"
    :class="$style.backdrop"
  >
    <div class="flex flex-col p-4 bg-white rounded-xl border border-primary w-2/5 h-7/10">
      <div class="flex justify-end">
        <button @click="closeSettings" class="text-3xl">&#215;</button>
      </div>
      <div class="flex justify-center mb-3">
        <h1 class="text-xl font-semibold">Налаштування</h1>
      </div>
      <div class="flex flex-row justify-center gap-3 mb-8 mt-4">
        <div class="flex flex-col gap-3">
          <label class="text-sm font-medium">Кількість кас</label>
          <label class="text-sm font-medium">Кількість входів</label>
          <label class="text-sm font-medium">Час обслуговування (с.)</label>
        </div>
        <div class="flex flex-col gap-3">
          <TextInput id="cashbox" value="6" />
          <TextInput id="entrance" value="2" />
          <TextInput id="time" value="30" />
        </div>
      </div>
      <label class="text-sm font-medium flex mb-4 justify-center">Стратегія появи</label>

      <v-radio-group class="m-6">
        <v-radio label="Стратегія 1" value="one"></v-radio>
        <v-radio label="Стратегія 2" value="two"></v-radio>
      </v-radio-group>

      <div class="flex flex-row justify-center items-center w-full gap-8">
        <div class="h-10 w-36">
          <AppButton text="Скасувати зміни" variant="outline" />
        </div>
        <div class="h-10 w-52">
          <AppButton text="Зберегти і перезапустити" />
        </div>
      </div>
    </div>
  </div>
</template>
<style lang="scss" module>
.backdrop {
  backdrop-filter: blur(2px);
}
</style>
