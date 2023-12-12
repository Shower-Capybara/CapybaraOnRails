import type { Privilegy } from '@/game_engine/types'

export interface ClientAddedEvent {
  type: string
  hall: {
    id: number
    entrances: { start: { x: number; y: number }; end: { x: number; y: number } }[]
    ticketOffices: {
      id: number
      segment: {
        start: { x: number; y: number }
        end: { x: number; y: number }
      }
      queue: {
        id: number
        firstName: string
        lastName: string
        privilegy: Privilegy
        position: { x: number; y: number }
      }[]
      timeToServeTicket: number
      isClosed: boolean
      isReserved: boolean
      direction: string
      transactions: unknown[]
      reserved: boolean
    }[]
    segment: {
      start: { x: number; y: number }
      end: { x: number; y: number }
    }
  }
  client: {
    id: number
    firstName: string
    lastName: string
    privilegy: Privilegy
    position: { x: number; y: number }
  }
  timestamp: [number, number, number, number, number, number, number]
}
