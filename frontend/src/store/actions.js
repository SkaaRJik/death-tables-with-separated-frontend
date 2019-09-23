import router from '../router/vue-router'

import { EventBus } from '../event-bus.js'

import axios from 'axios'
import RestServiceConfig from '../../config/rest-service.js'
import browserDetector from '../libraries/device'

import AuthApi from "../api/AuthApi";

const url = RestServiceConfig.host + ':' + RestServiceConfig.port

export const actions = {
  async userSignIn ({commit}, payload) {
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
      try {


          const res = await AuthApi.signIn(data)
          commit('setAuth', true)
          commit('setLoading', false)
          commit('setError', null)

         /* const refreshToken = EventBus.$cookie.get('Refresh')
          EventBus.$cookie.delete('Refresh')*/
         const  data = res.data

          commit('setUser', data.userProfile)
          dispatch('setTokens', data.tokens)



          EventBus.$emit('authenticated', 'User authenticated')
          router.push('/statistics')

      }catch(error){
      commit('setError', error.message)
      commit('setLoading', false)
    }
  },
  userSignOut ({commit}) {
      commit ('clearAuth')
      commit('clearRefreshToken')
      commit ('setUser', null)
      EventBus.$emit('authenticated', 'User not authenticated')
      router.push('/signIn')
  },
    setTokens({commit}, tokens) {
        axios.defaults.headers.common['authorization'] = tokens.accessToken
        commit('setTokens', tokens)
    },
    clearTokens({commit}){
        commit('clearTokens')
    },
}
