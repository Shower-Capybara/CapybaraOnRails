<template>
  <button class="add-entrance-button" @click="handleClick">Додати вхід</button>
</template>

<script setup>
const handleClick = () => {
  // Add your click event handling logic here
  console.log('Button entrance clicked!')
}
</script>

<style scoped>
.add-entrance-button {
  margin: 5px;
  background-color: #122083;
  color: white;
  font-size: 16px;
  font-weight: bold;
  padding: 10px 20px;
  text-align: center;
  border: none;
  cursor: pointer;
  border-radius: 10px;
}
</style>
