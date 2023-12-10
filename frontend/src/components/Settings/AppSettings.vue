<script lang="ts" setup>
import AppButton from '@/components/atoms/AppButton.vue'
import TextInput from '@/components/atoms/AppInput.vue'
import { defineEmits } from 'vue'
import { ref } from 'vue'
const selected = ref('option1')
const selectStrategy = (option) => {
  selected.value = option
  console.log(selected)
}

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
      <label class="text-sm font-medium flex mb-2 justify-center">Стратегія появи</label>

      <div class="flex flex-col items-center mb-4">
        <div>
          <input
            type="radio"
            id="option1"
            name="option"
            value="option1"
            v-model="selected"
            @click="selectStrategy('option1')"
          />
          <label class="text-sm font-medium p-4" for="option1">Стратегія 1</label>
        </div>
        <div>
          <input
            type="radio"
            id="option2"
            name="option"
            value="option2"
            v-model="selected"
            @click="selectStrategy('option2')"
          />
          <label class="text-sm font-medium p-4" for="option2">Стратегія 2</label>
        </div>
      </div>

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
input[type='radio'] {
  -webkit-appearance: radio; /* WebKit/Blink browsers */
  -moz-appearance: radio; /* Firefox */
  appearance: radio; /* Standard */
  display: inline-block; /* Для вирівнювання відносно тексту */
  width: 20px; /* Розмір кола (можна змінити) */
  height: 16px; /* Розмір кола (можна змінити) */
  border-radius: 50%; /* Заокруглені кути, щоб отримати коло */
  border: 1px solid #ccc; /* Обведення кола (можна змінити) */
  margin: 0; /* Зовнішній відступ */
  padding: 0; /* Внутрішній відступ */
  vertical-align: middle; /* Вирівнювання по вертикалі */
}
</style>
