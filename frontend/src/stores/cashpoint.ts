import axios, { AxiosError } from 'axios'
import { defineStore } from 'pinia'
type Status = 'working' | 'stopped' | 'reserved'
interface Point {
  x: number
  y: number
}
interface Segment {
  start: Point
  end: Point
}
interface CreateCashpointPayload {
  segment: Segment
  direction: string
  isReserved: boolean
  timeToServeTicket: number
  isClosed: boolean
}
interface CreateCashpointInput {
  start: Point
  end: Point
  status: Status
  timeToServeTicket: number
}
const BASE_API = 'http://localhost:8000/'
export default defineStore('cashpoint', {
  state: () => ({
    isCashpointError: false,
    cashpointError: ''
  }),
  actions: {
    async createCashpoint(data: CreateCashpointInput) {
      let direction: string
      if (data.start.x - data.end.x === 2 && data.start.y < data.end.y) {
        direction = 'Down'
      } else if (data.start.x - data.end.x === 2 && data.start.y > data.end.y) {
        direction = 'Up'
      } else if (data.start.y - data.end.y === 2 && data.start.x > data.end.x) {
        direction = 'Right'
      } else {
        direction = 'Left'
      }
      const payload: CreateCashpointPayload = {
        segment: {
          start: data.start,
          end: data.end
        },
        isReserved: data.status === 'reserved',
        timeToServeTicket: data.timeToServeTicket,
        isClosed: data.status === 'stopped',
        direction
      }
      try {
        await axios.post(`${BASE_API}train_station/halls/1/ticket_offices`, payload)
      } catch (e) {
        const err = e as AxiosError
        this.isCashpointError = true
        this.cashpointError = err.message
      }
    },
    async toggleCashpointStatus({
      id,
      status
    }: {
      id: number
      status: Exclude<Status, 'reserved'>
    }) {
      try {
        if (status === 'stopped') {
          await axios.post(`${BASE_API}train_station/halls/${id}/ticket_offices/close`)
        } else {
          await axios.post(`${BASE_API}train_station/halls/${id}/ticket_offices/open`)
        }
      } catch (e) {
        const err = e as AxiosError
        this.isCashpointError = true
        this.cashpointError = err.message
      }
    }
  }
})
