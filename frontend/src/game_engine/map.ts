import * as PIXI from 'pixi.js'
import { CELL_SIZE, MAP_SIZE } from './constants'
import type { Point } from './types'
import { Cashpoint } from './cashpoint'

type Cell = PIXI.Graphics

const cells: Cell[] = []

export function generateMap(app: PIXI.Application<PIXI.ICanvas>) {
  const gridContainer = new PIXI.Container()
  app.stage.addChild(gridContainer)
  createGrid(gridContainer)
}

export function getCoordinates({ cellX, cellY }: { cellX: number; cellY: number }): Point {
  return {
    x: cells[cellX * MAP_SIZE].x,
    y: cells[cellX * MAP_SIZE + cellY].y
  }
}

export function getCells(): Cell[] {
  return [...cells]
}

function createGrid(gridContainer: PIXI.Container<PIXI.DisplayObject>) {
  for (let i = 0; i < MAP_SIZE; i++) {
    for (let j = 0; j < MAP_SIZE; j++) {
      const cell = new PIXI.Graphics()
      // cell.lineStyle(1, 0x000000, 0.5)
      cell.drawRect(0, 0, CELL_SIZE, CELL_SIZE)
      cell.position.set(i * CELL_SIZE, j * CELL_SIZE)
      gridContainer.addChild(cell)
      cells.push(cell)
    }
  }
  const cashpoint = new Cashpoint(12, { x: 0, y: 5 }, { x: 1, y: 7 }, { status: 'working' })
  cashpoint.mount(gridContainer)
}
