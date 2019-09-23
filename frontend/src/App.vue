<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-app id="inspire">
    <v-navigation-drawer
            v-model="drawer"
            app
            clipped
    >
      <v-list dense>

        <v-list-item
                v-for="item in menuItems"
                      :key="item.title"
                      :to="item.path">
          <v-list-item-action>
            <v-icon>{{item.icon}}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{item.title}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar
            app
            clipped-left
    >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>Application</v-toolbar-title>
    </v-app-bar>

    <v-content>
      <router-view/>
    </v-content>

    <v-footer app>
      <span>&copy; 2019</span>
    </v-footer>

  </v-app>
</template>

<script>
  import { EventBus } from './event-bus.js'
  import AuthApi from './api/AuthApi'
  import router from './router/vue-router'


  export default {
    name: 'App',
    data() {
      return {
        isAuthenticated: false,
        tokens: null,
        drawer: false
      }
    },
    created () {

      this.tokens = localStorage.getItem("tokens")
      console.log('tokens: ' + this.tokens)

      if (this.tokens == null){
        this.isAuthenticated = false
        router.replace('login')
      } else {
        AuthApi.refreshTokenWithParam(this.tokens.refreshToken).then(res => {
          this.tokens = res.data
          localStorage.setItem("tokens", this.tokens)
          this.isAuthenticated = true

        })
      }
      console.log('-----------------------------------------------------------')
      console.log('tokens: ' + this.tokens)
      console.log('isAuthenticated: ' + this.isAuthenticated)

      localStorage.setItem("auth", this.isAuthenticated)



      //Use localstorage because isAuthenticated from $store is undefined when event is called
      EventBus.$on('authenticated', () => {
        this.isAuthenticated = localStorage.getItem("auth")
        console.log('isAuthenticated: ' + this.isAuthenticated)
      });
    },
    beforeDestroy() {
      EventBus.$off('authenticated')
    },
    computed: {
      menuItems() {
        if (this.isAuthenticated) {
          return [
            {title: 'Home', path: '/home', icon: 'home'},
            {title: 'Statistic', path: '/statistic', icon: 'multiline_chart'},
            {title: 'Secured page', path: '/secured', icon: 'vpn_key'}
          ]
        } else {
          return [
            {title: 'Home', path: '/home', icon: 'home'},
            {title: 'Statistic', path: '/statistic', icon: 'multiline_chart'},
            {title: 'Sign In', path: '/login', icon: 'lock_open'}
          ]
        }
      }
    },
    methods: {
      userSignOut() {
        this.$store.dispatch('userSignOut')
      }
    }
  }
</script>

<style scoped>

</style>
