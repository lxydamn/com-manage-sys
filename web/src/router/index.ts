import {createRouter, createWebHistory} from 'vue-router'


const routes = [
    {
        path:'/',
        redirect:'/index'
    },
    {
        path: '/index',
        component: () => import(/* webpackChunkName: "index" */ '../views/hello.vue')
    },

];

const router = createRouter({
    history : createWebHistory(),
    routes,
})

export default router