import axios from 'axios'
import RestServiceConfig from "../../config/rest-service";
import store from "../store/vuex-store";

const url = RestServiceConfig.host + ':' + RestServiceConfig.port + "/api/public"


export default {
    signIn: signInDetails => {return axios.post(url+'/login' , signInDetails)},
    signUp: signUpDetails => {return axios.post(url+'/signup' , signUpDetails)},
    isEmailRegistrated: email => { return axios.get(url+'/check-email', {params: {email: email}})},
    refreshTokenWithParam(tokens) { return axios.post(this.getRefreshTokenUrl(), tokens)},
    /*runTokenWatcher() {
        const savedThis = this

            if (Store.state.profile != null) {
                try {
                let timerId = setTimeout(async function tick() {
                    console.log('Old token : \n' + JSON.stringify(Store.state.profile.token))
                    const result = await savedThis.updateTokens(Store.state.profile.token)
                    const newTokens = await result.json()
                    Store.dispatch("updateTokens", newTokens)

                    timerId = setTimeout(tick, newTokens.accessTokenExpiredIn - newTokens.accessTokenExpiredIn / 5)
                    Store.dispatch('setTokenRefresher', timerId)

                }, Store.state.profile.token.accessTokenExpiredIn - Store.state.profile.token.accessTokenExpiredIn / 5);
                } catch (e) {
                    Store.dispatch("logout")
                }
            }

    },*/



    refreshToken() {
        const refreshToken = store.getters.getRefreshToken
        return axios.post(url+'/refresh-token', refreshToken)
    },

    clear(){
        store.dispatch('userSignOut')
        store.dispatch('clearTokens')
    },

    setTokens(tokens){
        store.dispatch("setTokens", tokens)
    },

    getUrl(){
        return url
    },

    getRefreshTokenUrl(){
        return url+'/refresh-token'
    }


}