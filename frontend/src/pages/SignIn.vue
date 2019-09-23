<template>
  <v-container fluid width="600">
    <v-row >
        <v-alert error dismissible v-model="alert">
          {{ error }}
        </v-alert>
      <v-col cols="12">
        <v-row
                align="center"
                justify="center"

        >
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
                  v-model="showErrorSignIn"
                  type="error"
                  transition="scale-transition"
                  dense
                  border="bottom"

                  elevation="2"
                  class="mb-0"
          >
            {{signInError}}
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
                                v-model="signInDetails.email"
                                v-on:focusout="checkEmail"
                                required></v-text-field>
                      </v-flex>
                      <v-flex xs12>
                        <v-text-field label="Пароль"
                                      type="password"
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
                  <v-btn color="blue darken-1" text @click="userSignIn">Войти</v-btn>
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
                  <v-btn color="blue darken-1" text @click="userSignUp">Зарегистрироваться</v-btn>
                </v-card-actions>
              </v-card>
            </v-tab-item>
          </v-tabs-items>
        </v-row>
      </v-col>
    </v-row>
  </v-container>

  <!--<v-layout column>
    <v-flex xs12 class="text-xs-center" mt-5>
      <h3>Sign In</h3>
    </v-flex>
    <v-flex xs12 sm6 offset-sm3 mt-3>
      <form @submit.prevent="userSignIn">
        <v-layout column>
          <v-flex>
            <v-alert error dismissible v-model="alert">
              {{ error }}
            </v-alert>
          </v-flex>
          <v-flex>
            <v-text-field
              name="username"
              label="Username"
              id="username"
              type="text"
              v-model="username"
              required></v-text-field>
          </v-flex>
          <v-flex>
            <v-text-field
              name="password"
              label="Password"
              id="password"
              type="password"
              v-model="password"
              required></v-text-field>
          </v-flex>
          <v-flex class="text-xs-center" mt-5>
            <v-btn type="submit" :disabled="loading" dark color="teal lighten-1" autofocus>Sign In</v-btn>
          </v-flex>
        </v-layout>
      </form>
    </v-flex>
  </v-layout>-->
</template>

<script>

  import {mapActions, mapState} from 'vuex'
  import AuthApi from "../api/AuthApi";

  export default {
    data () {
      return {

        alert: false,

        reg: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/,
        dialog: false,
        model: 'sign-in',
        emailError: null,
        passwordError: null,
        signInError: null,
        showErrorSignIn: false,

        signInDetails:{
          email: null,
          password: null,
        },
        userDetails:{
          email: null,
          password: null,
          firstName: null,
          lastName: null,
        },
        repeatPassword: null,

      }
    },
    computed: {

      error () {
        return this.$store.getters.getError
      },
      loading () {
        return this.$store.getters.getLoading
      }

    },
    watch: {
      error (value) {
        if (value) {
          this.alert = true
        }
      },
      alert (value) {
        if (!value) {
          this.$store.dispatch('setError', false)
        }
      }
    },
    methods: {
      async userSignIn () {
        this.$store.dispatch('userSignIn', this.signInDetails)
      },

      async userSignUp () {
        this.$store.dispatch('userSignUp', this.userDetails)
      },

      isEmailValid( email ) {

        return (email == "") ? false : (this.reg.test(email));

      },
      async checkEmail() {
        if(this.isEmailValid(this.signInDetails.email) )
          try {
            this.showErrorSignIn = false
            const result = await AuthApi.isEmailRegistrated(this.signInDetails.email)
            const data = await result.data
            if(data == false) {
              this.signInError = 'Пользователя с таким email не существует'
              this.showErrorSignIn = true
            } else {
              this.showErrorSignIn = false
            }
          } catch (e) {
            console.log(e)
          }
        else {
          this.signInError = 'email не валиден'
          this.showErrorSignIn = true
        }
      },

    }
  }
</script>


<style scoped>

</style>
