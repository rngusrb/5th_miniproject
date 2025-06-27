<template>
    <div>
        <Number
            label="BookId"
            v-model="value.bookId"
            :editMode="editMode"
        />
        <Number
            label="AuthorId"
            v-model="value.authorId"
            :editMode="editMode"
        />
        <String
            label="BookTitle"
            v-model="value.bookTitle"
            :editMode="editMode"
        />
        <String
            label="Category"
            v-model="value.category"
            :editMode="editMode"
        />
        <Date
            label="CreateDate"
            v-model="value.createDate"
            :editMode="editMode"
        />
        <Date
            label="ModifyDate"
            v-model="value.modifyDate"
            :editMode="editMode"
        />
        <String
            label="BookSummary"
            v-model="value.bookSummary"
            :editMode="editMode"
        />
        <String
            label="BookCoverImage"
            v-model="value.bookCoverImage"
            :editMode="editMode"
        />
        <String
            label="BookContent"
            v-model="value.bookContent"
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
    name: 'Book',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "books",
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
