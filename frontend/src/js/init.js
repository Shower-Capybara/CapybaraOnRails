import Overworld from './Overworld'

const init = () => {
  return new Overworld({
    element: document.querySelector('.game-container')
  }).init()
}

export default init
