import Vue from 'vue'

import store from 'vue-modules/vuex-store'
import router from 'vue-modules/vue-router'
import vuetify from 'vue-modules/vuetify'

import "core-js/stable";
import "regenerator-runtime/runtime";

import 'api/resource'

import App from 'pages/App.vue'


var app = new Vue({
    el: '#app',
    store,
    router,
    vuetify,
    render: a => a(App)



})
