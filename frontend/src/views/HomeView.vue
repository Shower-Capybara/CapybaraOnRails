<script setup lang="ts">
import * as PIXI from 'pixi.js'
import { onMounted, ref } from 'vue'
import * as canvasUtils from '@/js/canvasUtils.js'
import EventList from '@/components/EventList/EventList.vue'

const pixiCanvasContainer = ref<HTMLDivElement | null>(null)

const app = new PIXI.Application({ background: '#ffffff', width: 800, height: 600 })

canvasUtils.resizeCanvas(app)
canvasUtils.addImageToCanvas(app)
canvasUtils.createLineWithHole(app)
canvasUtils.addGreenSquare(app)
canvasUtils.moveHumanToGreenSquare(app)

onMounted(() => {
  const container = pixiCanvasContainer.value

  if (container) {
    const canvasElement = app.view as HTMLCanvasElement
    container.appendChild(canvasElement)
  }
})
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
