<script setup lang="ts">
import * as PIXI from 'pixi.js'
import EventList from '@/components/EventList/EventList.vue'

const app = new PIXI.Application({ width: 800, height: 600 })
const resizeCanvas = () => {
  const div = document.querySelector('.pixi-container')
  if (div) {
    const { width, height } = div.getBoundingClientRect()
    app.renderer.resize(width, height)
  }
}
resizeCanvas()
window.addEventListener('resize', resizeCanvas)

// Websocket

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
</script>

<template>
  <main class="min-h-screen max-h-screen flex flex-row justify-center w-full">
    <div class="w-8/12 h-screen border-4 border-primary pixi-container">
      <canvas ref="pixiCanvas"></canvas>
    </div>
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
