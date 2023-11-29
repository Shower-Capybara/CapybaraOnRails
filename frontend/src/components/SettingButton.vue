<template>
  <button class="config-button" @click="handleClick">
    <img class="save-icon" src="../assets/setting.svg" alt="Setting Icon" />
  </button>
</template>

<script setup>
  const handleClick = () => {
    // Add your click event handling logic here
    console.log('Button clicked!')
  }
</script>
