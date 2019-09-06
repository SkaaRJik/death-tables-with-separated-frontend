
import axios from 'axios'
import RestServiceConfig from '../../config/rest-service.js'


const url = RestServiceConfig.host + ':' + RestServiceConfig.port + "/api/secured/admin"

export default {
  welcome: function () {
    return axios.get(url+'/welcome')
  }



}
