import * as PIXI from 'pixi.js'
import { CELL_SIZE, MAP_SIZE } from './contants'
import type { Point } from './types'

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
      cell.lineStyle(1, 0x000000, 0.5)
      cell.drawRect(0, 0, CELL_SIZE, CELL_SIZE)
      cell.position.set(i * CELL_SIZE, j * CELL_SIZE)
      gridContainer.addChild(cell)
      cells.push(cell)
    }
  }
}
