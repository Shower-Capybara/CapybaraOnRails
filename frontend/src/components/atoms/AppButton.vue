<script lang="ts" setup>
type Variant = 'primary' | 'outline'

const emit = defineEmits(['onClick'])
const props = withDefaults(
  defineProps<{
    text: string
    variant?: Variant
  }>(),
  { variant: 'primary', text: 'AppButton' }
)

function onClick(): void {
  emit('onClick')
}
</script>
<template>
  <button
    @click="onClick"
    class="w-full h-full flex justify-center items-center rounded-lg font-semibold duration-200"
    :class="{
      'bg-primary text-white hover:bg-primary_hover': props.variant === 'primary',
      'border border-solid border-stroke_grey-25 bg-background_grey text-black hover:bg-gray-200':
        props.variant === 'outline'
    }"
  >
    {{ text }}
  </button>
</template>
