import * as PIXI from 'pixi.js'
import type { Point } from './types'

type Cell = PIXI.Graphics
export class Map {
  private cells: Cell[] = []
  private gridContainer: PIXI.Container<PIXI.DisplayObject>
  public cashpointsContainer: PIXI.Container<PIXI.DisplayObject> | null = null
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
    const gridContainer = new PIXI.Container()
    this.container.addChild(gridContainer)
    this.cashpointsContainer = new PIXI.Container() // створіть контейнер для кас
    this.container.addChild(this.cashpointsContainer)
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
        //cell.lineStyle(1, 0x000000, 0.5)
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
    const cellX: number = Math.floor(x / this.cellSize)
    const cellY: number = Math.floor(y / this.cellSize)

    return { x: cellX, y: cellY }
  }

  getSize(): number {
    return this.size
  }

  getCell(coord: Point): Point | undefined {
    const x = Math.floor(coord.x / this.cellSize)
    const y = Math.floor(coord.y / this.cellSize)

    // Перевірка чи координати знаходяться на мапі
    if (x >= 0 && x < this.size && y >= 0 && y < this.size) {
      return { x, y }
    }

    // Якщо координати поза межами мапи, повертаємо undefined
    return undefined
  }

  getAllCells(): Cell[] {
    return this.cells
  }

  getAllCashpoints(): PIXI.Container<PIXI.DisplayObject> | null {
    return this.cashpointsContainer
  }

  getAdjacentCells(currentCell: Point): Point[] {
    const adjacentCells: Point[] = []

    // Визначте всі можливі напрямки для сусідніх комірок (верх, низ, ліво, право)
    const directions: Point[] = [
      { x: 0, y: -1 }, // верх
      { x: 0, y: 1 }, // низ
      { x: -1, y: 0 }, // ліво
      { x: 1, y: 0 } // право
    ]

    // Перевірте кожне напрямок і додайте сусідню комірку, якщо вона належить до мапи
    for (const direction of directions) {
      const neighborCell: Point = {
        x: currentCell.x + direction.x,
        y: currentCell.y + direction.y
      }

      // Перевірка чи сусідня комірка належить до мапи
      // Тут також можна додати умову для перевірки, чи комірка не виходить за межі мапи
      if (this.isValidCell(neighborCell)) {
        adjacentCells.push(neighborCell)
      }
    }

    return adjacentCells
  }

  isValidCell(cell: Point): boolean {
    return cell.x >= 0 && cell.x < this.size && cell.y >= 0 && cell.y < this.size
  }
}
