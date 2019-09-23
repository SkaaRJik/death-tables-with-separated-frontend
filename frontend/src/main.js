// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router/vue-router'
import VueResource from 'vue-resource'
import VueCookie from 'vue-cookie'

import '@babel/polyfill'



import store from './store/vuex-store'
import * as axios from "axios"
import vuetify from "./vuetify/vuetify"
import UserApi from "./api/UserApi"
import AuthApi from "./api/AuthApi";

axios.defaults.withCredentials = true

// Setting up Axios on Vue Instance, for use via this.$axios
Vue.prototype.$axios = axios
Vue.config.productionTip = false




// Tell Vue to use the plugin
Vue.use(VueCookie);
Vue.use(VueResource)


/*axios.interceptors.response.use(response => {
    return Promise.resolve(response)
  },
  error => {
    if (error.response.status === 401) {
      console.log('Unauthorized, logging out ...');
      store.dispatch('userSignOut')
      router.replace('signIn')
      return Promise.reject(error)
    } else {
      return Promise.reject(error.response);
    }
  })*/

axios.interceptors.response.use( (response) => {
  // Return a successful response back to the calling service
  return response;
}, (error) => {
  // Return any error which is not due to authentication back to the calling service
  if (error.response.status !== 401) {
    return new Promise((resolve, reject) => {
      reject(error);
    });
  }

  // Logout user if token refresh didn't work or user is disabled
  if (error.config.url === AuthApi.getRefreshTokenUrl() || error.response.message == 'Account is disabled.') {

    AuthApi.clear();
    router.replace('login')

    return new Promise((resolve, reject) => {
      reject(error);
    });
  }

  // Try request again with new token
  return TokenService.refreshToken()
      .then((token) => {

        // New request with new token
        const config = error.config;
        const newTokens = token.data()

        config.headers['Authorization'] = newTokens.accessToken;
        TokenService.setTokens(newTokens)
        /*axios.defaults.headers.common['authorization'] = newToken.accessToken*/


        return new Promise((resolve, reject) => {
          axios.request(config).then(response => {
            resolve(response);
          }).catch((error) => {
            reject(error);
          })
        });

      })
      .catch((error) => {
        return new Promise((resolve, reject) => {
          reject(error);
        });
      });
});






/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  vuetify,
  components: { App },
  template: '<App/>'
})
