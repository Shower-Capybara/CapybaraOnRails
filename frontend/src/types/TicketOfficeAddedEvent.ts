import type { Privilegy } from '@/game_engine/types'
import type { Point } from '@/game_engine/types'

export interface TicketOfficeAddedEvent {
  type: string
  ticketOffice: {
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
    transactions: any[] // Додайте тип для transactions, якщо відомий
  }
  timestamp: [number, number, number, number, number, number, number]
}
