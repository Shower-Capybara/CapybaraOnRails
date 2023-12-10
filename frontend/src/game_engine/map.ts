import * as PIXI from 'pixi.js'
import { CELL_SIZE, MAP_SIZE } from './contants'
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
  const cashpoint = new Cashpoint({ x: 0, y: 6 }, { x: 2, y: 7 }, 'vertical')
  cashpoint.mount(gridContainer)
  // const graphics = new PIXI.Graphics()
  // graphics.beginFill(0xff0000)
  // graphics.lineStyle({ color: '#D0D0D0', width: 1 })
  // graphics.drawRoundedRect(160, 200, 40, 80, 5)
  // graphics.endFill()
  // gridContainer.addChild(graphics)
}
