<template>
  <div class="flex flex-col w-full h-full">
    <div class="flex justify-between mb-4">
      <h1 class="text-xl font-bold">Налаштування</h1>
      <a href="#" class="text-blue-500">Історія обслуговування</a>
    </div>
    <div class="flex flex-col gap-4">
      <div class="mb-4">
        <label class="text-sm font-medium">Кількість кас</label>
        <input type="number" class="w-full form-control" />
      </div>
      <div class="mb-4">
        <label class="text-sm font-medium">Кількість входів</label>
        <input type="number" class="w-full form-control" />
      </div>
      <div class="mb-4">
        <label class="text-sm font-medium">Час обслуговування</label>
        <input type="number" class="w-full form-control" />
      </div>
      <div class="mb-4">
        <label class="text-sm font-medium">Стратегія появи</label>
        <select class="w-full form-control">
          <option value="стратегія 1">Стратегія 1</option>
          <option value="стратегія 2">Стратегія 2</option>
        </select>
      </div>
    </div>
    <div class="flex justify-end">
      <button class="btn btn-primary">Зберегти і перезапустити</button>
      <button class="btn btn-secondary">Скасувати зміни</button>
    </div>
  </div>
</template>

<script>
// -----
</script>
