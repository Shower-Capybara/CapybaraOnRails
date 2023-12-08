import * as PIXI from 'pixi.js'
import humanImagePath from '@/PNG_images/human.png'

var human = null
var greenSquare = null
var holeStartX = null
var holeEndX = null

var centerX = 0
var centerY = 0

var lineStartX = 0
var lineEndX = 0
var lineY = 0

var myApp = null

export const resizeCanvas = (app) => {
  const resize = () => {
    const div = document.querySelector('.pixi-container')
    if (div) {
      const { width, height } = div.getBoundingClientRect()
      app.renderer.resize(width, height)
    }
  }
  resize()
  window.addEventListener('resize', resize)
}

export const addImageToCanvas = (app) => {
  myApp = app
  const humanTexture = PIXI.Texture.from(humanImagePath)
  human = new PIXI.Sprite(humanTexture)
  human.anchor.set(0.5)
  human.x = 20
  human.y = 20
  human.width = 40
  human.height = 40
  app.stage.addChild(human)
}

export const createLineWithHole = (app) => {
  const line = new PIXI.Graphics()
  line.lineStyle(2, 0xff0000) // Червоний колір, товщина лінії 2 пікселя

  centerX = app.screen.width / 2
  centerY = app.screen.height / 2

  lineStartX = 0
  lineEndX = app.screen.width - 50
  lineY = centerY

  // Перша частина лінії (500 пікселів)
  line.moveTo(lineStartX, lineY)
  line.lineTo(lineStartX + 500, lineY)

  // Отвір (100 пікселів без лінії)
  holeStartX = lineStartX + 500
  holeEndX = holeStartX + 100
  const holeY = lineY

  line.lineStyle(0) // Знищуємо товщину лінії для малювання отвору
  line.beginHole()
  line.endHole()

  // Друга частина лінії (200 пікселів)
  const secondLineStartX = holeEndX
  line.lineStyle(2, 0xff0000) // Відновлюємо червоний колір
  line.moveTo(secondLineStartX, lineY)
  line.lineTo(secondLineStartX + 200, lineY)

  app.stage.addChild(line)
}

export const addGreenSquare = (app) => {
  greenSquare = new PIXI.Graphics()
  greenSquare.beginFill(0x00ff00) // Зелений колір заливки
  greenSquare.drawRect(10, app.screen.height - 50, 40, 40) // Розміри та позиція квадрата
  greenSquare.endFill()

  app.stage.addChild(greenSquare)
}

// Функція для перевірки чи точка (x, y) знаходиться в межах отвору
function isInsideHole(x, y) {
  return x >= holeStartX && x <= holeEndX
}

// Функція для перевірки, чи точка (x, y) перебуває на червоній лінії
function isOnRedLine(x, y) {
  // Перевірка для першої частини лінії (500 пікселів)
  if (x >= lineStartX && x <= lineStartX + 500 && y === lineY) {
    return true
  }

  // Перевірка для другої частини лінії (200 пікселів)
  if (x >= holeEndX && x <= holeEndX + 200 && y === lineY) {
    return true
  }

  return false
}

// Функція для руху людини до зеленого квадрата
export const moveHumanToGreenSquare = (app) => {
  console.log('moveHumanToGreenSquare called!')
  const greenSquareX = 10 // координата X зеленого квадрата
  const greenSquareY = app.screen.height - 50 // координата Y зеленого квадрата

  const startX = human.x // початкова позиція X людини
  const startY = human.y // початкова позиція Y людини

  const distanceX = greenSquareX - startX // відстань по X до зеленого квадрата
  const distanceY = greenSquareY - startY // відстань по Y до зеленого квадрата

  // Рухати людину до зеленого квадрата тільки якщо вона не на червоній лінії
  if (!isOnRedLine(human.x, human.y)) {
    // Якщо поточна позиція людини не збігається з позицією зеленого квадрата
    if (distanceX !== 0 || distanceY !== 0) {
      // Визначення кроків для переміщення
      const stepX = distanceX > 0 ? 1 : -1 // напрямок руху по X
      const stepY = distanceY > 0 ? 1 : -1 // напрямок руху по Y

      // Переміщення людини в напрямку зеленого квадрата
      human.x += stepX
      human.y += stepY
    }
  }
}
