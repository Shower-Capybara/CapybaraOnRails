import * as PIXI from 'pixi.js'
import { getCoordinates } from './map'
import { getNormalizedCoordinates } from './utils'
import type { Point } from './types'
export class Sprite {
  private sprite: PIXI.Sprite

  constructor(
    private x: number,
    private y: number,
    private width: number,
    private height: number
  ) {}

  addTexture(path: string, app: PIXI.Application<PIXI.ICanvas>) {
    this.sprite = new PIXI.Sprite(PIXI.Texture.from(path))
    const coords: Point = getCoordinates({ cellX: this.x, cellY: this.y })
    const { x, y } = getNormalizedCoordinates(coords, this.width, this.height)
    this.sprite.x = x
    this.sprite.y = y
    this.sprite.width = this.width
    this.sprite.height = this.height
    app.stage.addChild(this.sprite)
  }

  move(point: Point): void {
    console.log('move called!')
    const moveToCoords: Point = getCoordinates({ cellX: point.x, cellY: point.y })

    const currentCoords: Point = getCoordinates({ cellX: this.x, cellY: this.y })
    const deltaX: number = moveToCoords.x - currentCoords.x
    const deltaY: number = moveToCoords.y - currentCoords.y

    // Логіка руху з урахуванням комбінацій координат
    if (deltaX < 0 && deltaY < 0) {
      // (-;-)
      this.sprite.x -= Math.abs(deltaX)
      this.sprite.y -= Math.abs(deltaY)
    } else if (deltaX < 0 && deltaY >= 0) {
      // (-;+)
      this.sprite.x -= Math.abs(deltaX)
      this.sprite.y += deltaY
    } else if (deltaX >= 0 && deltaY < 0) {
      // (+;-)
      this.sprite.x += deltaX
      this.sprite.y -= Math.abs(deltaY)
    } else {
      // (+;+)
      this.sprite.x += deltaX
      this.sprite.y += deltaY
    }

    this.x = point.x
    this.y = point.y
  }

  getSprite(): PIXI.Sprite | undefined {
    return this.sprite
  }
}

// human.anchor.set(0.5)
// human.x = 20
// human.y = 20
// human.width = 40
// human.height = 40
// app.stage.addChild(human)

// export const moveHumanToGreenSquare = (app) => {
//   console.log('moveHumanToGreenSquare called!')
//   const greenSquareX = 10 // координата X зеленого квадрата
//   const greenSquareY = app.screen.height - 50 // координата Y зеленого квадрата

//   const startX = human.x // початкова позиція X людини
//   const startY = human.y // початкова позиція Y людини

//   const distanceX = greenSquareX - startX // відстань по X до зеленого квадрата
//   const distanceY = greenSquareY - startY // відстань по Y до зеленого квадрата

//   // Рухати людину до зеленого квадрата тільки якщо вона не на червоній лінії
//   if (!isOnRedLine(human.x, human.y)) {
//     // Якщо поточна позиція людини не збігається з позицією зеленого квадрата
//     if (distanceX !== 0 || distanceY !== 0) {
//       // Визначення кроків для переміщення
//       const stepX = distanceX > 0 ? 1 : -1 // напрямок руху по X
//       const stepY = distanceY > 0 ? 1 : -1 // напрямок руху по Y

//       // Переміщення людини в напрямку зеленого квадрата
//       human.x += stepX
//       human.y += stepY
//     }
//   }
// }
