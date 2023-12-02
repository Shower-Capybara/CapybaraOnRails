<script lang="ts" setup>
import DataCard from './DataCard.vue'
import ClockIcon from '@/components/icons/ClockIcon.vue'
import UserIcon from '@/components/icons/UserIcon.vue'
import TicketArrowCount from './TicketCountArrow.vue'
</script>
<template>
  <div
    class="flex flex-row justify-between items-center w-full gap-2 border-b pb-4 pt-4 border-b-stroke_grey-50"
  >
    <DataCard>
      <div class="flex flex-col justify-center items-center">
        <span>Каса #31</span>
        <div class="flex flex-row justify-center items-center gap-1">
          <ClockIcon />
          <span class="whitespace-nowrap">12:10 - 12:15</span>
        </div>
      </div>
    </DataCard>
    <TicketArrowCount />
    <DataCard>
      <div class="flex justify-center items-center gap-2">
        <UserIcon />
        <div class="flex flex-col justify-center items-center">
          <span class="text-xs">#BK1234567</span>
          <span>User Name</span>
        </div>
      </div>
    </DataCard>
  </div>
</template>
