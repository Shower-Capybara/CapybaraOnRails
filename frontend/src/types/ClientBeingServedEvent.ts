import type { Privilegy } from '@/game_engine/types'

export interface ClientBeingServedEvent {
  type: string
  ticketOffice: {
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
    direction: string
    transactions: any[] // Додайте тип для transactions, якщо відомий
    closed: boolean
    reserved: boolean
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
