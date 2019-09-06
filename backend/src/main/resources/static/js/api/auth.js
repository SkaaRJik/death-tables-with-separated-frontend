import Vue from 'vue'
import Store from 'vue-modules/vuex-store'



export default {
    signIn: signInDetails => {return Vue.resource('/api/public/auth/signin').save({}, signInDetails)},
    signUp: signUpDetails => {return Vue.resource('/api/public/auth/signup').save({}, signUpDetails)},
    isEmailRegistrated: email => {return Vue.resource('/api/public/auth/check-email').get({email: email})},
    updateTokens(tokens) {return Vue.resource('/api/private/auth/refresh-token').update({}, tokens)},
    runTokenWatcher() {
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

    }
}