import type { Privilegy } from '@/game_engine/types'
import type { Point } from '@/game_engine/types'

export interface ClientAddedEvent {
  type: string
  hall: {
    id: number
    entrances: { start: Point; end: Point }[]
    ticketOffices: {
      id: number
      segment: {
        start: Point
        end: Point
      }
      queue: {
        id: number
        firstName: string
        lastName: string
        privilegy: Privilegy
        position: Point
      }[]
      timeToServeTicket: number
      isClosed: boolean
      isReserved: boolean
      direction: string
      transactions: unknown[]
      reserved: boolean
    }[]
    segment: {
      start: Point
      end: Point
    }
  }
  client: {
    id: number
    firstName: string
    lastName: string
    privilegy: Privilegy
    position: Point
  }
  timestamp: [number, number, number, number, number, number, number]
}
