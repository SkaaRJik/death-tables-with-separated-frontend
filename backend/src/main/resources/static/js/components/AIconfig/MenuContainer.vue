<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">

            <v-list class="pa-1">

                <v-list-group v-for="(item, i) in items"
                              :key="i"
                              @click=""
                >
                    <template v-slot:activator>
                        <v-list-tile>
                            <v-list-tile-title>{{item.header}}</v-list-tile-title>
                        </v-list-tile>
                    </template>
                    <template v-if="item.header === 'Настройки активационных функций:'">
                        <v-list-group v-for="(layer, j) in item.layers"
                                      :key="j"
                                      @click=""
                                      no-action
                                      sub-group
                        >
                            <template v-slot:activator>
                                <v-list-tile>
                                    <v-list-tile-title>{{layer.head}}</v-list-tile-title>
                                </v-list-tile>
                            </template>


                            <v-list-tile v-for="(field, k) in layer.fields"
                                         :key="k"
                                         @click="">


                                <v-switch
                                        v-model="field.model"
                                        :label=field.placeholderText
                                        color="red"
                                        :true-value=field.selected
                                        false-value=""
                                ></v-switch>

                            </v-list-tile>

                        </v-list-group>
                    </template>
                    <template v-else>
                        <v-list-tile class="my-4" v-for="(field, j) in item.fields"
                                     :key="j"
                                     @click="">


                            <template >

                                <template v-if="field.type === 'text'">


                                    <v-layout  v-if="field.placeholderText === 'Семя генератора случ. чисел'" align-space-between justify-start row fill-heigh class="my-4"t>
                                        <v-text-field
                                                v-model="field.model"
                                                :label=field.placeholderText
                                                v-show="!field.show"
                                                :disabled="field.disabled"
                                                outline
                                                clearable
                                        />

                                        <v-btn v-if="field.placeholderText === 'Семя генератора случ. чисел'" flat icon @click="generateNewSeed">
                                            <v-icon>
                                                refresh
                                            </v-icon>
                                        </v-btn>

                                    </v-layout>
                                    <template v-else>
                                        <v-text-field
                                                v-model="field.model"
                                                :label=field.placeholderText
                                                v-show="!field.show"
                                                :disabled="field.disabled"
                                                outline
                                                clearable
                                        />
                                    </template>
                                </template>
                                <template v-else-if="field.type === 'toggle'" >
                                    <v-switch
                                            v-model="field.model"
                                            :label=field.placeholderText
                                            :true-value=true
                                            :false-value=false
                                            v-show="!field.show"
                                            color="red"
                                            hide-details
                                    ></v-switch>
                                </template>

                            </template>




                        </v-list-tile>
                    </template>
                </v-list-group>




            </v-list>





</template>

<script>
    export default {
        props:['config'],
        data() {
            return {
                defaultConfig: null,


                navigatorItems: [
                    {
                        header: 'Параметры генетического алгоритма',
                        fields: [
                            {
                                placeholderText: 'Семя генератора случ. чисел',
                                type: 'text',
                                model: '',
                            },
                            {
                                placeholderText: 'Вероятность мутации',
                                type: 'text',
                                model: '',
                            },
                            {
                                placeholderText: 'Вероятность мутации актив. функции',
                                type: 'text',
                                model: '',
                            },
                            {
                                placeholderText: 'Вероятность новой связи',
                                type: 'text',
                                model: '',
                            },
                            {
                                placeholderText: 'Вероятность нового нейрона',
                                type: 'text',
                                model: '',
                            },
                            {
                                placeholderText: 'Вероятность мутации байеса',
                                type: 'text',
                                model: '',
                            },
                            {
                                placeholderText: 'Вероятность включ/исключ. связи',
                                type: 'text',
                                model: '',
                            },
                            {
                                placeholderText: 'Вероятность изменения веса',
                                type: 'text',
                                model: '',
                            }]
                    },

                    {
                        header: 'Контроль эпох',
                        fields: [
                            {
                                placeholderText: 'Размер популяции',
                                type: 'text',
                                model: '',
                            },
                            {
                                placeholderText: 'Количество эпох',
                                type: 'text',
                                model: '',
                            },

                            {
                                placeholderText: 'Дополнительные аллели',
                                type: 'text',
                                model: '',
                            },
                            {
                                placeholderText: 'Всегда сохранять лучшего',
                                type: 'toggle',
                                model: Boolean
                            },
                            {
                                placeholderText: 'Включить прерывание',
                                type: 'toggle',
                                model: Boolean
                            },
                            {
                                placeholderText: 'Значение остановки',
                                type: 'text',
                                show: false,
                                model: '',

                            },
                            ]
                    },
                    {
                        header: 'Настройки активационных функций:',
                        layers: [
                            {
                                head: 'Входной слой',
                                fields: [
                                    {
                                        placeholderText: 'y=x',
                                        type: 'toggle',
                                        model: '',
                                        selected: 'org.neat4j.neat.nn.core.functions.LinearFunction',
                                        disabled: ''
                                    },
                                    {
                                        placeholderText: 'sin(x)',
                                        type: 'toggle',
                                        model: '',
                                        selected: 'org.neat4j.neat.nn.core.functions.SigmoidFunction',
                                        disabled: ''
                                    },
                                    {
                                        placeholderText: 'tanh(x)',
                                        type: 'toggle',
                                        model: '',
                                        selected: 'org.neat4j.neat.nn.core.functions.TanhFunction',
                                        disabled: ''
                                    }
                                ]
                            },
                            {
                                head: 'Скрытые слои',
                                fields: [
                                    {
                                        placeholderText: 'y=x',
                                        type: 'toggle',
                                        model: '',
                                        selected: 'org.neat4j.neat.nn.core.functions.LinearFunction',
                                        disabled: ''
                                    },
                                    {
                                        placeholderText: 'sin(x)',
                                        type: 'toggle',
                                        model: '',
                                        selected: 'org.neat4j.neat.nn.core.functions.SigmoidFunction',
                                        disabled: ''
                                    },
                                    {
                                        placeholderText: 'tanh(x)',
                                        type: 'toggle',
                                        model: '',
                                        selected: 'org.neat4j.neat.nn.core.functions.TanhFunction',
                                        disabled: ''
                                    }
                                ]
                            },
                            {
                                head: 'Выходной слой',
                                fields: [
                                    {
                                        placeholderText: 'y=x',
                                        type: 'toggle',
                                        model: '',
                                        selected: 'org.neat4j.neat.nn.core.functions.LinearFunction',
                                        disabled: ''
                                    },
                                    {
                                        placeholderText: 'sin(x)',
                                        type: 'toggle',
                                        model: '',
                                        selected: 'org.neat4j.neat.nn.core.functions.SigmoidFunction',
                                        disabled: ''
                                    },
                                    {
                                        placeholderText: 'tanh(x)',
                                        type: 'toggle',
                                        model: '',
                                        selected: 'org.neat4j.neat.nn.core.functions.TanhFunction',
                                        disabled: ''
                                    }
                                ]
                            }
                        ]

                    },
                    {
                        header: 'Настройки нейросети',
                        fields: [
                            {
                                placeholderText: 'Входов сети',
                                type: 'text',
                                disabled: true,
                                model: ''
                            },
                            {
                                placeholderText: 'Выходов сети',
                                type: 'text',
                                disabled: true,
                                model: ''
                            },
                            {
                                placeholderText: 'Макс. смещение весов',
                                type: 'text',
                                model: ''
                            },
                            {
                                placeholderText: 'Макс. смещение байеса',
                                type: 'text',
                                model: ''
                            },
                            {
                                placeholderText: 'Отбор фич',
                                type: 'toggle',
                                model: ''

                            },
                            {
                                placeholderText: 'Разрешить рекурентность',
                                type: 'toggle',
                                model: ''
                            },
                        ]
                    },
                    {
                        header: 'Настройки нишинга',
                        fields: [
                            {
                                placeholderText: 'Коэф-нт дополнительных генов (c1)',
                                type: 'text',
                                model: ''
                            },
                            {
                                placeholderText: 'Коэф-нт непересекающихся генов (c2)',
                                type: 'text',
                                model: ''
                            },
                            {
                                placeholderText: 'Коэф-нт средней разницы весов совпадающих генов',
                                type: 'text',
                                model: ''
                            },]
                    },
                    {
                        header: 'Контроль видообразования',
                        fields: [
                            {
                                placeholderText: 'Пороговое значение соответствия особей внутри вида',
                                type: 'text',
                                model: ''
                            },
                            {
                                placeholderText: 'Значение совместимости мутаций',
                                type: 'text',
                                model: ''
                            },
                            {
                                placeholderText: 'Макс. количество видов',
                                type: 'text',
                                model: ''
                            },
                            {
                                placeholderText: 'Пороговое значение выживания',
                                type: 'text',
                                model: ''
                            },
                            {
                                placeholderText: 'Макс. время жизни вида',
                                type: 'text',
                                model: ''
                            },
                            {
                                placeholderText: 'Порог, до которого особь считается молодой',
                                type: 'text',
                                model: ''
                            },
                            {
                                placeholderText: 'Штраф для старой особи',
                                type: 'text',
                                model: ''
                            },
                            {
                                placeholderText: 'Ускорение для молодой особи',
                                type: 'text',
                                model: ''
                            },
                            {
                                placeholderText: 'Максимальное значение приспособленности',
                                type: 'text',
                                model: ''
                            }]
                    },
                    {
                        header: 'Контроль',
                        fields: [
                            {
                                placeholderText: 'Включить контроль вымирания',
                                type: 'toggle',
                                model: ''
                            },
                            {
                                placeholderText: 'Оставить особей в живых (штук)',
                                type: 'text',
                                model: '',
                                show: false,
                            },
                            {
                                placeholderText: 'Время жизни',
                                type: 'text',
                                model: '',
                                show: false,
                            },
                        ]
                    },

                ],




            }
        },
        methods:{
            getDefaultConfig(){
                this.$resource("api/trainer/default-config").get().then(res => {
                    if(res.ok){
                        res.json().then(data => {
                            this.defaultConfig = data
                            console.log(this.defaultConfig)
                            this.navigatorItems[0].fields[0].model = this.defaultConfig.settings.GENERATOR_SEED
                            this.navigatorItems[0].fields[1].model = this.defaultConfig.settings.PROBABILITY_MUTATION
                            this.navigatorItems[0].fields[2].model = this.defaultConfig.settings.PROBABILITY_NEWACTIVATIONFUNCTION
                            this.navigatorItems[0].fields[3].model = this.defaultConfig.settings.PROBABILITY_ADDLINK
                            this.navigatorItems[0].fields[4].model = this.defaultConfig.settings.PROBABILITY_ADDNODE
                            this.navigatorItems[0].fields[5].model = this.defaultConfig.settings.PROBABILITY_MUTATEBIAS
                            this.navigatorItems[0].fields[6].model = this.defaultConfig.settings.PROBABILITY_TOGGLELINK
                            this.navigatorItems[0].fields[7].model = this.defaultConfig.settings.PROBABILITY_WEIGHT_REPLACED

                            this.navigatorItems[1].fields[0].model = this.defaultConfig.settings.POP_SIZE
                            this.navigatorItems[1].fields[1].model = this.defaultConfig.settings.NUMBER_EPOCHS
                            this.navigatorItems[1].fields[2].model = this.defaultConfig.settings.EXTRA_FEATURE_COUNT
                            this.navigatorItems[1].fields[3].model = this.defaultConfig.settings.KEEP_BEST_EVER
                            this.navigatorItems[1].fields[4].model = this.defaultConfig.settings.TERMINATION_VALUE_TOGGLE
                            this.navigatorItems[1].fields[5].model = this.defaultConfig.settings.TERMINATION_VALUE


                            let newArr = []
                            for(var i = 0; i < this.navigatorItems[2].layers.length; i++){

                                if(i === 0){
                                    newArr = this.defaultConfig.settings.INPUT_ACTIVATIONFUNCTIONS.split(';')
                                }
                                if(i === 1){
                                    newArr = this.defaultConfig.settings.HIDDEN_ACTIVATIONFUNCTIONS.split(';')
                                }
                                if(i === 2){
                                    newArr = this.defaultConfig.settings.OUTPUT_ACTIVATIONFUNCTIONS.split(';')
                                }
                                for(var j = 0 ; j < newArr.length ; j++){
                                    this.navigatorItems[2].layers[i].fields[j].model = newArr[j]
                                }
                            }





                            this.navigatorItems[3].fields[0].model = this.defaultConfig.settings.INPUT_NODES
                            this.navigatorItems[3].fields[1].model = this.defaultConfig.settings.OUTPUT_NODES
                            this.navigatorItems[3].fields[2].model = this.defaultConfig.settings.MAX_PERTURB
                            this.navigatorItems[3].fields[3].model = this.defaultConfig.settings.MAX_BIAS_PERTURB
                            this.navigatorItems[3].fields[4].model = this.defaultConfig.settings.FEATURE_SELECTION
                            this.navigatorItems[3].fields[5].model = this.defaultConfig.settings.RECURRENCY_ALLOWED

                            this.navigatorItems[4].fields[0].model = this.defaultConfig.settings.EXCESS_COEFFICIENT
                            this.navigatorItems[4].fields[1].model = this.defaultConfig.settings.DISJOINT_COEFFICIENT
                            this.navigatorItems[4].fields[2].model = this.defaultConfig.settings.WEIGHT_COEFFICIENT

                            this.navigatorItems[5].fields[0].model = this.defaultConfig.settings.COMPATABILITY_THRESHOLD
                            this.navigatorItems[5].fields[1].model = this.defaultConfig.settings.COMPATABILITY_CHANGE
                            this.navigatorItems[5].fields[2].model = this.defaultConfig.settings.SPECIE_COUNT
                            this.navigatorItems[5].fields[3].model = this.defaultConfig.settings.SURVIVAL_THRESHOLD
                            this.navigatorItems[5].fields[4].model = this.defaultConfig.settings.SPECIE_AGE_THRESHOLD
                            this.navigatorItems[5].fields[5].model = this.defaultConfig.settings.SPECIE_YOUTH_THRESHOLD
                            this.navigatorItems[5].fields[6].model = this.defaultConfig.settings.SPECIE_OLD_PENALTY
                            this.navigatorItems[5].fields[7].model = this.defaultConfig.settings.SPECIE_YOUTH_BOOST
                            this.navigatorItems[5].fields[8].model = this.defaultConfig.settings.SPECIE_FITNESS_MAX

                            this.navigatorItems[6].fields[0].model = this.defaultConfig.settings.ELE_EVENTS
                            this.navigatorItems[6].fields[1].model = this.defaultConfig.settings.ELE_SURVIVAL_COUNT
                            this.navigatorItems[6].fields[2].model = this.defaultConfig.settings.ELE_EVENT_TIME

                        })

                    }
                })
            },
            send(){

                this.defaultConfig.settings.GENERATOR_SEED = this.navigatorItems[0].fields[0].model;
                this.defaultConfig.settings.PROBABILITY_MUTATION = this.navigatorItems[0].fields[1].model;
                this.defaultConfig.settings.PROBABILITY_NEWACTIVATIONFUNCTION = this.navigatorItems[0].fields[2].model
                this.defaultConfig.settings.PROBABILITY_ADDLINK = this.navigatorItems[0].fields[3].model
                this.defaultConfig.settings.PROBABILITY_ADDNODE = this.navigatorItems[0].fields[4].model
                this.defaultConfig.settings.PROBABILITY_MUTATEBIAS = this.navigatorItems[0].fields[5].model
                this.defaultConfig.settings.PROBABILITY_TOGGLELINK = this.navigatorItems[0].fields[6].model
                this.defaultConfig.settings.PROBABILITY_WEIGHT_REPLACED = this.navigatorItems[0].fields[7].model

                this.defaultConfig.settings.POP_SIZE = this.navigatorItems[1].fields[0].model
                this.defaultConfig.settings.NUMBER_EPOCHS = this.navigatorItems[1].fields[1].model
                this.defaultConfig.settings.EXTRA_FEATURE_COUNT = this.navigatorItems[1].fields[2].model
                this.defaultConfig.settings.KEEP_BEST_EVER = this.navigatorItems[1].fields[3].model
                this.defaultConfig.settings.TERMINATION_VALUE_TOGGLE = this.navigatorItems[1].fields[4].model
                this.defaultConfig.settings.TERMINATION_VALUE = this.navigatorItems[1].fields[5].model

                this.defaultConfig.settings.INPUT_ACTIVATIONFUNCTIONS=this.navigatorItems[2].layers[0].fields[0].model+";"+this.navigatorItems[2].layers[0].fields[1].model+";"+this.navigatorItems[2].layers[0].fields[2].model
                this.defaultConfig.settings.OUTPUT_ACTIVATIONFUNCTIONS=this.navigatorItems[2].layers[2].fields[0].model+";"+this.navigatorItems[2].layers[2].fields[1].model+";"+this.navigatorItems[2].layers[2].fields[2].model
                this.defaultConfig.settings.HIDDEN_ACTIVATIONFUNCTIONS=this.navigatorItems[2].layers[1].fields[0].model+";"+this.navigatorItems[2].layers[1].fields[1].model+";"+this.navigatorItems[2].layers[1].fields[2].model

                this.defaultConfig.settings.INPUT_NODES = this.navigatorItems[3].fields[0].model
                this.defaultConfig.settings.OUTPUT_NODES = this.navigatorItems[3].fields[1].model
                this.defaultConfig.settings.MAX_PERTURB = this.navigatorItems[3].fields[2].model
                this.defaultConfig.settings.MAX_BIAS_PERTURB = this.navigatorItems[3].fields[3].model
                this.defaultConfig.settings.FEATURE_SELECTION = this.navigatorItems[3].fields[4].model
                this.defaultConfig.settings.RECURRENCY_ALLOWED = this.navigatorItems[3].fields[5].model


                this.defaultConfig.settings.EXCESS_COEFFICIENT = this.navigatorItems[4].fields[0].model
                this.defaultConfig.settings.DISJOINT_COEFFICIENT =this.navigatorItems[4].fields[1].model
                this.defaultConfig.settings.WEIGHT_COEFFICIENT =this.navigatorItems[4].fields[2].model

                this.defaultConfig.settings.COMPATABILITY_THRESHOLD = this.navigatorItems[5].fields[0].model
                this.defaultConfig.settings.COMPATABILITY_CHANGE = this.navigatorItems[5].fields[1].model
                this.defaultConfig.settings.SPECIE_COUNT = this.navigatorItems[5].fields[2].model
                this.defaultConfig.settings.SURVIVAL_THRESHOLD = this.navigatorItems[5].fields[3].model
                this.defaultConfig.settings.SPECIE_AGE_THRESHOLD = this.navigatorItems[5].fields[4].model
                this.defaultConfig.settings.SPECIE_YOUTH_THRESHOLD = this.navigatorItems[5].fields[5].model
                this.defaultConfig.settings.SPECIE_OLD_PENALTY = this.navigatorItems[5].fields[6].model
                this.defaultConfig.settings.SPECIE_YOUTH_BOOST = this.navigatorItems[5].fields[7].model
                this.defaultConfig.settings.SPECIE_FITNESS_MAX = this.navigatorItems[5].fields[8].model

                this.defaultConfig.settings.ELE_EVENTS = this.navigatorItems[6].fields[0].model
                this.defaultConfig.settings.ELE_SURVIVAL_COUNT = this.navigatorItems[6].fields[1].model
                this.defaultConfig.settings.ELE_EVENT_TIME = this.navigatorItems[6].fields[2].model


                console.log(this.defaultConfig)


                this.$resource("/trainer/save").save({},  this.defaultConfig).then(res => {
                    if(res.ok){
                        console.log("save ok!")
                    }
                })
            },
            generateNewSeed(){
                this.navigatorItems[0].fields[0].model = new Date().getTime()

            }
        },
        watch:{
            navigatorItems: {
                handler(newVal, oldVal){
                    this.navigatorItems[1].fields[5].show = !this.navigatorItems[1].fields[4].model
                    this.navigatorItems[6].fields[1].show = !this.navigatorItems[6].fields[0].model
                    this.navigatorItems[6].fields[2].show = !this.navigatorItems[6].fields[0].model

                },
                deep: true
            }
        },
        created() {
            this.getDefaultConfig();
        }
    }
</script>

<style>

</style>