import * as PIXI from 'pixi.js'
import * as TWEEN from '@tweenjs/tween.js'
import type { Point } from './types'
import type { Map } from './map'
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

    const duration = 4000 // milliseconds

    this.moveWithCollisionHandling(start, end, duration)
  }

  getSprite(): PIXI.Sprite {
    return this.sprite
  }

  getId(): number {
    return this.id
  }
}
