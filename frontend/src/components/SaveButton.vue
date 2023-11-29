<template>
  <button class="save-button" @click="handleClick">
    <img class="save-icon" src="../assets/save.svg" alt="Save Icon" />
  </button>
</template>

<script setup>
  const handleClick = () => {
    // Add your click event handling logic here
    console.log('Button clicked!')
  }
</script>
