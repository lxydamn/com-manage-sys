<template>
    <div class="container">
        <div class="header-box">
            <div class="logo">
                CMS 商品管理
            </div>
        </div>
        
        <div class="content">
            <div class="side-menu">
                <ul>
                    <li :class="route.name === 'specification' ? 'active' : ''">
                        <RouterLink to="specification">
                            规格管理
                        </RouterLink>
                    </li>
                    <li :class="route.name === 'goods' ? 'active' : ''">
                        <RouterLink to="goods">
                            商品管理
                        </RouterLink>
                    </li>
                    <li :class="route.name === 'categroy' ? 'active' : ''">
                        <RouterLink to="categroy">
                            商品类别管理
                        </RouterLink>
                    </li>
                    <li :class="route.name === 'goodsSpecification' ? 'active' : ''">
                        <RouterLink to="goodsSpecification">
                            商品规格管理
                        </RouterLink>
                    </li>
                    <li :class="route.name === 'brand' ? 'active' : ''">
                        <RouterLink to="brand">
                            商品品牌管理
                        </RouterLink>   
                    </li>
                </ul>
            </div>
            <div class="table-menu">
                <RouterView v-slot="{ Component }">
                    <component :is="Component"></component>
                </RouterView>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { useRoute } from 'vue-router';
export default defineComponent({
  components: {

  },

  setup() {
    const route = useRoute()
    return {
        route
    }
  }


})
</script>

<style scoped>
    .logo {
        display: flex;
        align-items: center;
        text-align: center;
        justify-content: center;
        font-size: 35px;
        font-weight: 900;
        @supports (-webkit-background-clip: text) or (background-clip: text) {
            background-image: linear-gradient(to right, #eea2a2 0%, #bbc1bf 19%, #57c6e1 42%, #b49fda 79%, #7ac5d8 100%);
            -webkit-background-clip:text;
            -webkit-text-fill-color:transparent;
        }
    }
    .side-menu ul {
        background-color: #FFFFFF;
        border-radius: 5px;
        padding: 5px;
        list-style-type: none;
        display: flex;
        flex-direction: column;
    }
    .side-menu a {
        color: #000;
    }

    .side-menu li {
        text-align: center;
        height: 50px;
        line-height: 50px;
        flex-grow: 1;
        transition: 0.1s;
    }
    .side-menu li:hover a {
        color: #1890FF;
    }
    .side-menu li.active a {
        color: #1890FF;
    }
    .side-menu li:hover {
        height: 65px;
        line-height: 65px;
        color: #1890FF;
        background-color: #F2F3F5;
    }
    .side-menu {
       width: 250px;

       border-radius: 10px;
    }
    .side-menu li.active {
        height: 65px;
        line-height: 65px;
        background-color: #F2F3F5;
    }
    .content {
        display: flex;
        padding: 30px;
    }
    .container {
        background-color: rgba(0, 0, 0, 0);
    }
    .table-menu {
        margin-left: 40px;
        background-color: #FFFFFF;
        width: 100%;
        height: 80vh;
    }
</style>