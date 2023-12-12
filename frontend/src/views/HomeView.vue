<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { app } from '@/game_engine/app'
// import { generateMap } from '@/game_engine/map'
import EventList from '@/components/EventList/EventList.vue'
import * as PIXI from 'pixi.js'
import { Cashpoint } from '@/game_engine/cashpoint'
import { Map } from '@/game_engine/map'
import { CELL_SIZE, MAP_SIZE } from '@/game_engine/constants'
import { Sprite } from '@/game_engine/sprite'
import { Entrance } from '@/game_engine/entrance'

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
    const map = new Map(MAP_SIZE, CELL_SIZE, app.stage)
    map.generate()

    const cashpoint1 = new Cashpoint(1, { x: 0, y: 3 }, { x: 1, y: 5 }, map, { status: 'working' })

    const cashpoint2 = new Cashpoint(2, { x: 0, y: 7 }, { x: 1, y: 9 }, map, { status: 'working' })

    const cashpoint3 = new Cashpoint(3, { x: 0, y: 11 }, { x: 1, y: 13 }, map, {
      status: 'working'
    })

    const cashpoint4 = new Cashpoint(4, { x: 3, y: MAP_SIZE - 1 }, { x: 5, y: MAP_SIZE }, map, {
      status: 'stopped'
    })

    const cashpoint5 = new Cashpoint(5, { x: 3, y: MAP_SIZE - 1 }, { x: 5, y: MAP_SIZE }, map, {
      status: 'stopped'
    })

    const cashpoints = [cashpoint1, cashpoint2, cashpoint3, cashpoint4, cashpoint5]
    for (let cashpoint of cashpoints) {
      cashpoint.mount(app.stage)
    }

    const sprite1 = new Sprite(
      1,
      map.getCoordinates({ x: 0, y: 0 }),
      CELL_SIZE,
      CELL_SIZE,
      '/images/man.svg',
      map
    )

    const sprite2 = new Sprite(
      1,
      map.getCoordinates({ x: 10, y: 5 }),
      CELL_SIZE,
      CELL_SIZE,
      '/images/man.svg',
      map
    )

    const sprite3 = new Sprite(
      1,
      map.getCoordinates({ x: 10, y: 10 }),
      CELL_SIZE,
      CELL_SIZE,
      '/images/man.svg',
      map
    )

    setTimeout(() => {
      sprite1.move(map.getCoordinates({ x: 1, y: 19 }))
    }, 10)

    setTimeout(() => {
      sprite2.move(map.getCoordinates({ x: 0, y: 5 }))
    }, 2000)

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
