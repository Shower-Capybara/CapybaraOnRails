<script setup lang="ts">
import SideList from '../components/SideList.vue'
import AddCashierButton from '../components/AddCashierButton.vue'
import AddEntranceButton from '../components/AddEntranceButton.vue'
import SaveButton from '../components/SaveButton.vue'
import SettingButton from '../components/SettingButton.vue'
</script>

<template>
  <main>
    <div class="headliner">
      <span>Історія обслуговування</span>
      <SaveButton />
    </div>
    <div class="list">
      <SideList></SideList>
    </div>
    <div class="buttons">
      <AddCashierButton />
      <AddEntranceButton />
      <SettingButton />
    </div>
  </main>
</template>

<style>
.headliner {
  font-size: 26px; /* Adjust the value to your preference */
  font-weight: bold;
  display: inline-flex;
  justify-content: space-between;
  width: 50%;
}
.save-icon {
  margin-left: auto;
}

.buttons {
  display: inline-flex;
  justify-content: space-between;
}
.add-cashier-button:not(:last-child),
.add-entrance-button:not(:last-child),
.setting-button:not(:last-child) {
  margin-right: 60px;
}
</style>
