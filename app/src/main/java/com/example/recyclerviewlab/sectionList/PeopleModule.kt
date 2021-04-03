package com.example.recyclerviewlab.sectionList

import android.content.Context
import com.example.recyclerviewlab.mockData.PeopleMockData
import com.google.gson.Gson
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val client = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okhttp", client)

@Module
@InstallIn(FragmentComponent::class)
class PeopleListModule {

    @Provides
    fun peopleListApi(@ApplicationContext context: Context): PeopleListApi = object : PeopleListApi {
        override suspend fun fetchAllPeopleLists(): PeopleResult {
            return PeopleResult.fromJson(PeopleMockData.peopleMockResponse(context))
        }
    }
}