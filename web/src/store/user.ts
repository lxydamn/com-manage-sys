import { defineStore } from "pinia";

export const useUserStore = defineStore('user', {
    persist: true,
    state: () => {
        return {
            id:"",
            username:"",
            is_login:false
        }
    }
})