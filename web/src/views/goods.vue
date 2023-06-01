<template>
    <div class="container">
        <div class="logo">
            CMS 商品管理
        </div>
        <div class="content">

        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  components: {

  },

  setup() {

  }


})
</script>

<style scoped>
    .logo {
        display: flex;
        align-items: center;
        text-align: center;
        justify-content: center;
        font-size: 30px;
        height: 8vh;
        font-weight: 900;
        @supports (-webkit-background-clip: text) or (background-clip: text) {
            background-image: linear-gradient(to right, #eea2a2 0%, #bbc1bf 19%, #57c6e1 42%, #b49fda 79%, #7ac5d8 100%);
            -webkit-background-clip:text;
            -webkit-text-fill-color:transparent;
        }
    }
    .content {
        background-color:#dedddd;
        height: 100vh;
    }
</style>