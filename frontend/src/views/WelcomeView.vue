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

    <div class="top-20 relative text-left inline-flex flex-col">
      <button
        @click="open = !open"
        class="p-3 flex items-center bg-white border rounded shadow border-blue-500"
      >
        <span class="mr-2">Оберіть станцію</span>
        <svg
          width="20"
          height="20"
          viewBox="0 0 20 20"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M6 9L10 13L14 9"
            stroke="#4A5568"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </button>

      <div v-if="open" class="absolute w-full mt-12 rounded-md p-4 shadow-lg bg-white border-t">
        <ul>
          <li
            v-for="item in dropdownItems"
            :key="item.id"
            class="block p-3 border-b border-gray-300 px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
          >
            {{ item.label }}
          </li>
        </ul>
      </div>
      <AppButton class="mt-10 w-64 p-4" text="До праці" />
    </div>
  </div>
</template>

<style scoped>
.parent {
  display: grid;
  place-items: center;
}
</style>
