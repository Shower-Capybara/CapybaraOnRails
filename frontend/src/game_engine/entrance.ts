import * as PIXI from 'pixi.js'
import type { Map } from './map'
import type { Point } from './types'

type Alignment = 'left' | 'right' | 'top' | 'bottom'

export class Entrance {
  private graphics: PIXI.Graphics
  private static width: number = 4
  constructor(
    private id: number,
    private point: Point,
    private map: Map,
    private alignment: Alignment
  ) {
    this.graphics = new PIXI.Graphics()
  }

  mount(container: PIXI.Container<PIXI.DisplayObject>) {
    this.render()
    container.addChild(this.graphics)
  }

  private render() {
    const coords = this.map.getCoordinates(this.point)
    this.graphics.beginFill('#F41919')
    switch (this.alignment) {
      case 'left':
        this.graphics.drawRect(coords.x, coords.y, Entrance.width, this.map.getCellSize())
        break
      case 'right':
        this.graphics.drawRect(
          coords.x + this.map.getCellSize(),
          coords.y,
          Entrance.width,
          this.map.getCellSize()
        )
        break
      case 'top':
        this.graphics.drawRect(coords.x, coords.y, this.map.getCellSize(), Entrance.width)
        break
      case 'bottom':
        this.graphics.drawRect(
          coords.x,
          coords.y + this.map.getCellSize(),
          this.map.getCellSize(),
          Entrance.width
        )
        break
    }
    this.graphics.endFill()
  }

  getId(): number {
    return this.id
  }
}
