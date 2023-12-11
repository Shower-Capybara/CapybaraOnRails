import * as PIXI from 'pixi.js'
import * as TWEEN from '@tweenjs/tween.js'
import { FPS_LIMIT } from './constants'

export const app = new PIXI.Application({
  width: 800,
  height: 800,
  backgroundColor: '#ffffff'
})

PIXI.Ticker.shared.add(() => {
  TWEEN.update()
})

PIXI.Ticker.shared.maxFPS = FPS_LIMIT
