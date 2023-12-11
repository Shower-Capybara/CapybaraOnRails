<script lang="ts" setup>
import EventListActions from './EventListActions.vue'
import SaveIcon from '@/components/icons/SaveIcon.vue'
import SaleEvent from '@/components/SaleEvent/SaleEvent.vue'
import { Map } from '@/game_engine/map'
import { ref } from 'vue'

const emit = defineEmits(['onAddCashier'])
const props = defineProps<{ isCashierAdditionDone: boolean }>()

const onAddCashier = () => {
  emit('onAddCashier')
}
</script>
<template>
  <div class="flex flex-col justify-start items-center h-full w-full px-4">
    <div
      class="flex justify-between items-center w-full pb-4 pt-2 border-b-2 border-b-stroke_grey-50"
    >
      <span class="text-2xl">Історія обслуговування!</span>
      <div class="flex justify-center items-center cursor-pointer">
        <SaveIcon />
      </div>
    </div>
    <div class="w-full overflow-y-auto" :class="$style.event_list">
      <SaleEvent />
      <SaleEvent />
      <SaleEvent />
      <SaleEvent />
      <SaleEvent />
      <SaleEvent />
      <SaleEvent />
      <SaleEvent />
      <SaleEvent />
      <SaleEvent />
      <SaleEvent />
      <SaleEvent />
      <SaleEvent />
    </div>
    <div class="w-full py-4">
      <EventListActions
        @on-add-cashier="onAddCashier"
        :is-cashier-addition-done="props.isCashierAdditionDone"
      />
    </div>
  </div>
</template>
<style lang="scss" module>
.event_list {
  height: calc(100vh - 150px);
}
</style>
