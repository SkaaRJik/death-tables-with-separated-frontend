import axios from 'axios'
import RestServiceConfig from '../../config/rest-service.js'


const securedUrl = RestServiceConfig.host + ':' + RestServiceConfig.port + "/api/secured/user"
const url = RestServiceConfig.host + ':' + RestServiceConfig.port + "/api/public"

export default {
  welcome: function () {
    return axios.get(securedUrl+'/welcome')
  },
  postData() {
    return axios.post(securedUrl+'/postdata')
  },
  getInfo() {
    return axios.get(url+'/test')
  }
}
