package com.example.recyclerviewlab.sectionList

import retrofit2.http.GET

interface PeopleListApi {

    @GET("people")
    suspend fun fetchAllPeopleLists(): PeopleResult
}