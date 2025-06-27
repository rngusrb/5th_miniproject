<template>
    <div>
        <Number
            label="AuthorId"
            v-model="value.authorId"
            :editMode="editMode"
        />
        <String
            label="AuthorPw"
            v-model="value.authorPw"
            :editMode="editMode"
        />
        <String
            label="AuthorName"
            v-model="value.authorName"
            :editMode="editMode"
        />
        <Date
            label="CreateDate"
            v-model="value.createDate"
            :editMode="editMode"
        />
        <String
            label="AuthorInfo"
            v-model="value.authorInfo"
            :editMode="editMode"
        />
        <String
            label="AuthorPortfolio"
            v-model="value.authorPortfolio"
            :editMode="editMode"
        />
        <Boolean
            label="IsActive"
            v-model="value.isActive"
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
    name: 'Author',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "authors",
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
