import Overworld from './Overworld'
;(function () {
  const overworld = new Overworld({
    element: document.querySelector('.game-container')
  })
  overworld.init()
})()
