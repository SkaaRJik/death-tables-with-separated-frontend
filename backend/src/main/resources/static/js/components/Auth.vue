<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">

    <v-dialog
              v-model="dialog"
              persistent
              width="600"
    >
        <template v-slot:activator="{ on }">
            <v-btn icon @click="dialog='true'">
                <v-icon>account_circle</v-icon>
            </v-btn>
        </template>

        <v-tabs
                v-model="model"
                centered
                slider-color="yellow"
        >
            <v-tab
                    href="#sign-in"
            >
                Вход
            </v-tab>
            <v-tab
                    href="#sign-up"
            >
                Регистрация
            </v-tab>
        </v-tabs>
        <v-alert
                v-model="showSuccessInfo"
                type="success"
                transition="scale-transition"
                dense
                border="bottom"

                elevation="2"
                class="mb-0"
        >
            {{successInfo}}
        </v-alert>
        <v-alert
                v-model="showAlert"
                type="error"
                transition="scale-transition"
                dense
                border="bottom"

                elevation="2"
                class="mb-0"
        >
            {{alertInfo}}
        </v-alert>
        <v-tabs-items v-model="model">
            <v-tab-item
                    value="sign-in"
            >

                <v-card>
                    <v-card-text>
                        <v-container grid-list-md>
                            <v-layout wrap>

                                <v-flex xs12>
                                    <v-text-field
                                            label="Email"
                                            type="email"
                                            v-model="signInDetails.username"
                                            v-on:focusout="isEmailRegistrated"
                                            v-on:input="clearSignInError"
                                            required></v-text-field>
                                </v-flex>
                                <v-flex xs12>
                                    <v-text-field label="Пароль"
                                                  type="password"
                                                  v-on:input="checkSignInForm"
                                                  v-model="signInDetails.password"
                                                  required>

                                    </v-text-field>
                                </v-flex>
                            </v-layout>
                        </v-container>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" text @click="dialog=!dialog">Отмена</v-btn>
                        <v-btn color="blue darken-1" text @click="signIn" :disabled="disableSignInForm" >Войти</v-btn>
                    </v-card-actions>
                </v-card>
            </v-tab-item>
            <v-tab-item
                    value="sign-up"
            >
                <v-card>
                    <v-card-text>
                        <v-container grid-list-md>
                            <v-layout wrap>
                                <v-flex xs12>
                                    <v-text-field label="Email*"
                                                  v-model="userDetails.email"
                                                  :hint="emailError"
                                                  @focusout="checkSignUpEmail"
                                                  persistent-hint
                                                  required>

                                    </v-text-field>
                                </v-flex>
                                <v-flex xs12>
                                    <v-text-field label="Пароль*" type="password" v-model="userDetails.password" required></v-text-field>
                                </v-flex>
                                <v-flex xs12>
                                    <v-text-field label="Повторите пароль*"
                                                  type="password"
                                                  v-model="repeatPassword"
                                                  :hint="passwordError"
                                                  persistent-hint
                                                  required>

                                    </v-text-field>
                                </v-flex>
                                <v-flex xs12 sm6 md4>
                                    <v-text-field label="Имя*" required v-model="userDetails.firstName"></v-text-field>
                                </v-flex>
                                <v-flex xs12 sm6 md4>
                                    <v-text-field label="Фамилия*" required v-model="userDetails.lastName"></v-text-field>
                                </v-flex>

                            </v-layout>
                        </v-container>
                        <small>*indicates required field</small>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" text @click="dialog=!dialog">Отмена</v-btn>
                        <v-btn color="blue darken-1" text @click="signUp" :disabled="disableRegistrationForm">Зарегистрироваться</v-btn>
                    </v-card-actions>
                </v-card>
            </v-tab-item>
        </v-tabs-items>
    </v-dialog>

</template>


<!--<v-dialog v-model="dialog" persistent max-width="600px">


</v-dialog>-->



<script>
    import {mapActions, mapState} from 'vuex'
    import authApi from 'api/auth.js'
    import browserDetector from 'libraries/device'


    export default {

        data() {
            return {
                reg: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/,
                dialog: false,
                model: 'sign-in',
                emailError: null,
                passwordError: null,

                passwordMinSize: 3,

                disableSignInForm: true,
                isSignInPasswordValid: false,
                isSignInEmailValid: false,


                disableRegistrationForm: true,
                isSignUpPasswordValid: false,
                isSignUpEmailValid: false,
                isSignUpFirstnameAndLastnameValid: false,





                alertInfo: null,
                showAlert: false,



                successInfo: null,
                showSuccessInfo: false,
                signInDetails:{
                    username: null,
                    password: null,
                    deviceInfo: {
                        browser: null,
                        os: null
                    }
                },
                userDetails:{
                    username: null,
                    email: null,
                    password: null,
                    firstName: null,
                    lastName: null,
                },
                repeatPassword: null,
            }
        },
        computed: {
            ...mapState({
                profile: 'profile'
            })
        },
        methods: {
            ...mapActions(['setProfile']),
           async signIn(){
                try{
                    var user = browserDetector.parse(navigator.userAgent);
                    this.signInDetails.deviceInfo.browser = user.browser.family + ' v.' + user.browser.version
                    this.signInDetails.deviceInfo.os = user.os.name

                    const result = await authApi.signIn(this.signInDetails)
                    const data = await result.json()
                    this.showAlert = false
                    this.$store.dispatch('setProfile', data)
                    await authApi.runTokenWatcher()
                    this.dialog=false
                } catch (error) {
                    console.log(error)
                    this.alertInfo = error.bodyText
                    this.showAlert = true
                }
            },
            async signUp() {
                try {
                    this.userDetails.username = this.userDetails.email
                    const res = await authApi.signUp(this.userDetails)
                    this.showSuccessInfo = true
                    this.successInfo = res.bodyText
                    this.model = 'sign-in'
                    this.signInDetails.username = this.userDetails.email
                } catch (error) {
                    console.log(error)
                    this.alertInfo = error.bodyText
                    this.showAlert = true
                }
            },
            isEmailValid( email ) {

                return (email == "") ? false : (this.reg.test(email));

            },
            async clearSignInError(){

                this.showAlert = false
            },
            async isEmailRegistrated() {
                if(this.isEmailValid(this.signInDetails.username) )
                try {
                    this.showAlert = false
                    const result = await authApi.isEmailRegistrated(this.signInDetails.username)
                    const data = await result.json()
                    if(data === false) {
                        this.alertInfo = 'Пользователя с таким email не существует'
                        this.showAlert = true
                    } else {
                        this.showAlert = false
                    }
                } catch (e) {
                    console.log(e)
                }
                else {
                    this.alertInfo = 'email не валиден'
                    this.showAlert = true
                }

                this.isSignInEmailValid = this.showAlert
                this.checkSignInForm()

            },
            checkSignInForm(){

                this.isSignInPasswordValid = (this.signInDetails.password != null) ? (this.signInDetails.password.length >= this.passwordMinSize) : false

                this.disableSignInForm = this.isSignInEmailValid || !this.isSignInPasswordValid
            },
            checkSignUpEmail(){
              this.isSignUpEmailValid = this.isEmailValid(this.userDetails.email)
               this.checkSignUpForm()
            },
            checkSignUpForm(){
                this.disableRegistrationForm = !this.isSignUpPasswordValid || !this.isSignUpEmailValid || !this.isSignUpFirstnameAndLastnameValid
            }

        },
        watch: {

            repeatPassword: function () {
                if(this.repeatPassword !== this.userDetails.password){
                    this.passwordError = 'Пароли не совпадают!'
                } else {
                    this.passwordError = null
                }
                this.isSignUpPasswordValid = this.passwordError != null
            },
            model: function () {
                this.showAlert = false

            },


            userDetails : {
                handler(newVal, oldVal) {
                    if(this.userDetails.firstName && this.userDetails.lastName){
                        if(this.userDetails.firstName.length >= 4 && this.userDetails.lastName.length >= 4){
                            this.isSignUpFirstnameAndLastnameValid = true
                        }
                        else {
                            this.isSignUpFirstnameAndLastnameValid = false
                        }
                    } else {
                        this.isSignUpFirstnameAndLastnameValid = false
                    }

                    if(this.userDetails.password) {
                        if (this.userDetails.password.length < this.passwordMinSize) this.isSignUpPasswordValid = false
                        else {
                            if(this.repeatPassword !== this.userDetails.password){
                                this.isSignUpPasswordValid = false
                            }
                            else {
                                this.isSignUpPasswordValid = true
                            }
                        }
                    } else {
                        this.isSignUpPasswordValid = false
                    }


                    this.checkSignUpForm()
                },
                deep: true
            },

        }

    }
</script>

<style scoped>

</style>