package com.example.recyclerviewlab

import android.app.Application
import android.content.Context
import com.example.recyclerviewlab.backend.Backend
import com.example.recyclerviewlab.backend.BackendImpl
import com.example.recyclerviewlab.backend.sendAwait
import com.example.recyclerviewlab.mockData.PeopleMockData
import com.example.recyclerviewlab.sectionList.*
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.*
import javax.inject.Singleton

@Singleton
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
class FakePeopleListModule() {

    @Provides
    fun provideService(backend: Backend) = PeopleListService(backend)

    @Provides
    fun provideRepository(
        service: PeopleListService,
        mapper: PeopleListMapper
    ) = PeopleListRepository(service, mapper)


    companion object {
        @JvmStatic
        @Singleton
        @Provides
        fun provideBackend(context: Context): Backend = BackendImpl(context)
    }
}