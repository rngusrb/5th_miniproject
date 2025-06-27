<template>
    <div>
        <Number
            label="UserId"
            v-model="value.userId"
            :editMode="editMode"
        />
        <Number
            label="Point"
            v-model="value.point"
            :editMode="editMode"
        />
        <Date
            label="ChangeDate"
            v-model="value.changeDate"
            :editMode="editMode"
        />
        <Date
            label="ExpireDate"
            v-model="value.expireDate"
            :editMode="editMode"
        />
        <Number
            label="ChangePoint"
            v-model="value.changePoint"
            :editMode="editMode"
        />
        <Number
            label="RemainPoint"
            v-model="value.remainPoint"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'Point',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "points",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
