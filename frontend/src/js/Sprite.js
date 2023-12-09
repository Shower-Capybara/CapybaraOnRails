// Sprite.js
import { reactive } from 'vue'

class Sprite {
  constructor(config) {
    this.image = new Image()
    this.image.src = config.src
    this.image.onload = () => {
      this.isLoaded = true
    }

    this.shadow = new Image()
    this.useShadow = true
    if (this.useShadow) {
      this.shadow.src = '/images/characters/shadow.png'
    }
    this.shadow.onload = () => {
      this.isShadowLoaded = true
    }

    this.animations = config.animations || {
      idleDown: [[0, 0]]
    }
    this.currentAnimation = config.currentAnimation || 'idleDown'
    this.currentAnimationFrame = 0

    this.gameObject = config.gameObject

    this.spriteData = reactive({
      x: this.gameObject.x - 8,
      y: this.gameObject.y - 18,
      isLoaded: false,
      isShadowLoaded: false
    })
  }

  draw(ctx) {
    const { x, y, isLoaded, isShadowLoaded } = this.spriteData

    isShadowLoaded && ctx.drawImage(this.shadow, x, y)
    isLoaded && ctx.drawImage(this.image, 0, 0, 32, 32, x, y, 32, 32)
  }
}

export default Sprite
