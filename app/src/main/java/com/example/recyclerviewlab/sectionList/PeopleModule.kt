package com.example.recyclerviewlab.sectionList

import android.content.Context
import com.example.recyclerviewlab.mockData.PeopleMockData
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

val client = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okhttp", client)

@Module
class PeopleListModule {

    @Provides
    fun peopleListApi(context: Context): PeopleListApi = object : PeopleListApi {
        override suspend fun fetchAllPeopleLists(): PeopleResult {
            return PeopleResult.fromJson(PeopleMockData.peopleMockResponse(context))
        }
    }
}