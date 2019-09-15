<template>
  <v-container>
    <v-row>
      <h1>Secured area</h1>
    </v-row>
      <v-row>
      <v-col>
      <v-btn dark color="teal lighten-1" v-on:click="getSecuredUserInformation">Call secured user service</v-btn>
      </v-col>
      <v-col>
        <v-btn dark color="teal lighten-1" v-on:click="getSecuredAdminInformation">Call secured admin service</v-btn>
      </v-col>
      <v-col>
        <v-btn dark color="teal lighten-1" v-on:click="postData">Make secured POST request</v-btn>
      </v-col>
    </v-row>

    <v-row>
      <h2>Request URL: {{responseObj.url}}</h2>
    </v-row>
      <v-row>
        <h2>Request method: {{responseObj.method}}</h2>
      </v-row>
      <v-row>
        <h2>Status code: {{responseObj.statusCode}}</h2>
      </v-row>
      <v-row>
        <h2>Response: {{responseObj.msg}}</h2>
      </v-row>
      <v-row>
        <h2>X-XSRF-TOKEN: {{responseObj.xsrfToken}}</h2>
      </v-row>
  </v-container>
</template>

<script>

  import AdminApi from '../api/AdminApi'
  import UserApi from '../api/UserApi'

  export default {



  data () {
    return {
      responseObj: {
        url: '',
        statusCode: '',
        method: '',
        msg: '',
        xsrfToken: ''
      }
    }
  },
  created: function () {
  },
  methods: {
    async getSecuredUserInformation() {
      this.responseObj = {}

      try {
        const response = await UserApi.welcome()
        console.log("Get response: ", response.data);
        this.responseObj = this.parseResponse(response)

      }catch(error) {
        this.alert = true;
        this.responseObj = this.parseResponse(error)
      };

    },
    async getSecuredAdminInformation() {
      this.responseObj = {}

      try {
        const response = await AdminApi.welcome()
        console.log("Get response: ", response.data);
        this.responseObj = this.parseResponse(response)

      }catch(error) {
        this.alert = true;
        this.responseObj = this.parseResponse(error)
      };

    },
    async postData() {
      this.responseObj = {}

      try {
        const response = await UserApi.postData()
        console.log("Get response: ", response.data);
        this.responseObj = this.parseResponse(response)

      }catch(error) {
        this.alert = true;
        this.responseObj = this.parseResponse(error)
      };
    },
    parseResponse(response) {
      let respObj = {};
      respObj.url = response.config.url
      respObj.statusCode = response.status
      respObj.method = response.config.method
      respObj.msg = response.data.message ? response.data.message : response.data
      respObj.xsrfToken = response.config.headers['X-XSRF-TOKEN']
      return respObj
    }
  }
}
</script>
