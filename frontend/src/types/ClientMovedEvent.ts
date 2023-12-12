import type { Privilegy } from '@/game_engine/types'

export interface ClientMovedEvent {
  type: string
  client: {
    id: number
    firstName: string
    lastName: string
    privilegy: Privilegy
    position: { x: number; y: number }
  }
  previousPoint: { x: number; y: number }
  timestamp: [number, number, number, number, number, number, number]
  newPoint: { x: number; y: number }
}
