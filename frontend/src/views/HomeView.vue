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
import * as PIXI from 'pixi.js'
import { onMounted, ref } from 'vue'
import Overworld from '@/js/Overworld' // Adjust the path accordingly

const pixiCanvasContainer = ref<HTMLDivElement | null>(null)

const app = new PIXI.Application({ background: '#ffffff', width: 800, height: 600 })

onMounted(() => {
  const container = pixiCanvasContainer.value

  if (container) {
    const canvasElement = app.view as HTMLCanvasElement
    container.appendChild(canvasElement)
  }
})

const overworld = new Overworld({
  element: pixiCanvasContainer
})
overworld.init()

// Your Websocket code can be placed here
</script>

<style>
canvas {
  display: block;
  width: 100%;
  height: 100%;
}
</style>
