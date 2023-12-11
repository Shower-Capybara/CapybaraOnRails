import * as PIXI from 'pixi.js'
import type { Point } from './types'

type Cell = PIXI.Graphics
export class Map {
  private cells: Cell[] = []
  private gridContainer: PIXI.Container<PIXI.DisplayObject>
  constructor(
    private size: number,
    private cellSize: number,
    private container: PIXI.Container<PIXI.DisplayObject>
  ) {
    this.gridContainer = new PIXI.Container()
  }
  generate(): void {
    this.container.addChild(this.gridContainer)
    this.createGrid({ isLinesShown: false, isFilled: false })
  }

  private createGrid({
    isLinesShown,
    isFilled
  }: {
    isLinesShown: boolean
    isFilled: boolean
  }): void {
    for (let i = 0; i < this.size; i++) {
      for (let j = 0; j < this.size; j++) {
        const cell = new PIXI.Graphics()
        if (isLinesShown) {
          cell.lineStyle(1, 0x000000, 0.2)
        }
        if ((j === 0 || j === this.size - 1 || i === 0 || i === this.size - 1) && isFilled) {
          cell.beginFill(0x00ff00, 0.2)
        }
        cell.drawRect(0, 0, this.cellSize, this.cellSize)
        cell.position.set(i * this.cellSize, j * this.cellSize)
        this.gridContainer.addChild(cell)
        if ((j === 0 || j === this.size - 1 || i === 0 || i === this.size - 1) && isFilled) {
          cell.endFill()
        }
        this.cells.push(cell)
      }
    }
  }

  showGrid(): void {
    this.cells = []
    this.gridContainer.removeChildren()
    this.createGrid({ isLinesShown: true, isFilled: true })
  }

  hideGrid(): void {
    this.cells = []
    this.gridContainer.removeChildren()
    this.createGrid({ isLinesShown: false, isFilled: false })
  }

  lightCells(): void {}

  addSpritesContainer(spritesContainer: PIXI.Container<PIXI.DisplayObject>): void {
    this.container.addChild(spritesContainer)
  }

  getCoordinates(point: Point): Point {
    return {
      x: this.cells[point.x * this.size].x,
      y: this.cells[point.x * this.size + point.y].y
    }
  }
  getCellSize(): number {
    return this.cellSize
  }
  getCellCoordinates(x: number, y: number) {
    const cellX = Math.round(x / this.cellSize)
    const cellY = Math.round(y / this.cellSize)

    return { x: cellX, y: cellY }
  }
}
