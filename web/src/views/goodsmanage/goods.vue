<template>
    <h1>goods</h1>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { useRoute } from 'vue-router';

export default defineComponent({
  components: {

  },

  setup() {
    const route = useRoute()
    const init = () => {
      console.log(route.name)
    }
    init()
    return {

    }
  }


})
</script>

<style>
</style>