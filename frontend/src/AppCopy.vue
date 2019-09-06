<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div id="app">
    <v-app id="inspired">

      <v-navigation-drawer
              v-model="drawer"
              app
              clipped
      >
        <v-list dense>
          <v-list-item @click="">
            <v-list-item-action>
              <v-icon>dashboard</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>Dashboard</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item @click="">
            <v-list-item-action>
              <v-icon>settings</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>Settings</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>

      <v-app-bar
              app
              clipped-left
      >
        <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
        <v-toolbar-title>
          <router-link to="/home" tag="span" style="cursor: pointer">
            Spring Security JWT CSRF Demo
          </router-link>
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-toolbar-items class="hidden-xs-only">
          <v-btn
                  text
                  v-for="item in menuItems"
                  :key="item.title"
                  :to="item.path">
            <v-icon left>{{ item.icon }}</v-icon>
            {{ item.title }}
          </v-btn>
          <v-btn text @click="userSignOut" v-if="isAuthenticated">
            <v-icon left>exit_to_app</v-icon>
            Sign Out
          </v-btn>
        </v-toolbar-items>
      </v-app-bar>

      <v-content>
        <router-view/>
      </v-content>

      <v-footer app>
        <span>&copy; 2019</span>
      </v-footer>

    </v-app>
  </div>
</template>

<script>
  import { EventBus } from './event-bus.js'

  export default {
    name: 'App',
    data() {
      return {
        isAuthenticated: false,
        drawer: false
      }
    },
    created () {
      this.isAuthenticated = localStorage.getItem("auth")
      //Use localstorage because isAuthenticated from $store is undefined when event is called
      EventBus.$on('authenticated', () => {
        this.isAuthenticated = localStorage.getItem("auth")
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
            {title: 'Secured page', path: '/secured', icon: 'vpn_key'}
          ]
        } else {
          return [
            {title: 'Home', path: '/home', icon: 'home'},
            {title: 'Sign In', path: '/signIn', icon: 'lock_open'}
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
