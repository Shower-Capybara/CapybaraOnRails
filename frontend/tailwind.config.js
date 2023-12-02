/* eslint-disable no-undef */
module.exports = {
  content: ['./index.html', './src/**/*.{vue,js,ts}'],
  theme: {
    extend: {
      colors: {
        primary: '#122083',
        primary_hover: '#344097',
        font_black: '#000000',
        font_white: '#FFFFF',
        background_grey: '#FAFAFA',
        dropdown_grey: '#AEAEAE',
        free_space_green: '#15B42E',
        yellow_design: '#FFAF44',
        yellow_status: '#F9BE26',
        green_status: '#15B42E',
        red_status: '#F41919',
        grey_light: '#FAF9F9',
        stroke_grey: '#F4F4F4',
        'stroke_grey-25': '#B3B3B3',
        'stroke_grey-50': '#D0D0D0'
      }
    }
  },
  variants: {
    extend: {}
  },
  plugins: []
}
