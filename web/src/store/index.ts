import { createPinia } from 'pinia'
import { App } from 'vue';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
export function setupStore(app: App<Element>) {
    app.use(pinia)
}
export {pinia}