import axios, { AxiosError } from 'axios'
import { defineStore } from 'pinia'
interface Point {
  x: number
  y: number
}
interface Segment {
  start: Point
  end: Point
}
const BASE_API = 'http://localhost:8000/'
export default defineStore('entrance', {
  state: () => ({
    isEntranceError: false,
    entranceError: ''
  }),
  actions: {
    async craeteEntrance(data: Segment) {
      try {
        await axios.post(`${BASE_API}train_station/1/halls/1/entrance`, data)
      } catch (e) {
        const err = e as AxiosError
        this.isEntranceError = true
        this.entranceError = err.message
      }
    }
  }
})
