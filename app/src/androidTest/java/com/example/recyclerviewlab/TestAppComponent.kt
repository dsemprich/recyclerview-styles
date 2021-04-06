package com.example.recyclerviewlab

import android.content.Context
import com.example.recyclerviewlab.mockData.PeopleMockData
import com.example.recyclerviewlab.sectionList.PeopleListApi
import com.example.recyclerviewlab.sectionList.PeopleResult
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
    modules = [ApplicationModule::class, FakePeopleListModule::class]
)
interface TestAppComponent: AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): TestAppComponent
    }
}

@Module
class FakePeopleListModule {

    @Provides
    fun peopleListApi(context: Context): PeopleListApi = object : PeopleListApi {
        override suspend fun fetchAllPeopleLists(): PeopleResult {
            return PeopleResult.fromJson(PeopleMockData.peopleMockResponseSmall(context))
        }
    }
}