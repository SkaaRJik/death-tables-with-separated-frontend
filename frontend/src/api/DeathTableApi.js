import Vue from 'vue'

import axios from 'axios'
import RestServiceConfig from "../../config/rest-service";


const url = RestServiceConfig.host + ':' + RestServiceConfig.port + "/api/public/death_table"

export default {

    getDeathTable: params => {
        
        
        return axios.get(url, {params:{

                location: params.location,
                
                MALE: params.maleData,
                FEMALE: params.femaleData,
                TOTAL: params.totalData,
                CITY_DWELLER: params.cityDwellersData,
                VILLAGER: params.villagersData,

                yearMode: params.yearMode,

                yearSelector: params.yearSelector,
                yearFrom: params.yearFrom,
                yearTo:  params.yearTo,
                years: params.years.toString(),

                ageSelector: params.ageSelector,
                ageFrom: params.ageFrom,
                ageTo: params.ageTo,
                ages: params.ages.toString(),
            
            }}
    )},
    getAllAges: function () {return axios.get(url+'/ages', {

    })},
    getAllBirthYears: function () {return axios.get(url+'/birth_years', {

    })},

    getAllLocations: function () {return axios.get(url+'/locations')
    }


    }
