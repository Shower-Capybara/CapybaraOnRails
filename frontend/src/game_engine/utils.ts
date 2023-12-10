import { CELL_SIZE } from './contants'
import type { Point } from './types'

export function getNormalizedCoordinates(point: Point, width: number, height: number): Point {
  const center_x: number = point.x - CELL_SIZE / 2
  const center_y: number = point.y - CELL_SIZE / 2

  const x: number = center_x - width / 2
  const y: number = center_y - height / 2
  return {
    x,
    y
  }
}
