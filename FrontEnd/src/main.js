import Vue from 'vue'
import 'babel-polyfill'
import ElementUI from 'element-ui'
import Axios from 'axios'
import NProgress from 'nprogress'
import api from './api/index.js'

import App from './App'
import router from './router'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/font-awesome-4.7.0/css/font-awesome.min.css'
import '@/assets/css/main.css'
import '@/assets/css/scrollbar.css'
import '@/assets/css/reset.css'
import 'nprogress/nprogress.css'
import 'animate.css'

import  VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'

import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'


import DropdownPlugin from './m/dropdown'
import NavbarPlugin from './m/navbar'
import * as Config from './utils/config'

Vue.use(ElementUI)
Vue.use(DropdownPlugin)
Vue.use(NavbarPlugin)
Vue.use(VueQuillEditor)

let whiteList = ['/login', '/401','/register']

router.beforeEach((to, from, next) => {
  NProgress.start()
  let user = sessionStorage.getItem("currentUser")
  let menus = sessionStorage.getItem("menus")
  console.log(to.path)
  if (whiteList.indexOf(to.path) !== -1 ) {
    next()
  } else {
    if (!user && to.name !== '404') {
      next({path: "/401"})
      NProgress.done()
    } else {
      next()
      }
    }
})

router.afterEach(transition => {
  NProgress.done()
})


window.APP_INFO = process.env.APP_INFO
Axios.defaults.withCredentials = true;
Vue.prototype.$api = api
Vue.prototype.$config = Config
Vue.config.productionTip = false

/* eslint-disable no-new */
var app = new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
})

window.app = app

