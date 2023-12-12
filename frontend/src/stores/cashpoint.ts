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
    isError: false,
    error: ''
  }),
  actions: {
    async createCashpoint(data: CreateCashpointInput) {
      let direction

      const payload: CreateCashpointPayload = {
        segment: {
          start: data.start,
          end: data.end
        },
        isReserved: data.status === 'reserved',
        timeToServeTicket: data.timeToServeTicket,
        isClosed: data.status === 'stopped'
      }

      try {
        await axios.post(`${BASE_API}train_station/halls/1/ticket_offices`, payload)
      } catch (e) {
        const err = e as AxiosError
        this.isError = true
        this.error = err.message
      }
    }
  }
})

// path("/train_station", () -> {
//     path("/halls/{h_id}", () ->
