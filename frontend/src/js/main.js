// main.js
import * as PIXI from 'pixi.js'
import Overworld from './Overworld'

// Assuming you are using a modern front-end framework that supports refs
// For example, in Vue.js or React, you can access the ref like this:
const pixiCanvasContainer = document.querySelector('.pixi-container')
const canvasWidth = pixiCanvasContainer.clientWidth // Use clientWidth to get the inner width
const canvasHeight = pixiCanvasContainer.clientHeight // Use clientHeight to get the inner height

const app = new PIXI.Application({ width: canvasWidth, height: canvasHeight })
pixiCanvasContainer.appendChild(app.view)

const overworld = new Overworld({
  element: pixiCanvasContainer
})
overworld.init()
