<template>
  <v-layout column>
    <v-flex xs12 class="text-xs-center" mt-5>
      <h1>Home page</h1>
      <h2>{{data}}</h2>

      <h2><router-link to="/secured" color="black">Go to secured page</router-link></h2>
    </v-flex>
  </v-layout>
</template>

<script>
  import UserApi from '../api/UserApi.js'

export default {
  name: 'Home',
  data () {
    return {
      data: '',
      status: '',
      alert: false
    }
  },
  created: function () {
    this.getHomePageInformation();
  },
  methods: {
    async getHomePageInformation() {
      try {
        const response = await UserApi.getInfo()
        console.log("Get response: ", response.data);
        this.data = response.data
      } catch(error) {
        this.alert = true;
      };
    }
  }
}
</script>

<style scoped>
  a {
    color: #42b983;
  }
</style>
