import type { Privilegy } from '@/game_engine/types'

export interface ClientServedEvent {
  type: string
  ticketOfficeId: number
  client: {
    id: number
    firstName: string
    lastName: string
    privilegy: Privilegy
    position: { x: number; y: number }
  }
  timestamp: [number, number, number, number, number, number, number]
}
