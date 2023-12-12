import type { Privilegy } from '@/game_engine/types'
import type { Point } from '@/game_engine/types'

export interface ClientMovedEvent {
  type: string
  client: {
    id: number
    firstName: string
    lastName: string
    privilegy: Privilegy
    position: Point
  }
  previousPoint: Point
  timestamp: [number, number, number, number, number, number, number]
  newPoint: Point
}
