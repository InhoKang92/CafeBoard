import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/AllBoard',
    name: 'AllBoard',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AllBoard.vue')
  },
  {
    path: '/NoticeBoard',
    name: 'NoticeBoard',
    component: () => import('../views/NoticeBoard.vue')
  },
  {
    path: '/NewsBoard',
    name: 'NewsBoard',
    component: () => import('../views/NewsBoard.vue')
  },
  {
    path: '/DebateBoard',
    name: 'DebateBoard',
    component: () => import('../views/DebateBoard.vue')
  },
  {
    path: '/ReadBoard',
    name: 'ReadBoard',
    component: () => import('../views/ReadBoard.vue')
  },
  {
    path: '/BoardWrite',
    name: 'BoardWrite',
    component: () => import('../views/BoardWrite.vue')
  },
  {
    path: '/ModifyBoard',
    name: 'ModifyBoard',
    component: () => import('../views/ModifyBoard.vue')
  },
  {
    path: '/LoginPage',
    name: 'LoginPage',
    component: () => import('../views/LoginPage.vue')
  },
  {
    path: '/AllVue',
    name: 'AllVue',
    component: () => import('../views/AllVue.vue')
  },
  {
    path: '/MemberVue',
    name: 'MemberVue',
    component: () => import('../views/MemberVue.vue')
  },
  {
    path: '/AdminVue',
    name: 'AdminVue',
    component: () => import('../views/AdminVue.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
