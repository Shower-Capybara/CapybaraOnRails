import WebSocket from 'ws'

const port = 1234
const ws = new WebSocket(`ws://localhost:${port}`)

ws.on('open', () => {
  console.log(`Client connected`)
  ws.send(`Client connected`)
})

ws.on('message', (data) => {
  console.log(`Received a message from the server: ${data}`)
})
ws.on('error', (error) => {
  console.error(`WebSocket encountered an error: ${error.message}`)
})
