import { createRouter, createWebHistory } from 'vue-router';
import { useUserStoreWithOut } from '../store/user';



const routes = [
    {
        path:'/',
        redirect:'/login'
    },
    {
        path: '/home',
        name: 'home',
        meta: {
            author:true
        },
        component: () => import(/* webpackChunkName: "home" */ '../views/home.vue'),
        children: [
            {
                path: '/customer',
                name: 'customer',
                meta: {
                    author:true
                },
                component: () => import(/* webpackChunkName: "customer" */ '../views/customer.vue'),
            },
            {
                path: '/supplier',
                name: 'supplier',
                meta: {
                    author:true
                },
                component: () => import(/* webpackChunkName: "supplier" */ '../views/supplier.vue'),
            },
            
        ]
    },
    {
        path: '/login',
        name:'login',
        meta: {
            author:false
        },
        component: () => import(/* webpackChunkName: "login" */ '../views/login.vue')
    },
    {
        path: '/register',
        name: 'register',
        meta: {
            author:false
        },
        component: () => import(/* webpackChunkName: "register" */ '../views/register.vue'),
    },
    {
        path: '/goods',
        name: 'goods',
        meta: {
            author:false
        },
        component: () => import(/* webpackChunkName: "goods" */ '../views/goods.vue'),
    },

];

const router = createRouter({
    history : createWebHistory(),
    routes,
})

router.beforeEach((to, _from, next) => {
    if (to.meta.author && !sessionStorage.getItem("is_login")) {
        const userStore = useUserStoreWithOut()
        if (userStore.is_supplier) {
            next({name:'/supplier'})
        } else {
            next({name:'/customer'})
        }
    } else {
        next()
    }
})
export default router