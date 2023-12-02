<script lang="ts" setup>
// #TODO: Fix layout(see Figma desing)
// #TODO: Use custom component for buttons(@/components/atoms/AppButton.vue)
// #TODO: Create and use custom component for input (text input and select input should be 2 different components, also keep in mind that select component is used on welcome screen for station selection)
// #TODO: Add close button and handle close event(see Figma desing)
</script>
<template>
  <div
    class="fixed top-0 left-0 bottom-0 right-0 flex justify-center items-center"
    :class="$style.backdrop"
  >
    <div class="flex flex-col p-4 bg-background_grey rounded-xl border border-stroke_grey-50">
      <div class="flex justify-between mb-4">
        <h1 class="text-xl font-bold">Налаштування</h1>
      </div>
      <div class="flex flex-col gap-4">
        <div class="mb-4">
          <label class="text-sm font-medium">Кількість кас</label>
          <input type="number" class="w-full form-control" />
        </div>
        <div class="mb-4">
          <label class="text-sm font-medium">Кількість входів</label>
          <input type="number" class="w-full form-control" />
        </div>
        <div class="mb-4">
          <label class="text-sm font-medium">Час обслуговування</label>
          <input type="number" class="w-full form-control" />
        </div>
        <div class="mb-4">
          <label class="text-sm font-medium">Стратегія появи</label>
          <select class="w-full form-control">
            <option value="стратегія 1">Стратегія 1</option>
            <option value="стратегія 2">Стратегія 2</option>
          </select>
        </div>
      </div>
      <div class="flex justify-end">
        <button class="btn btn-primary">Зберегти і перезапустити</button>
        <button class="btn btn-secondary">Скасувати зміни</button>
      </div>
    </div>
  </div>
</template>
<style lang="scss" module>
.backdrop {
  backdrop-filter: blur(2px);
}
</style>
