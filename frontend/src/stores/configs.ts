import { defineStore } from 'pinia'

const BASE_API = 'http://localhost:8000/'

export default defineStore('configs', {
  state: () => ({
    isError: false,
    error: ''
  }),
  actions: {}
})
