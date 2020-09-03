import Vue from 'vue'
import Router from 'vue-router'

import AppView from '@/components/app-view'

Vue.use(Router)

const page = name => () => import('@/pages/' + name)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '',
      component: AppView,
      children: [
        {path: '/', name: 'index', component: page('home')},
        {path: '/user', name: 'user', component: page('user/User')},
        {path: '/employee', name: 'employee', component: page('employee/Employee')},
        {path: '/role', name: 'role', component: page('role/Role')},
        {path: '/menu', name: 'menu', component: page('menu/Menu')},
      ],
    },
    {path: '/login', name: 'login', component: page('login')},
    {path: '/401', name: '401', component: page('401')},
    {path: '/500', name: '500', component: page('500')},
    {path: '/404', name: '404', component: page('404')},
    {path: '/register', name: 'p-register', component: page('register')},
    {path: '*', redirect: {name: '404'}}
  ]
})
