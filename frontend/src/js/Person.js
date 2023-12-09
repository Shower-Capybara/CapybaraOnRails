// Person.js
import { reactive } from 'vue'
import GameObject from './GameObject'

class Person extends GameObject {
  constructor(config) {
    super(config)
    this.movingProgressRemaining = 0
    this.isPlayerControlled = config.isPlayerControlled || false
    this.directionUpdate = {
      up: ['y', -1],
      down: ['y', 1],
      left: ['x', -1],
      right: ['x', 1]
    }

    this.personData = reactive({
      x: this.x,
      y: this.y,
      direction: this.direction,
      movingProgressRemaining: this.movingProgressRemaining
    })
  }

  update(state) {
    this.updatePosition()

    if (this.isPlayerControlled && this.personData.movingProgressRemaining === 0 && state.arrow) {
      this.direction = state.arrow
      this.personData.movingProgressRemaining = 16
    }
  }

  updatePosition() {
    if (this.personData.movingProgressRemaining > 0) {
      const [property, change] = this.directionUpdate[this.direction]
      this.personData[property] += change
      this.personData.movingProgressRemaining -= 1
    }
  }
}

export default Person
