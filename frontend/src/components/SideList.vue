<template>
  <div>
    <div v-for="(item, index) in itemList" :key="index">
      <TicketItem
        :itemText="item.itemText"
        :removeItem="() => removeItem(index)"
        :passengerName="Tim"
        :ticketId="10"
        :cashierNumber="10"
        :cashierTimeBegin="new Date('2023-07-01T12:30:00.000Z')"
        :cashierTimeEnd="new Date('2023-07-01T12:40:00.000Z')"
        :ticketAmount="2"
      />
    </div>
    <div class="add-button" @click="addItem">+</div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import TicketItem from './TicketItem.vue'

const itemList = ref([])

const addItem = () => {
  itemList.value.push({ itemText: `Item ${itemList.value.length + 1}` })
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
