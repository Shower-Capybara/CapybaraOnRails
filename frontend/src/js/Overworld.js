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
      this.person.update(/* pass your state here if needed */)
      this.person.sprite.draw(this.ctx)

      requestAnimationFrame(() => {
        step()
      })
    }

    step()
  }

  init() {
    // Initialize PIXI
    const app = new PIXI.Application({
      width: this.canvas.width,
      height: this.canvas.height
    })
    this.canvas.appendChild(app.view)

    // Create your person object
    this.person = new Person({
      // ... pass your configuration here
    })

    // Start the game loop
    this.startGameLoop()
  }
}

export default Overworld
