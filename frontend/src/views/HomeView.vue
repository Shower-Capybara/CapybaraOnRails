<template>
  <div class="min-h-screen max-h-screen flex flex-row justify-center w-full">
    <div
      class="w-8/12 h-screen border-4 border-primary pixi-container"
      ref="pixiCanvasContainer"
    ></div>
    <div
      class="w-4/12 h-screen border-4 border-l-yellow_design border-t-stroke_grey border-r-stroke_grey border-b-stroke_grey"
    >
      <EventList />
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { app } from '@/game_engine/app'
import { generateMap } from '@/game_engine/map'
import EventList from '@/components/EventList/EventList.vue'
import * as PIXI from 'pixi.js'
import { Sprite } from '@/game_engine/sprite'
import { Point } from '@/game_engine/types'

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

  const sprite = new Sprite(0, 0, 16, 16)
  sprite.addTexture('src/images_png/human.png', app)
  const pointToMove: Point = { x: 8, y: 12 }
  sprite.move(pointToMove)

  const pointToMove2: Point = { x: 2, y: 2 }
  sprite.move(pointToMove2)
})
</script>

<style>
canvas {
  display: block;
  width: 100%;
  height: 100%;
}
</style>
