import { defineStore } from "pinia";
import router from "../router";
import { pinia } from ".";
export const useUserStore = defineStore('user', {
    persist: true,
    state: () => {
        return {
            id:"",
            username:"",
            is_login:false,
            is_supplier:false,
        }
    },
    actions: {
        logout() {
            this.id=""
            this.username=""
            this.is_login=false
            this.is_supplier=false,
            sessionStorage.removeItem("is_login")
            router.push('login')
        }
    }
})

export function useUserStoreWithOut() {
    return useUserStore(pinia)
}
  