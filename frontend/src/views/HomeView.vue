<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { app } from '@/game_engine/app'
// import { generateMap } from '@/game_engine/map'
import EventList from '@/components/EventList/EventList.vue'
import * as PIXI from 'pixi.js'
import { Cashpoint } from '@/game_engine/cashpoint'
import { Map } from '@/game_engine/map'
import { CELL_SIZE, MAP_SIZE } from '@/game_engine/constants'
import { Sprite } from '@/game_engine/sprite'
import { Entrance } from '@/game_engine/entrance'
import useCashpointStore from '@/stores/cashpoint'
import useConfigsStore from '@/stores/configs'
import type { Point } from '@/game_engine/types'
import type {
  ClientAddedEvent,
  ClientBeingServedEvent,
  ClientBoughtTicketEvent,
  ClientLeftEvent,
  ClientMovedEvent,
  ClientServedEvent,
  TicketOfficeAddedEvent
} from '@/types/index'

const pixiCanvasContainer = ref<HTMLDivElement | null>(null)
const map = ref<Map | null>(null)
const cashpoints = ref<Cashpoint[]>([])
const entrances = ref<Entrance[]>([])
const sprites = ref<Sprite[]>([])
const configsStore = useConfigsStore()
// const cashpointStore = useCashpointStore()
// cashpointStore.createCashpoint() - action call example
// cashpointStore.cashpointError - get state exmaple
// watch()) => cashpointStore.cashpointError,

// const events = ref<type[]>([])
onMounted(() => {
  configsStore.getInitialConfig().then((res) => {
    if (res) {
      const entrances = res?.hall.entrances
      const ticketOffices = res?.hall.ticketOffices

      for (let ticketOffice of ticketOffices) {
        const status = ticketOffice.isClosed
          ? 'stopped'
          : ticketOffice.isReserved
            ? 'reserved'
            : 'working'
        const cashpoint = new Cashpoint(
          ticketOffice.id,
          ticketOffice.segment.start,
          ticketOffice.segment.end,
          map.value as Map,
          {
            status
          }
        )
        cashpoints.value.push(cashpoint)
        cashpoint.mount(app.stage)
      }

      for (let entrance of entrances) {
        const alignent = entrance.start.x <= 1 ? 'left' : entrance.start.y <= 1 ? 'top' : 'bottom'
        const entranceItem = new Entrance(1, entrance.start, map.value as Map, alignent)
        entranceItem.mount(app.stage)
      }
    }
  })
})
const isCashierAdditionDone = ref<boolean>(true)

const connection: WebSocket = new WebSocket('ws://localhost:8000/events') // replace with your WebSocket URL

const sendMessage = (message: string) => {
  console.log(connection)
  connection.send(message)
}
console.log('Starting connection to websocket server')

connection.onopen = (event: Event) => {
  console.log(event)
  console.log('Successfully connected')
}

const handleClientAddedEvent = (message: string) => {
  try {
    const parsedMessage = JSON.parse(message) as ClientAddedEvent
    const { id, firstName, lastName, privilegy, position } = parsedMessage.client

    const sprite = new Sprite(
      id,
      firstName,
      lastName,
      privilegy,
      { x: position.x, y: position.y },
      CELL_SIZE,
      CELL_SIZE,
      '/images/man.svg',
      map.value as Map
    )
    sprites.value?.push(sprite)
  } catch (error) {
    console.error('Error parsing JSON:', error)
  }
}

const handleClientBeingServedEvent = (message: string) => {
  try {
    const parsedMessage = JSON.parse(message) as ClientBeingServedEvent
    // do nothing right now
  } catch (error) {
    console.error('Error parsing JSON:', error)
  }
}

const handleClientBoughtTicketEvent = (message: string) => {
  try {
    const parsedMessage = JSON.parse(message) as ClientBoughtTicketEvent
    events.value.push(parsedMessage)
    // do nothing
  } catch (error) {
    console.error('Error parsing JSON:', error)
  }
}

const deleteSpriteById = (idToDelete: number) => {
  // Find the index of the sprite with the specified id
  const indexToDelete = sprites.value?.findIndex((sprite) => sprite.getId() === idToDelete)

  // If the sprite with the idToDelete is found
  if (indexToDelete !== undefined && indexToDelete !== -1) {
    // Remove the sprite from the sprites array
    const deletedSprite = sprites.value?.splice(indexToDelete, 1)[0]

    // Check if the sprite is successfully removed
    if (deletedSprite) {
      // Remove the sprite from the PIXI container (assuming the sprite has a reference to its PIXI display object)
      app.stage.removeChild(deletedSprite.getDisplayObject())
    } else {
      console.error(`Sprite with id ${idToDelete} not found.`)
    }
  } else {
    console.error(`Sprite with id ${idToDelete} not found.`)
  }
}

const handleClientLeftEvent = (message: string) => {
  try {
    const parsedMessage = JSON.parse(message) as ClientLeftEvent
    const clientIdToDelete = parsedMessage.client.id
    deleteSpriteById(clientIdToDelete)
  } catch (error) {
    console.error('Error parsing JSON:', error)
  }
}

const handleClientMovedEvent = (message: string) => {
  try {
    const parsedMessage = JSON.parse(message) as ClientMovedEvent
    const { position, id } = parsedMessage.client

    const foundSprite = sprites.value?.find((sprite) => sprite.getId() === id)
    if (foundSprite) {
      foundSprite.move(map.value?.getCell(position))
    } else {
      console.error('Sprite with the given ID was not found')
    }
  } catch (error) {
    console.error('Error parsing JSON:', error)
  }
}

const handleClientServedEvent = (message: string) => {
  try {
    const parsedMessage = JSON.parse(message) as ClientServedEvent
    // display on EventList
  } catch (error) {
    console.error('Error parsing JSON:', error)
  }
}

const handleTicketOfficeAddedEvent = (message: string) => {
  try {
    const parsedMessage = JSON.parse(message) as TicketOfficeAddedEvent

    const { id, segment, direction, isClosed, isReserved } = parsedMessage.ticketOffice
    const cashpoint = new Cashpoint(id, segment.start, segment.end, map.value as Map, {
      status: 'working'
    })

    cashpoints.value?.push(cashpoint)
  } catch (error) {
    console.error('Error parsing JSON:', error)
  }
}

connection.onmessage = (event: MessageEvent) => {
  try {
    const message = JSON.parse(event.data)
    console.log(message)
    // switch (message.type) {
    //   default:
    //     console.warn('Unknown message type:', message.type)
    // }
  } catch (error) {
    console.error('Error parsing JSON:', error)
  }
}

function getClickCanvasCoords(event) {
  const { x, y } = event.data.global
  return { x, y }
}

const addCashier = () => {
  isCashierAdditionDone.value = false
  map.value?.showGrid()
  app.stage.interactive = true

  let clicks = 0
  let x1, y1, x2, y2

  app.stage.on('pointerdown', (event) => {
    const { x, y } = getClickCanvasCoords(event)
    clicks++

    if (clicks === 1) {
      x1 = x
      y1 = y
    } else if (clicks === 2) {
      x2 = x
      y2 = y
      app.stage.interactive = false
      console.log('End:', x1, y1, x2, y2)
      let coords1 = map.value?.getCellCoordinates(x1, y1)
      let coords2 = map.value?.getCellCoordinates(x2, y2)

      console.log('Coords:', coords1.x, coords1.y, coords2.x, coords2.y)

      if (coords1.y > coords2.y) {
        let coords3Y = coords1.y
        coords1.y = coords2?.y
        coords2.y = coords3Y
      }

      if (coords1.x > coords2.x) {
        let coords3X = coords1.x
        coords1.x = coords2?.x
        coords2.x = coords3X
      }
      coords2.x = coords2.x + 1
      coords2.y = coords2.y + 1

      console.log('New:', coords1.x, coords1.y, coords2.x, coords2.y)
      const number = cashpoints.value.length + 1
      const newCashpoint = new Cashpoint(
        number,
        { x: coords1.x, y: coords1.y },
        { x: coords2.x, y: coords2.y },
        map.value as Map,
        {
          status: 'working'
        }
      )
      cashpoints.value.push(newCashpoint)
      newCashpoint.mount(app.stage)
      console.log(cashpoints.value)
      map.value?.hideGrid()
      isCashierAdditionDone.value = true
      return
    }
  })
}

onMounted(() => {
  const container = pixiCanvasContainer.value

  if (container) {
    map.value = new Map(MAP_SIZE, CELL_SIZE, app.stage)
    map.value.generate()
    // const cashpoint1 = new Cashpoint(1, { x: 0, y: 3 }, { x: 1, y: 5 }, map.value as Map, {
    //   status: 'working'
    // })

    // const cashpoint2 = new Cashpoint(2, { x: 0, y: 7 }, { x: 1, y: 9 }, map.value as Map, {
    //   status: 'working'
    // })

    // const cashpoint3 = new Cashpoint(3, { x: 0, y: 11 }, { x: 1, y: 13 }, map.value as Map, {
    //   status: 'working'
    // })

    // const cashpoint4 = new Cashpoint(
    //   4,
    //   { x: 3, y: MAP_SIZE - 1 },
    //   { x: 5, y: MAP_SIZE },
    //   map.value as Map,
    //   {
    //     status: 'stopped'
    //   }
    // )

    // const cashpoint5 = new Cashpoint(
    //   5,
    //   { x: 3, y: MAP_SIZE - 1 },
    //   { x: 5, y: MAP_SIZE },
    //   map.value as Map,
    //   {
    //     status: 'stopped'
    //   }
    // )

    // cashpoints.value = [cashpoint1, cashpoint2, cashpoint3, cashpoint4, cashpoint5]
    // for (let cashpoint of cashpoints.value) {
    //   cashpoint.mount(app.stage)
    // }

    const canvasElement = app.view as HTMLCanvasElement
    container.appendChild(canvasElement)
  }
})
</script>

<template>
  <main class="min-h-screen max-h-screen flex flex-row justify-center w-full">
    <div
      class="w-8/12 h-screen border-4 border-primary pixi-container"
      ref="pixiCanvasContainer"
    ></div>
    <div
      class="w-4/12 h-screen border-4 border-l-yellow_design border-t-stroke_grey border-r-stroke_grey border-b-stroke_grey"
    >
      <EventList @on-add-cashier="addCashier" :is-cashier-addition-done="isCashierAdditionDone" />
    </div>
  </main>
</template>

<style>
canvas {
  display: block;
  width: 100%;
  height: 100%;
}
</style>
