import * as PIXI from 'pixi.js'
import { getCoordinates } from './map'
import type { Point } from './types'

type Orientation = 'vertical' | 'horizontal'

export class Cashpoint {
  private graphics: PIXI.Graphics
  private static radius: number = 5
  constructor(
    private start: Point,
    private end: Point,
    private orientation: Orientation
    // #TODO: options
  ) {
    this.graphics = new PIXI.Graphics()
  }

  mount(container: PIXI.Container<PIXI.DisplayObject>) {
    this.render()
    container.addChild(this.graphics)
  }

  private render() {
    if (this.orientation === 'horizontal') {
      return this.renderHorizontal()
    } else {
      return this.renderVertical()
    }
  }

  private renderHorizontal() {
    let coords
    if (this.end.x < this.start.x) {
      coords = getCoordinates({ cellX: this.end.x, cellY: this.end.y })
    } else {
      coords = getCoordinates({ cellX: this.start.x, cellY: this.start.y })
    }
    this.paint(coords, this.width, this.height)
  }
  private renderVertical() {
    let coords
    if (this.end.y > this.start.y) {
      coords = getCoordinates({ cellX: this.start.x, cellY: this.start.y })
    } else {
      coords = getCoordinates({ cellX: this.end.x, cellY: this.end.y })
    }
    this.paint(coords, this.height, this.width)
  }

  private paint(coords: Point, width: number, height: number) {
    console.log(coords, width, height)

    this.graphics.beginFill('#FAFAFA')
    this.graphics.lineStyle({ color: '#D0D0D0', width: 1 })
    this.graphics.drawRoundedRect(coords.x, coords.y, width, height, Cashpoint.radius)
    this.graphics.endFill()
  }

  private get width(): number {
    const coordsStart = getCoordinates({ cellX: this.start.x, cellY: this.start.y })
    const coordsEnd = getCoordinates({ cellX: this.end.x, cellY: this.end.y })
    return Math.abs(coordsStart.x - coordsEnd.x)
  }

  private get height(): number {
    const coordsStart = getCoordinates({ cellX: this.start.x, cellY: this.start.y })
    const coordsEnd = getCoordinates({ cellX: this.end.x, cellY: this.end.y })
    return Math.abs(coordsStart.y - coordsEnd.y)
  }
}
