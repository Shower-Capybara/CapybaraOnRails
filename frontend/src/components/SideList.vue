<template>
  <div>
    <div v-for="(item, index) in itemList" :key="index">
      <TicketItem :itemText="item.text" :removeItem="() => removeItem(index)" />
    </div>
    <div class="add-button" @click="addItem">+</div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import TicketItem from './TicketItem.vue'

const itemList = ref([])

const addItem = () => {
  itemList.value.push({ text: `Item ${itemList.value.length + 1}` })
}

const removeItem = (index) => {
  itemList.value.splice(index, 1)
}
</script>

<style scoped>
/* Add your styles for TicketList */
.add-button {
  cursor: pointer;
  margin-top: 10px;
  padding: 5px;
  border: 1px solid #ccc;
  display: inline-block;
}
</style>
