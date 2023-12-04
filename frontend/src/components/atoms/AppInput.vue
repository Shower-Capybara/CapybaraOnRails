<script lang="ts" setup>
import { defineEmits, withDefaults } from 'vue'

const emit = defineEmits(['input'])
const props = withDefaults(
  defineProps<{
    id: string
    value: string
    required?: boolean
    pattern?: string
    title?: string
  }>(),
  { required: false, pattern: '', title: '' }
)

function updateValue(event: Event): void {
  emit('input', (event.target as HTMLInputElement).value)
}
</script>

<template>
  <div class="flex flex-col gap-3">
    <input
      :id="props.id"
      type="text"
      :value="props.value"
      :required="props.required"
      :pattern="props.pattern"
      :title="props.title"
      @input="updateValue"
      class="mb-3 px-1 py-2 border border-solid border-stroke_grey-25 rounded bg-background_grey text-sm"
    />
  </div>
</template>

<style scoped>
input {
  margin: 0%;
  width: 60px;
  height: 20px;
}
</style>
