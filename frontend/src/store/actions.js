import router from '../router/vue-router'

import { EventBus } from '../event-bus.js'

import axios from 'axios'
import RestServiceConfig from '../../config/rest-service.js'
import browserDetector from '../libraries/device'

const url = RestServiceConfig.host + ':' + RestServiceConfig.port

export const actions = {
  userSignIn ({commit}, payload) {
      const user = browserDetector.parse(navigator.userAgent);

      let data = {
          username: payload.username,
          password: payload.password,
          deviceInfo: {
              browser: user.browser.family + ' v.' + user.browser.version,
              os: user.os.name,

          }
      }
    commit('setLoading', true)
    axios.post(url+'/login', data)
     .then(res => {
       commit ('setAuth', true)
       commit ('setLoading', false)
       commit ('setError', null)
       EventBus.$emit('authenticated', 'User authenticated')
       router.push('/home')
     })
    .catch(error => {
      commit('setError', error.message)
      commit('setLoading', false)
    })
  },
  userSignOut ({commit}) {
    commit ('clearAuth')
    EventBus.$emit('authenticated', 'User not authenticated')
    router.push('/signIn')
  }
}
