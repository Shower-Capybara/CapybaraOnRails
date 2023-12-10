// Overworld.js
import Person from './Person'
//import GameObject from './GameObject'
import * as PIXI from 'pixi.js'

class Overworld {
  constructor(config) {
    this.element = config.element
    this.canvas = this.element.querySelector('.game-canvas')
    this.ctx = this.canvas.getContext('2d')
    this.person = null // Reference to your person object
  }

  startGameLoop() {
    const step = () => {
      this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height)

      // Draw the person
      this.person.update()
      this.person.sprite.draw(this.ctx)

      requestAnimationFrame(() => {
        step()
      })
    }

    step()
  }

  init() {
    console.log('init')
    // Initialize PIXI
    const app = new PIXI.Application({
      width: this.canvas.width,
      height: this.canvas.height
    })
    this.canvas.appendChild(app.view)

    // Create your person object
    this.person = new Person({
      x: 3,
      y: 3,
      width: 16,
      height: 16,
      texture: PIXI.Texture.fromImage('./images_png/human.png')
    })

    this.person.move({ x: 2, y: 2 })

    // Start the game loop
    this.startGameLoop()
  }
}

export default Overworld
