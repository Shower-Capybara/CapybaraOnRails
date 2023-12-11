import * as PIXI from 'pixi.js'
import type { Point } from './types'
import type { Map } from './map'

type Status = 'working' | 'stopped' | 'reserved'
interface Options {
  status: Status
}

export class Cashpoint {
  private graphics: PIXI.Graphics
  private text: PIXI.Text
  private static radius: number = 5
  private static padding: number = 5
  private static colors: Record<Status, string> = {
    working: '#15B42E',
    stopped: '#F41919',
    reserved: '#F9BE26'
  }
  constructor(
    private id: number,
    private start: Point,
    private end: Point,
    private map: Map,
    private options: Options
  ) {
    this.graphics = new PIXI.Graphics()
    this.text = new PIXI.Text(`#${this.id}`, {
      fontSize: 12,
      fill: 0x000000
    })
  }

  mount(container: PIXI.Container<PIXI.DisplayObject>): void {
    this.render()
    container.addChild(this.graphics)
    container.addChild(this.text)
  }

  private render(): void {
    if (this.width > this.height) {
      return this.renderHorizontal()
    } else {
      return this.renderVertical()
    }
  }

  private renderHorizontal(): void {
    let coords
    if (this.end.x < this.start.x) {
      coords = this.map.getCoordinates({ x: this.end.x, y: this.end.y })
    } else {
      coords = this.map.getCoordinates({ x: this.start.x, y: this.start.y })
    }
    this.paint(coords, this.width, this.height)
  }
  private renderVertical(): void {
    let coords
    if (this.end.y > this.start.y) {
      coords = this.map.getCoordinates({ x: this.start.x, y: this.start.y })
    } else {
      coords = this.map.getCoordinates({ x: this.end.x, y: this.end.y })
    }
    this.paint(coords, this.width, this.height)
  }

  private paint(coords: Point, width: number, height: number): void {
    this.graphics.beginFill('#FAFAFA')
    this.graphics.lineStyle({ color: '#122083', width: 1 })
    this.graphics.drawRoundedRect(coords.x, coords.y, width, height, Cashpoint.radius)
    this.graphics.endFill()
    this.graphics.beginFill(Cashpoint.colors[this.options.status])
    this.graphics.lineStyle({ alpha: 0 })
    this.graphics.drawCircle(
      coords.x + Cashpoint.padding * 2,
      coords.y + Cashpoint.padding * 2,
      Cashpoint.radius
    )

    this.graphics.endFill()
    this.text.position.set(coords.x + Cashpoint.padding, coords.y + Cashpoint.padding * 4)
  }

  private get width(): number {
    const coordsStart = this.map.getCoordinates({ x: this.start.x, y: this.start.y })
    const coordsEnd = this.map.getCoordinates({ x: this.end.x, y: this.end.y })
    return Math.abs(coordsStart.x - coordsEnd.x)
  }

  private get height(): number {
    const coordsStart = this.map.getCoordinates({ x: this.start.x, y: this.start.y })
    const coordsEnd = this.map.getCoordinates({ x: this.end.x, y: this.end.y })
    return Math.abs(coordsStart.y - coordsEnd.y)
  }
}
