import * as PIXI from 'pixi.js'
import type { Point } from './types'

type Cell = PIXI.Graphics
export class Map {
  private cells: Cell[] = []
  constructor(
    private size: number,
    private cellSize: number,
    private container: PIXI.Container<PIXI.DisplayObject>
  ) {}
  generate(): void {
    const gridContainer = new PIXI.Container()
    this.container.addChild(gridContainer)
    this.createGrid(gridContainer)
  }

  private createGrid(gridContainer: PIXI.Container<PIXI.DisplayObject>): void {
    for (let i = 0; i < this.size; i++) {
      for (let j = 0; j < this.size; j++) {
        const cell = new PIXI.Graphics()
        // cell.lineStyle(1, 0x000000, 0.5)
        cell.drawRect(0, 0, this.cellSize, this.cellSize)
        cell.position.set(i * this.cellSize, j * this.cellSize)
        gridContainer.addChild(cell)
        this.cells.push(cell)
      }
    }
  }

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
}
