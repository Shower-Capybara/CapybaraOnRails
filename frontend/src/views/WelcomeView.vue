<script lang="ts" setup>
import AppButton from '@/components/atoms/AppButton.vue'
import SelectInput from '@/components/atoms/AppSelectInput.vue'

// refopen: false,
// dropdownItems: [
// { id: 1, label: 'Option 1' },
// { id: 2, label: 'Option 2' },
// { id: 3, label: 'Option 3' }
// ]
</script>

<template>
  <div class="parent">
    <div class="relative text-left inline-flex">
      <!-- Shower Image -->
      <img src="../assets/icons/shower.svg" alt="Shower Image" />

      <!-- Capybara Image (Overlayed) -->
      <img
        class="absolute top-40 left-60"
        src="../assets/icons/capybara.svg"
        alt="Capybara Image"
      />
    </div>

    <div class="p-4 w-64 top-20 relative text-left inline-flex flex-col">
      <SelectInput
        class="h-14"
        :id="'1'"
        :value="'none'"
        :title="'Оберіть станцію'"
        :required="true"
        :options="['1', '2']"
      />
      <AppButton class="mt-10 h-14" text="До праці" />
    </div>
  </div>
</template>

<style scoped>
.parent {
  display: grid;
  place-items: center;
}
</style>
