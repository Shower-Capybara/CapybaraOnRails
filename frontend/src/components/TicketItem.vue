<!-- TicketItem.vue -->

<template>
  <div class="ticket-item">
    <!-- Info about ticket on the cashier side -->
    <div class="cashier-info">
      <div>Каса #{{ props.cashierNumber }}</div>
      <div class="time-info">
        <img src="../assets/clock.svg" alt="Clock Icon" />
        <span>{{ formattedCashierTimeBegin }} - {{ formattedCashierTimeEnd }}</span>
      </div>
    </div>

    <!-- Info about ticket amount -->
    <div class="amount-info">
      <div class="amount-text">
        {{ props.ticketAmount }} квитки
        <img src="../assets/ticket.svg" alt="Ticket Icon" />
      </div>
      <img src="../assets/arrow.svg" alt="Arrow" />
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
const formattedCashierTimeBegin = new Date(props.cashierTimeBegin).toLocaleTimeString([], {
  hour: '2-digit',
  minute: '2-digit'
})
const formattedCashierTimeEnd = new Date(props.cashierTimeEnd).toLocaleTimeString([], {
  hour: '2-digit',
  minute: '2-digit'
})
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
  background-color: #e7e6e6;
  width: 200px;
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
  display: inline;
}
/* Add your styles for TicketItem */
.passenger-info {
  background-color: #efefef;
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

.amount-info {
  flex-direction: column;
  align-items: center;
  text-align: center;
}
.amount-text {
  display: inline-flex;
}
</style>
