import * as PIXI from 'pixi.js'
import * as TWEEN from '@tweenjs/tween.js'
import type { Point } from './types'
import type { Map } from './map'
import { Cashpoint } from './cashpoint'
import { CELL_SIZE } from './constants'
import { cs } from 'vuetify/locale'
export class Sprite {
  private sprite!: PIXI.Sprite
  private static spritesContainer: PIXI.Container<PIXI.DisplayObject> = new PIXI.Container()
  constructor(
    private id: number,
    private point: Point,
    private width: number,
    private height: number,
    private texturePath: string,
    private map: Map
  ) {
    this.renderSprite(this.point)
    this.map.addSpritesContainer(Sprite.spritesContainer)
  }

  private renderSprite(point: Point): void {
    this.sprite = new PIXI.Sprite(PIXI.Texture.from(this.texturePath))
    this.sprite.x = point.x
    this.sprite.y = point.y
    this.sprite.width = this.width
    this.sprite.height = this.height
    Sprite.spritesContainer.addChild(this.sprite)
  }

  private checkCollision(targetPosition: Point): boolean {
    const targetBounds = new PIXI.Rectangle(
      targetPosition.x,
      targetPosition.y,
      this.width,
      this.height
    )

    for (const sprite of Sprite.spritesContainer.children) {
      if (sprite !== this.sprite) {
        const spriteBounds = sprite.getBounds()
        if (targetBounds.intersects(spriteBounds)) {
          return true
        }
      }
    }

    return false
  }

  private checkCollisionForCashpoints(targetPosition: Point): boolean {
    const targetBounds = new PIXI.Rectangle(
      targetPosition.x,
      targetPosition.y,
      this.width,
      this.height
    )

    if (this.map.cashpointsContainer) {
      for (const cashpoint of this.map.cashpointsContainer.children) {
        const cashpointBounds = cashpoint.getBounds()
        if (targetBounds.intersects(cashpointBounds)) {
          return true
        }
      }
    }

    return false
  }

  private moveWithCollisionHandling(start: Point, end: Point, duration: number): void {
    const tween = new TWEEN.Tween(start)
      .to(end, duration)
      .easing(TWEEN.Easing.Linear.None)
      .onUpdate(({ x, y }) => {
        const nextPoint = { x: Math.round(x), y: Math.round(y) }

        if (!this.checkCollision(nextPoint)) {
          this.sprite.alpha = 1
          this.sprite.x = x
          this.sprite.y = y
          start.x = x
          start.y = y
        } else {
          console.log('collision')
          this.sprite.alpha = 0.5
          // tween.stop()
          this.sprite.x = x
          this.sprite.y = y
          start.x = x
          start.y = y
        }
      })
      // .onComplete(() => {
      //   Sprite.spritesContainer.removeChild(this.sprite)
      // })
      .start()
  }

  move(point: Point): void {
    const start = { x: this.point.x, y: this.point.y }
    const end = { x: point.x, y: point.y }

    const duration = 500 // milliseconds

    const path: Point[] = this.findPathDijkstra(this.map.getCell(start), this.map.getCell(end))

    console.log('collision', this.checkCollisionForCashpoints({ x: 0, y: 120 }))
    console.log('path', path)

    // Індекс, що вказує на поточну позицію у масиві маршруту
    let currentIndex = 0

    // Запускаємо інтервал, щоб кожні duration мілісекунд рухати персонажа по маршруту
    const interval = setInterval(() => {
      if (currentIndex < path.length) {
        const nextCell = path[currentIndex]

        // Рухаємо персонажа до наступної комірки у маршруті
        this.moveWithCollisionHandling(start, this.map.getCoordinates(nextCell), duration)

        currentIndex++
      } else {
        clearInterval(interval) // Зупиняємо інтервал, коли дійшли до кінця маршруту
      }
    }, duration) // Кожні duration мілісекунд
  }

  getSprite(): PIXI.Sprite {
    return this.sprite
  }

  getId(): number {
    return this.id
  }

  findPathDijkstra(startCell: Point, endCell: Point): Point[] {
    const visited: Record<string, boolean> = {} // змінна для зберігання відвіданих клітинок
    const distances: Record<string, number> = {} // змінна для зберігання відстаней
    const previous: Record<string, Point | null> = {} // змінна для зберігання попередніх клітинок
    const queue: Point[] = [] // черга для обходу

    for (const cell of this.map.getAllCells()) {
      // ініціалізуємо змінні
      distances[`${cell.x / CELL_SIZE},${cell.y / CELL_SIZE}`] = Infinity // відстань від стартової клітинки до будь-якої іншої - нескінченність
      previous[`${cell.x / CELL_SIZE},${cell.y / CELL_SIZE}`] = null // попередня клітинка - null
    }

    //console.log('distances', distances)
    //console.log('previous', previous)

    distances[`${startCell.x},${startCell.y}`] = 0 // відстань від стартової клітинки до неї самої - 0

    queue.push(startCell) // додаємо стартову клітинку до черги

    while (queue.length > 0) {
      // поки черга не пуста
      queue.sort((a, b) => distances[`${a.x},${a.y}`] - distances[`${b.x},${b.y}`]) // сортуємо чергу за відстанями
      const currentCell = queue.shift() // беремо першу клітинку з черги

      if (!currentCell) break // якщо клітинок немає - виходимо з циклу

      visited[`${currentCell.x},${currentCell.y}`] = true // відмічаємо клітинку як відвідану

      if (currentCell.x === endCell.x && currentCell.y === endCell.y) {
        // якщо поточна клітинка - цільова
        const path: Point[] = [] // масив для зберігання шляху
        let tempCell: Point | null = endCell // тимчасова змінна для зберігання поточної клітинки

        while (tempCell) {
          // поки тимчасова змінна не null
          path.unshift(tempCell) // додаємо поточну клітинку в початок масиву
          tempCell = previous[`${tempCell.x},${tempCell.y}`] // змінюємо поточну клітинку на попередню
        }

        return path
      }

      const neighbors = this.map.getAdjacentCells(currentCell) // отримуємо сусідні клітинки

      for (const neighbor of neighbors) {
        // для кожної сусідньої клітинки
        if (!visited[`${neighbor.x},${neighbor.y}`]) {
          // якщо сусідня клітинка не відвідана
          const distance = distances[`${currentCell.x},${currentCell.y}`] + 1 // відстань від сусідньої клітинки до стартової - відстань від поточної клітинки до стартової + 1

          if (
            distance < distances[`${neighbor.x},${neighbor.y}`] && // якщо відстань від сусідньої клітинки до стартової менша за відстань від сусідньої клітинки до стартової
            !this.checkCollision(this.map.getCoordinates(neighbor)) &&
            !this.checkCollisionForCashpoints(this.map.getCoordinates(neighbor)) // якщо сусідня клітинка не зайнята
          ) {
            distances[`${neighbor.x},${neighbor.y}`] = distance // оновлюємо відстань
            previous[`${neighbor.x},${neighbor.y}`] = currentCell // оновлюємо попередню клітинку
            queue.push(neighbor) // додаємо сусідню клітинку до черги
          }
        }
      }
    }

    return []
  }
}
