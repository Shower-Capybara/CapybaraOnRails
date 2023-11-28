<!-- TicketItem.vue -->

<template>
  <div class="ticket-item">
    <!-- Info about ticket on the cashier side -->
    <div class="cashier-info">
      <div class="time-info">
        <img src="../assets/clock.svg" alt="Clock Icon" />
        <span>{{ formattedCashierTimeBegin }} - {{ formattedCashierTimeEnd }}</span>
      </div>
      <div>Каса #{{ props.cashierNumber }}</div>
    </div>

    <!-- Info about ticket amount -->
    <div class="amount-info">
      <div>
        {{ props.ticketAmount }} квитки
        <img src="../assets/ticket.svg" alt="Ticket Icon" />
        <img src="../assets/arrow.svg" alt="Arrow" />
      </div>
    </div>

    <!-- Info about passenger -->
    <div class="passenger-info">
      <img src="../assets/user.svg" alt="User Icon" />
      <div>#{{ props.ticketId }}</div>
      <div>{{ props.passengerName }}</div>
    </div>

    <div class="action" @click="props.removeItem">Remove</div>
  </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'

interface TicketItemProps {
  ticketId: string
  passengerName: string
  cashierNumber: string
  cashierTimeBegin: Date
  cashierTimeEnd: Date
  ticketAmount: number
  removeItem: () => void
}

const props = defineProps<TicketItemProps>()
const formattedCashierTimeBegin = new Date(props.cashierTimeBegin).toLocaleTimeString()
const formattedCashierTimeEnd = new Date(props.cashierTimeEnd).toLocaleTimeString()
</script>

<style scoped>
/* Add your styles for TicketItem */
.ticket-item {
  display: flex;
  justify-content: space-between;
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
}

.cashier-info {
  background-color: #b3b3b3;
  border-radius: 10px;
  padding: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.time-info {
  margin-bottom: 10px;
}

.time-info img {
  margin-bottom: 5px;
}
/* Add your styles for TicketItem */
.passenger-info {
  background-color: #b3b3b3;
  border-radius: 10px;
  padding: 10px;
  display: flex;
  align-items: center;
  text-align: center;
}

.passenger-info img {
  margin-right: 10px;
}

.passenger-info div {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
