<script lang="ts" setup>
import { defineEmits, withDefaults } from 'vue'

const emit = defineEmits(['input'])
const props = withDefaults(
  defineProps<{
    id: string
    value: string
    options: string[] // Масив опцій для вибору
    required?: boolean
    title?: string
  }>(),
  { required: false, title: '' }
)

function updateValue(event: Event): void {
  emit('input', (event.target as HTMLSelectElement).value)
}
</script>

<template>
  <div class="flex flex-col gap-3">
    <select
      :id="props.id"
      :value="props.value"
      :required="props.required"
      :title="props.title"
      @change="updateValue"
      class="mb-3 px-1 py-2 border border-solid border-stroke_grey-25 rounded bg-background_grey text-sm"
    >
      <option v-for="(option, index) in props.options" :key="index" :value="option">
        {{ option }}
      </option>
    </select>
  </div>
</template>

<style scoped>
/* стилі */
</style>
