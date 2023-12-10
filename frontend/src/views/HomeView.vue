<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { app } from '@/game_engine/app'
import { generateMap } from '@/game_engine/map'
import EventList from '@/components/EventList/EventList.vue'
import * as PIXI from 'pixi.js'
import { Cashpoint } from '@/game_engine/cashpoint'

const pixiCanvasContainer = ref<HTMLDivElement | null>(null)

const connection: WebSocket = new WebSocket('wss://ws.bitmex.com/realtime') // replace with your WebSocket URL

const sendMessage = (message: string) => {
  console.log(connection)
  connection.send(message)
}

console.log('Starting connection to websocket server')

connection.onopen = (event: Event) => {
  console.log(event)
  console.log('Successfully connected')
}

connection.onmessage = (event: MessageEvent) => {
  console.log(event)
}

onMounted(() => {
  const container = pixiCanvasContainer.value

  if (container) {
    generateMap(app)
    const canvasElement = app.view as HTMLCanvasElement
    container.appendChild(canvasElement)
  }
})
</script>

<template>
  <main class="min-h-screen max-h-screen flex flex-row justify-center w-full">
    <div
      class="w-8/12 h-screen border-4 border-primary pixi-container"
      ref="pixiCanvasContainer"
    ></div>
    <div
      class="w-4/12 h-screen border-4 border-l-yellow_design border-t-stroke_grey border-r-stroke_grey border-b-stroke_grey"
    >
      <EventList />
    </div>
  </main>
</template>

<style>
canvas {
  display: block;
  width: 100%;
  height: 100%;
}
</style>
