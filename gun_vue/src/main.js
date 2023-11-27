import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import axios from 'axios'
import router from '@/router/index.js'

const app = createApp(App)
app.config.globalProperties.$axios = axios
app.config.globalProperties.$serverUrl = 'http://localhost:8888'

loadFonts()

createApp(App)
  .use(router)
  .use(vuetify)
  .mount('#app')
