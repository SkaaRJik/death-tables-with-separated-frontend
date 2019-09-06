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
    <v-container
            class="fill-height"
            fluid
    >
        <v-row
                align="center"
                justify="center"
        >
            <vue-funnel-graph :width="width" :height="height" :labels="labels"
                              :values="values" :colors="colors" :sub-labels="subLabels" :direction="direction"
                              :gradient-direction="gradientDirection"
                              :animated="true" :display-percentage="true"
            ></vue-funnel-graph>
        </v-row>

    </v-container>

</template>

<script>

    //import deathTableAPI from 'api/DeathTableApi.js'
    //import LineChart from 'components/LineChart.js'
    import { VueFunnelGraph } from 'vue-funnel-graph-js'

    export default {
        name: "StatisticPage",
        components: {
            VueFunnelGraph
        },
        data() {
            return {

                drawer: null,

                yearSelector: [],



                birthYearSelector: -1,

                birthYearFrom: null,
                birthYearTo: null,
                birthYearData: [],

                ageForBirthYearSelector: -1,

                ageOfBirthYearFrom: null,
                ageOfBirthYearTo: null,
                agesOfBirthYear:[],



                calendarYearSelector: -1,

                calendarYearFrom: null,
                calendarYearTo: null,
                calendarYearData: [],

                ageForCalendarYearSelector: -1,

                ageOfCalendarYearFrom: null,
                ageOfCalendarYearTo: null,
                agesOfCalendarYear:[],


                dates: [],
                minYear: 0,
                maxYear: 3000,
                ages:[],
                maxAge: 110,

                labels: ['0', '1', '2'],
                subLabels: ['Male', 'Female'],
                values: [
                    // with the given Labels and SubLabels here's what the values represent:
                    //
                    // Direct, Social, Ads
                    //    |      |     |
                    //    v      v     v
                        [97015,  98156], // Segments of "Impressions" from top to bottom
                        [97015,  98156], // Segments of "Impressions" from top to bottom
                        [98176, 99083], // Segments of "Add To Cart"
                        [100000, 100000], // Segments of "Buy"


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

            //this.fillDates()
            //this.fillAges()
        },
        computed: {
            datesComputed() {
                return this.dates
            },
            agesComputed(){
                return this.ages
            }
        },
        methods: {


            async fillDates(){

                //const result = await deathTableAPI.getAllBirthYears();

                this.dates = result.data

                this.minYear = this.dates[0]
                this.maxYear = this.dates[this.dates.length-1]

                this.birthYearFrom = this.minYear
                this.birthYearTo = this.maxYear

                this.calendarYearFrom = this.minYear
                this.calendarYearTo = this.maxYear

            },

            async fillAges(){

                const result = await deathTableAPI.getAllAges();

                this.ages = result.data

                this.maxAge = this.ages[this.ages.length-1]

                this.ageOfBirthYearFrom = 0
                this.ageOfBirthYearTo = this.maxAge

                this.ageOfCalendarYearFrom = 0
                this.ageOfCalendarYearTo = this.maxAge


            },


            async test(){
                try {
                    const result = await deathTableAPI.getDeathTable({year: 2000,
                        ageFrom: 18, ageTo: 27})

                    const data = result.data

                    console.log(data)

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
            yearSelector:  function () {
                console.log(this.yearSelector)
            },
        }
    }
</script>

<style scoped>

</style>