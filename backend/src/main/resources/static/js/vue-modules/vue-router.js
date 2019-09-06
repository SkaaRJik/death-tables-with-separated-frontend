import Vue from 'vue'
import VueRouter from 'vue-router'
import AuthPage from 'pages/AuthPage.vue'
import StatisticPage from 'pages/StatisticPage.vue'
import News from 'pages/News.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: News},
    { path: '/auth', component: AuthPage},
    { path: '/statistic', component: StatisticPage},
    { path: '*', component: News },
]

export default new VueRouter({
        mode: 'history',
        routes
    })

