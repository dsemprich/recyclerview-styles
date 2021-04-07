package com.example.recyclerviewlab.sectionList

import android.app.Application
import android.content.Context
import com.example.recyclerviewlab.backend.Backend
import com.example.recyclerviewlab.backend.BackendImpl
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

    @Provides
    fun provideViewModelFactory(repository: PeopleListRepository) = PlaylistViewModelFactory(repository)

    @Provides
    fun provideMapper() = PeopleListMapper()

    @Provides
    fun provideService(backend: Backend) = PeopleListService(backend)

    @Provides
    fun provideRepository(
        service: PeopleListService,
        mapper: PeopleListMapper) = PeopleListRepository(service, mapper)

    @Provides
    fun provideBackend(context: Context) : Backend = BackendImpl(context)

}