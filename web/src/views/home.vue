<template>

  <headerBar></headerBar>

  <RouterView v-slot="{ Component }">
    <div class="container">
      <component :is="Component"></component>
    </div>
  </RouterView>

  </template>
  <script lang="ts">
  import headerBar from '../components/headerBar.vue';
  import { defineComponent } from 'vue';
  export default defineComponent({
    components: {
      headerBar,
    },
    setup() {
      return {
      };
    },
  });
  </script>
  
  <style scoped>
    .container {
      background-color: rgb(223, 222, 222);
      height: 100vh;
    }
  </style>
  