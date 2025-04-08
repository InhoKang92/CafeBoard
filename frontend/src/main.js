import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faList } from '@fortawesome/free-solid-svg-icons'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { faR } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'



library.add(faList)
library.add(fas)
library.add(faR)

createApp(App).use(router).component('font-awesome-icon', FontAwesomeIcon).mount('#app')



global.jQuery = require('jquery');
var $ = global.jQuery;
window.$ = $;