import type { AxiosError } from 'axios'
import axios from 'axios'
import { defineStore } from 'pinia'
interface Position {
  x: number
  y: number
}
interface Privilege {
  type: string
  significance: number
}
interface Person {
  id: number
  firstName: string
  lastName: string
  privilege: Privilege | null
  position: Position
}
interface Segment {
  start: Position
  end: Position
}
interface TicketOffice {
  id: number
  segment: Segment
  queue: Person[]
  timeToServeTicket: number
  isClosed: boolean
  isReserved: boolean
  direction: string
  transactions: any[] | null
}
interface Hall {
  id: number
  entrances: Segment[]
  ticketOffices: TicketOffice[]
  segment: Segment
}
export interface InitialStateData {
  id: number
  hall: Hall
}
const BASE_API = 'http://localhost:8000/'
export default defineStore('configs', {
  state: () => ({
    isConfigError: false,
    configError: ''
  }),
  actions: {
    async getInitialConfig(): Promise<InitialStateData | undefined> {
      try {
        const response = await axios.get<InitialStateData>(`${BASE_API}train_station/1`)
        return response.data
      } catch (e) {
        const err = e as AxiosError
        this.isConfigError = true
        this.configError = err.message
      }
    }
  }
})
