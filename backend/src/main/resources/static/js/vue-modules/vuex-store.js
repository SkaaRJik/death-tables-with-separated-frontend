import Vue from 'vue'
import Vuex from 'vuex'
import authApi from "api/auth"
Vue.use(Vuex);

import axios from 'axios'

export default new Vuex.Store({
    state: {
        profile: null,
        dialogShow: false,
        tokenRefresher: null

    },
    mutations: {
        showHideDialog(state) {
            state.dialogShow = !state.dialogShow
        },
        setProfileMutation(state, profile){
            state.profile = profile
            state.profile.avatar = profile.avatar.data
            axios.defaults.headers.common['Authorization'] =  profile.token.accessToken;
            Vue.http.headers.common['Authorization'] = profile.token.accessToken;

        },
        updateTokensMutation(state, token) {
            state.profile.token = token
            Vue.http.headers.common['Authorization'] = token.accessToken;
        },

        setTokenRefresherMutation(state, tokenRefresher){
            state.tokenRefresher = tokenRefresher
        },

        logoutMutation(state){
            state.profile = null
            this.$router.replace('/')
        }

    },
    actions:{
        showHideDialog({ commit }){
            commit('showHideDialog')
        },

        setProfile({commit, state}, profile){
            if(profile!=null){
                commit('setProfileMutation', profile)
            }
        },

        updateTokens({commit, state}, newTokens){
            if(state.profile!=null) {
                commit('updateTokensMutation', newTokens)
            }
        },

        setTokenRefresher({commit, state}, tokenRefresher){
            if(state.profile!=null) {
                commit('setTokenRefresherMutation', tokenRefresher)
            }
        },
        getAccessToken({commit, state}){
            return state.profile == null ? null : state.profile.token.accessToken
        },

        logout({commit, state}){
            commit('logoutMutation')

        }

    }
})