<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <!--<v-app>
        <v-app-bar
        >
            &lt;!&ndash;<v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>&ndash;&gt;









        </v-app-bar>


        <menu-container></menu-container>
        </v-navigation-drawer>&ndash;&gt;
        <v-content>

        </v-content>

    </v-app>-->

    <v-app id="inspire">
        <v-navigation-drawer v-if="profile"
                             v-model="drawer"
                             absolute
                             right
                             temporary
        >

            <v-list-item>
                <v-list-item-avatar>
                    <v-avatar :tile="false">
                        <img v-bind:src="'data:image/jpeg;base64,'+profile.avatar" alt="avatar">
                    </v-avatar>
                </v-list-item-avatar>

                <v-list-item-content>
                    <v-list-item-title>{{profile.firstname}} {{profile.lastname}}</v-list-item-title>
                </v-list-item-content>
            </v-list-item>

            <v-divider></v-divider>


            <v-list>
                <v-list-item
                        v-for="item in navigatorItems"
                        :key="item.name"
                        link
                        :href="item.link"
                >
                    <v-list-item-icon>
                        <v-icon>{{ item.logo }}</v-icon>
                    </v-list-item-icon>

                    <v-list-item-content>
                        <v-list-item-title>{{ item.name }}</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>

        <v-app-bar
                app
                clipped-left
        >
            <v-toolbar-title>Таблицы смертности</v-toolbar-title>

            <v-btn
                    v-for="item in toolbarItems"
                    :key="item.name"
                    link
                    :href="item.link"
                    class="ma-2" tile outlined >
                <v-icon left>{{item.logo}}</v-icon> {{item.name}}
            </v-btn>


            <v-spacer></v-spacer>

            <auth v-if="!profile"></auth>

            <v-avatar v-else :tile="false" @click.stop="drawer = !drawer">
                <img :src="'data:image/jpeg;base64,'+profile.avatar" alt="avatar">
            </v-avatar>

        </v-app-bar>

        <v-content>


           <!-- <v-container
                    fluid
                    fill-height
            >
                <v-layout
                        align-center
                        justify-center
                >
                    <v-flex shrink>
                        <v-tooltip right>
                            <template v-slot:activator="{ on }">
                                <v-btn
                                        :href="source"
                                        icon
                                        large
                                        target="_blank"
                                        v-on="on"
                                >
                                    <v-icon large>mdi-code-tags</v-icon>
                                </v-btn>
                            </template>
                            <span>Source</span>
                        </v-tooltip>
                        <v-tooltip right>
                            <template v-slot:activator="{ on }">
                                <v-btn
                                        icon
                                        large
                                        href="https://codepen.io/johnjleider/pen/bXNzZL"
                                        target="_blank"
                                        v-on="on"
                                >
                                    <v-icon large>mdi-codepen</v-icon>
                                </v-btn>
                            </template>
                            <span>Codepen</span>
                        </v-tooltip>
                    </v-flex>
                </v-layout>
            </v-container>-->
            <router-view></router-view>
        </v-content>

        <v-footer app>
            <span>&copy; 2019</span>
        </v-footer>
    </v-app>


</template>

<script>
    import MenuContainer from 'components/AIconfig/MenuContainer.vue'
    import Auth from "components/Auth.vue";





    import {mapActions, mapState} from 'vuex'


    export default {
        props: {
            source: String,
        },
        components: {
            Auth,
            MenuContainer,

        },
        data() {
            return {
                drawer: null,

                commonItems:[
                    {
                        logo: "bar_chart",
                        name: "Статистика",
                        link:  '/statistic',
                    }
                ],

                toolbarItems:[],

                navigatorItems:[
                    {
                        logo: "assignment_ind",
                        name: "Профиль",
                        link: "/",
                    },
                ],


            }
        },

        created(){
            for (let item of this.commonItems){
                this.navigatorItems.push(item)
                this.toolbarItems.push(item)

            }


        },
        computed: {
            ...mapState(['profile']),
            commonItemsComputed() {
                return this.commonItems
            },
            toolbarItemsComputed() {
                return this.toolbarItems
            },
            navigatorItemsComputed() {
                return this.navigatorItems
            },
        },

        methods: {
            ...mapActions(['showHideDialog']),

        },


    }
</script>

<style>

</style>