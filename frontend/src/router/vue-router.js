import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/pages/Home'
import NotFound from '@/pages/NotFound'
import SignIn from '@/pages/SignIn'
import Secured from '@/pages/Secured'
import StatisticPage from "@/pages/StatisticPage";

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/home',
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      meta: {nonRequiresAuth: true}
    },

    {
      path: '/statistic',
      name: 'Statistic',
      component: StatisticPage,
      meta: {nonRequiresAuth: true}
    },
    {
      path: '/secured',
      name: 'Secured',
      component: Secured,
      meta: {nonRequiresAuth: false}
    },
    {
      path: '/login',
      name: 'signIn',
      component: SignIn,
      meta: {loginPage: true, nonRequiresAuth: true}
    },
    {
      path: '*',
      component: NotFound
    }
  ]
})

router.beforeEach((to, from, next) => {
  const requiresAuth = !to.matched.some(record => record.meta.nonRequiresAuth)
  const isLoginPage = to.matched.some(record => record.meta.loginPage)
  const isAuthenticated = localStorage.getItem("auth")
  if (requiresAuth && !isAuthenticated) {
    next('/signIn')
  } else if (isLoginPage && isAuthenticated) {
    router.push('/home')
  }
  next()
})

export default router