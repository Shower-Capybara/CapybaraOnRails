// main.js
import * as PIXI from 'pixi.js'
import Overworld from './Overworld'
import './init' // Import as a script

const pixiCanvasContainer = document.querySelector('.pixi-container')
const canvasWidth = pixiCanvasContainer.clientWidth
const canvasHeight = pixiCanvasContainer.clientHeight

const app = new PIXI.Application({ width: canvasWidth, height: canvasHeight })
pixiCanvasContainer.appendChild(app.view)

const overworld = new Overworld({
  element: pixiCanvasContainer
})
overworld.init()
