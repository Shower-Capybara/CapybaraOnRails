import type { Privilegy } from '@/game_engine/types'
import type { Point } from '@/game_engine/types'

export interface ClientServedEvent {
  type: string
  ticketOfficeId: number
  client: {
    id: number
    firstName: string
    lastName: string
    privilegy: Privilegy
    position: Point
  }
  timestamp: [number, number, number, number, number, number, number]
}
