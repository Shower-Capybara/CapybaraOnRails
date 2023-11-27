/* eslint-disable no-undef */
module.exports = {
  purge: ['./index.html', './src/**/*.{vue,js,ts}'],
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {
      colors: {
        primary: '#122083',
        font_black: '#000000',
        font_white: '#FFFFF',
        dropdown_grey: '#AEAEAE',
        background_grey: '#B3B3B3',
        free_space_green: '#15B42E',
        yellow_design: '#FFAF44',
        yellow_status: '#F9BE26',
        green_status: '#15B42E',
        red_status: '#F41919'
      }
    }
  },
  variants: {
    extend: {}
  },
  plugins: []
}
