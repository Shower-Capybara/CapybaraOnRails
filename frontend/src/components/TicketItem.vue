<!-- TicketItem.vue -->

<template>
    <div class="ticket-item">
      <div class="icon">
        <img src="../assets/ticket.svg" alt="Ticket Icon" />
      </div>
      <div class="text">{{ itemText }}</div>
      <div class="action" @click="removeItem">-</div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { defineProps } from 'vue';
  
  interface TicketItemProps {
    itemText: string;
    removeItem: () => void;
  }
  
  const props = defineProps<TicketItemProps>();
  </script>
  