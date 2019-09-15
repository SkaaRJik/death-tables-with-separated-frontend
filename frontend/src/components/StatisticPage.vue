<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <!-- <v-container class="d-flex align-content-center flex-wrap">
         &lt;!&ndash; Stack the columns on mobile by making one full-width and the other half-width &ndash;&gt;

         <v-row>
             <v-col

                     md="3"
                     col="3"
             >
                 <v-card
                         class="pa-2"
                         outlined

                 >



                     <v-chip-group
                             column
                             active-class="primary&#45;&#45;text"
                             multiple
                             v-model="yearSelector"
                     >
                         <v-chip>
                             Дата рождения
                         </v-chip>
                         <v-chip>
                             Календарный год
                         </v-chip>
                     </v-chip-group>
                 </v-card>
             </v-col>

             &lt;!&ndash;Вторая колонка&ndash;&gt;

             <v-col

                     md="4"
                     col="4"
             >
                 <v-card
                         class="pa-2"
                         outlined
                         tile
                         :disabled="!yearSelector.includes(0)"
                 >
                     <label>Данные по дате рождения</label>
                     <v-chip-group
                             column
                             active-class="primary&#45;&#45;text"
                             v-model="birthYearSelector"
                     >
                         <v-chip>
                             За определенный период
                         </v-chip>
                         <v-chip>
                             За определенные года
                         </v-chip>
                     </v-chip-group>


                     <v-combobox v-if="birthYearSelector === 1"
                                 v-model="birthYearData"
                                 :items="dates"
                                 label="Выберите года"
                                 multiple
                                 chips
                     ></v-combobox>

                     <template v-if="birthYearSelector === 0">
                         <v-container class="d-flex align-content-center flex-wrap">
                             <v-row>
                                 <v-col
                                         md="6"

                                 >
                                     <v-combobox
                                             v-model="birthYearFrom"
                                             :items="dates"
                                             label="От"
                                     ></v-combobox>
                                 </v-col>
                                 <v-col md="6"
                                 >
                                     <v-combobox
                                             v-model="birthYearTo"
                                             :items="dates"
                                             label="До"
                                     ></v-combobox>
                                 </v-col>
                             </v-row>
                         </v-container>
                     </template>

                     <template v-if="(birthYearFrom >= minYear && birthYearTo <= maxYear) || birthYearData.length > 0">


                         <label justify-center align-center>
                             Возраст
                         </label>

                         <v-chip-group
                                 column
                                 active-class="primary&#45;&#45;text"
                                 v-model="ageForBirthYearSelector"
                         >
                             <v-chip>
                                 В диапозоне
                             </v-chip>
                             <v-chip>
                                 Определенный возраст
                             </v-chip>
                         </v-chip-group>

                         <v-combobox v-if="ageForBirthYearSelector === 1"
                                     v-model="agesOfBirthYear"
                                     :items="ages"
                                     label="Выберите возраста"
                                     multiple
                                     chips
                         ></v-combobox>

                         <template v-if="ageForBirthYearSelector === 0">
                             <v-container class="d-flex align-content-center flex-wrap">
                                 <v-row>
                                     <v-col
                                             md="6"

                                     >
                                         <v-combobox
                                                 v-model="ageOfBirthYearFrom"
                                                 :items="ages"
                                                 :value="ageOfBirthYearFrom"
                                                 label="От"
                                         ></v-combobox>
                                     </v-col>
                                     <v-col md="6"
                                     >
                                         <v-combobox
                                                 v-model="ageOfBirthYearTo"
                                                 :items="ages"
                                                 :value="ageOfBirthYearTo"
                                                 label="До"
                                         ></v-combobox>
                                     </v-col>

                                 </v-row>

                             </v-container>
                         </template>
                     </template>


                 </v-card>
             </v-col>


             &lt;!&ndash;Третья колонка&ndash;&gt;

             <v-col

                     md="4"
                     col="4"
             >
                 <v-card
                         class="pa-2"
                         outlined
                         tile
                         :disabled="!yearSelector.includes(1)"
                 >
                     <label>Данные на год</label>
                     <v-chip-group
                             column
                             active-class="primary&#45;&#45;text"
                             v-model="calendarYearSelector"
                     >
                         <v-chip>
                             За определенный период
                         </v-chip>
                         <v-chip>
                             За определенные года
                         </v-chip>
                     </v-chip-group>


                     <v-combobox v-if="calendarYearSelector === 1"
                                 v-model="calendarYearData"
                                 :items="dates"
                                 label="Выберите года"
                                 multiple
                                 chips
                     ></v-combobox>

                     <template v-if="calendarYearSelector === 0">
                         <v-container class="d-flex align-content-center flex-wrap">
                             <v-row>
                                 <v-col
                                         md="6"

                                 >
                                     <v-combobox
                                             v-model="calendarYearFrom"
                                             :items="dates"
                                             label="От"
                                     ></v-combobox>
                                 </v-col>
                                 <v-col md="6"
                                 >
                                     <v-combobox
                                             v-model="calendarYearTo"
                                             :items="dates"
                                             label="До"
                                     ></v-combobox>
                                 </v-col>
                             </v-row>
                         </v-container>
                     </template>

                     <template v-if="(calendarYearFrom >= minYear && calendarYearTo <= maxYear) || calendarYearData.length > 0">


                         <label justify-center align-center>
                             Возраст
                         </label>

                         <v-chip-group
                                 column
                                 active-class="primary&#45;&#45;text"
                                 v-model="ageForCalendarYearSelector"
                         >
                             <v-chip>
                                 В диапозоне
                             </v-chip>
                             <v-chip>
                                 Определенный возраст
                             </v-chip>
                         </v-chip-group>

                         <v-combobox v-if="ageForCalendarYearSelector === 1"
                                     v-model="agesOfCalendarYear"
                                     :items="ages"
                                     label="Выберите возраста"
                                     multiple
                                     chips
                         ></v-combobox>

                         <template v-if="ageForCalendarYearSelector === 0">
                             <v-container class="d-flex align-content-center flex-wrap">
                                 <v-row>
                                     <v-col
                                             md="6"

                                     >
                                         <v-combobox
                                                 v-model="ageOfCalendarYearFrom"
                                                 :items="ages"
                                                 label="От"
                                         ></v-combobox>
                                     </v-col>
                                     <v-col md="6"
                                     >
                                         <v-combobox
                                                 v-model="ageOfCalendarYearTo"
                                                 :items="ages"
                                                 label="До"
                                         ></v-combobox>
                                     </v-col>

                                 </v-row>

                             </v-container>
                         </template>
                     </template>

                 </v-card>
             </v-col>
         </v-row>



         <v-row>
             <v-col
                     cols="12"
                     md="8"
             >
                 <v-card
                         class="pa-2"
                         outlined
                         tile
                 >
                     .col-12 .col-md-8
                 </v-card>
             </v-col>
             <v-col
                     cols="6"
                     md="4"
             >
                 <v-card
                         class="pa-2"
                         outlined
                         tile
                 >
                     .col-6 .col-md-4
                 </v-card>
             </v-col>
         </v-row>

         &lt;!&ndash; Columns start at 50% wide on mobile and bump up to 33.3% wide on desktop &ndash;&gt;
         <v-row>
             <v-col
                     v-for="n in 3"
                     :key="n"
                     cols="6"
                     md="4"
             >
                 <v-card
                         class="pa-2"
                         outlined
                         tile
                 >
                     .col-6 .col-md-4
                 </v-card>
             </v-col>
         </v-row>

         &lt;!&ndash; Columns are always 50% wide, on mobile and desktop &ndash;&gt;
         <v-row>
             <v-col
                     v-for="n in 2"
                     :key="n"
                     cols="6"
             >
                 <v-card
                         class="pa-2"
                         outlined
                         tile
                 >
                     .col-6
                 </v-card>
             </v-col>
         </v-row>
     </v-container>-->
    <div>
        <v-container fluid fill-height>
            <v-row>
                <v-col>
                    <v-card>
                        <v-list class="ma-2">
                            <v-list-item>
                                <v-combobox class="ma-2"
                                            v-model="currentLocation"
                                            :items="locations"
                                            item-text="name"
                                            item-value="id"
                                            label="Страна, субъект, город"
                                />
                            </v-list-item>
                            <v-subheader>
                                Тип данных
                            </v-subheader>
                            <v-list-item>
                                <v-chip-group
                                        column
                                        active-class="primary--text"
                                        v-model="dataTypes"
                                        multiple
                                >
                                    <v-chip value="total">
                                        Общий
                                    </v-chip>
                                    <v-chip value="male">
                                        Мужчины
                                    </v-chip>
                                    <v-chip value="female">
                                        Женщины
                                    </v-chip>
                                    <v-chip value="villager">
                                        Сельские
                                    </v-chip>
                                    <v-chip value="citizen">
                                        Городские
                                    </v-chip>

                                </v-chip-group >
                            </v-list-item>
                            <v-divider/>
                            <v-subheader>
                                Данные
                            </v-subheader>
                            <v-list-item>
                                <v-chip-group
                                        column
                                        active-class="primary--text"
                                        v-model="yearMode"
                                >
                                    <v-chip :disabled="dataTypes.length === 0">
                                        Дата рождения
                                    </v-chip>
                                    <v-chip :disabled="dataTypes.length === 0">
                                        Календарный год
                                    </v-chip>
                                </v-chip-group >
                            </v-list-item>
                            <v-divider/>
                            <v-subheader>Года</v-subheader>
                            <v-list-item>
                                <v-chip-group
                                        column
                                        active-class="primary--text"
                                        v-model="yearSelector"

                                >
                                    <v-chip :disabled = "yearMode == null">
                                        За период
                                    </v-chip>
                                    <v-chip :disabled = "yearMode == null">
                                        За определенные года
                                    </v-chip>
                                </v-chip-group>
                            </v-list-item>
                            <v-list-item  v-if="yearSelector === 1">
                                <v-combobox class="ma-2"
                                        v-model="yearsData"
                                        :items="dates"
                                        label="Выберите года"
                                        multiple
                                        chips
                                ></v-combobox>
                            </v-list-item>
                            <v-list-item v-else-if="yearSelector === 0">
                                <v-combobox class="ma-2"
                                        v-model="yearFrom"
                                        :items="dates"
                                        label="От"
                                />
                                <v-combobox class="ma-2"
                                        v-model="yearTo"
                                        :items="dates"
                                        label="До"
                                />
                            </v-list-item>
                            <v-divider/>
                            <v-subheader>
                                Возраст
                            </v-subheader>
                            <v-list-item>
                                <v-chip-group
                                        column
                                        active-class="primary--text"
                                        v-model="ageSelector"
                                >
                                    <v-chip :disabled="((yearFrom < minYear || yearTo > maxYear) && yearSelector === 0)
                                    || (yearsData.length === 0 && yearSelector === 1)
                                    || yearSelector === null
                                    || yearMode === null">
                                        В диапозоне
                                    </v-chip>
                                    <v-chip :disabled="((yearFrom < minYear || yearTo > maxYear) && yearSelector === 0)
                                    || (yearsData.length === 0 && yearSelector === 1)
                                    || yearSelector === null
                                    || yearMode === null">
                                        Определенный возраст
                                    </v-chip>
                                </v-chip-group>
                            </v-list-item>
                            <v-list-item v-if="ageSelector === 1">
                                <v-combobox class="ma-2"
                                        v-model="agesData"
                                        :items="ages"
                                        label="Выберите возраста"
                                        multiple
                                        chips
                                ></v-combobox>
                            </v-list-item>
                            <v-list-item v-else-if="ageSelector === 0">
                                <v-combobox class="ma-2"
                                        v-model="ageFrom"
                                        :items="ages"
                                        :value="ageFrom"
                                        label="От"
                                />
                                <v-combobox class="ma-2"
                                        v-model="ageTo"
                                        :items="ages"
                                        :value="ageTo"
                                        label="До"
                                />
                            </v-list-item>
                            <v-list-item>
                                <v-list-item-action>
                                    <v-btn @click="loadData" :disabled="(ageSelector === 0 && (ageFrom < 0 || ageTo > maxAge))
                                     || (ageSelector === 1 && (agesData.length === 0))
                                      || ageSelector == null" >Показать</v-btn>
                                </v-list-item-action>
                            </v-list-item>
                        </v-list>
                    </v-card>
                </v-col>
                <v-col>
                    <v-card>
                        <vue-funnel-graph :width="width" :height="height" :labels="labels"
                                          :values="values" :colors="colors" :sub-labels="subLabels" :direction="direction"
                                          :gradient-direction="gradientDirection"
                                          :animated="true" :display-percentage="true"
                        ></vue-funnel-graph>
                    </v-card>



                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<script>

    import deathTableAPI from '../api/DeathTableApi.js'
    //import LineChart from 'components/LineChart.js'
    import { VueFunnelGraph } from 'vue-funnel-graph-js'
    import DeathTableApi from "../api/DeathTableApi";

    export default {
        name: "StatisticPage",
        components: {
            VueFunnelGraph
        },
        data() {
            return {

                drawer: null,
                dataTypes: [],

                locations: [],
                currentLocation: null,

                yearMode: null,

                yearSelector: null,

                yearFrom: null,
                yearTo: null,
                yearsData: [],

                ageSelector: null,

                ageFrom: null,
                ageTo: null,
                agesData:[],

                dates: [],
                minYear: 0,
                maxYear: 3000,
                ages:[],
                maxAge: 110,

                labels: ["1", "2", "3"],
                subLabels: ['Male', 'Female'],
                values: [
                    [1000, 1000],
                    [500, 500],
                    [1,1]
                ],
                colors: [
                    ['#FFB178', '#FF3C8E'], // color set for "Impressions" segment
                    ['#A0BBFF', '#EC77FF'], // color set for "Add To Cart" segment
                    ['#A0F9FF', '#7795FF']  // color set for "Buy" segment
                ],
                direction: 'vertical',
                gradientDirection: 'horizontal',
                height: 300,
                width: 800,

                datacollection: null,
                loaded: false,
                chartOptions: {
                    responsive: true,
                    title: {
                        display: true,
                        text: 'Chart.js Line Chart'
                    },
                    tooltips: {
                        mode: 'vuex-store.js',
                        intersect: false,
                    },
                    hover: {
                        mode: 'nearest',
                        intersect: true
                    },
                    scales: {
                        xAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: 'Month'
                            }
                        }],
                        yAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: 'Value'
                            }
                        }]
                    }
                }
            }
        },
        created(){

            this.fillDates()
            this.fillAges()
            this.fillLocations()
        },
        computed: {
            datesComputed() {
                return this.dates
            },
            agesComputed(){
                return this.ages
            },
            locationsComputed(){
              return this.locations
            }
        },
        methods: {


            async fillDates(){

                const result = await deathTableAPI.getAllBirthYears();

                this.dates = result.data

                this.minYear = this.dates[0]
                this.maxYear = this.dates[this.dates.length-1]

                this.yearFrom = this.minYear
                this.yearTo = this.maxYear
            },

            async fillAges(){

                const result = await deathTableAPI.getAllAges();

                this.ages = result.data

                this.maxAge = this.ages[this.ages.length-1]

                this.ageFrom = 0
                this.ageTo = this.maxAge


            },

            async fillLocations(){
              const  result = await deathTableAPI.getAllLocations();

              this.locations = result.data

              this.currentLocation = this.locations[0]

            },

            async loadData(){
                try{

                    const savedThis = this

                    let params = {

                        location: savedThis.currentLocation,

                        maleData: savedThis.dataTypes.includes("male"),
                        femaleData: savedThis.dataTypes.includes("female"),
                        totalData: savedThis.dataTypes.includes("total"),
                        cityDwellersData: savedThis.dataTypes.includes("citizen"),
                        villagersData: savedThis.dataTypes.includes("villager"),

                        yearMode: savedThis.yearMode === 0 ? "birth" : "year",


                        yearSelector: savedThis.yearSelector == 0 ? "range" : "array",
                        yearFrom: savedThis.yearFrom,
                        yearTo:  savedThis.yearTo,
                        years: savedThis.yearsData,

                        ageSelector: savedThis.ageSelector === 0 ? "range" : "array",
                        ageFrom: savedThis.ageFrom,
                        ageTo: savedThis.ageTo,
                        ages: savedThis.agesData,
                    }

                    const result = await DeathTableApi.getDeathTable(params)
                    console.log(result.data)







                } catch (e) {

                    console.log(e)
                }
            },

            async test(){
                try {
                    /*const result = await deathTableAPI.getDeathTable({year: 2000,
                        ageFrom: 18, ageTo: 27})

                    const data = result.data

                    console.log(data)*/

                    /*let maleData = {
                        pointBackgroundColor: "#FFFFFF",
                        pointBorderColor: "#DDDDDD",
                        backgroundColor: '#FFA500',
                        label: 'Мужчины',
                        data: []
                    }
                    let femaleData = {
                        pointBackgroundColor: "#FFFFFF",
                        pointBorderColor: "#DDDDDD",
                        backgroundColor: '#49FF00',
                        label: 'Женщины',
                        data: []
                    }

                    /!*data.deathNotesMale.forEach(function(item, i, arr) {
                        labels.push(item.x)
                        maleData.data.push(item.l)
                    })*!/
                    let labels = []

                    if(data.deathNotesMale.length === data.deathNotesFemale.length){

                        for (let i = data.deathNotesMale.length - 1; i >= 0; i--) {
                            labels.push(data.deathNotesMale[i].x)
                            maleData.data.push(data.deathNotesMale[i].l)
                            femaleData.data.push(data.deathNotesFemale[i].l)
                        }


                    } else {
                        for (let i = data.deathNotesMale.length - 1; i >= 0; i--) {
                            labels.push(data.deathNotesMale[i].x)
                            maleData.data.push(data.deathNotesMale[i].l)
                        }
                        for (let i = data.deathNotesFemale.length - 1; i >= 0; i--) {
                            labels.push(data.deathNotesFemale[i].x)
                            femaleData.data.push(data.deathNotesFemale[i].l)
                        }

                    }

                    this.datacollection = {
                        labels,
                        datasets: [
                            maleData,
                            femaleData
                        ]
                    }

                    this.loaded = true*/

                } catch (e) {
                    console.log(JSON.stringify(e))
                }

            },

        },
        watch: {

        }
    }
</script>

<style scoped>

</style>