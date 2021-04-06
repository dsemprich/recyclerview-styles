package com.example.recyclerviewlab

import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.recyclerviewlab.sectionList.ContactsFragment
import com.example.recyclerviewlab.sectionList.PeopleListModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module


@Component(
    modules = [ApplicationModule::class, PeopleListModule::class]
)
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(contactsFragment: ContactsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}

@Module
class ApplicationModule(private val applicationContext: Context) {

}

class BaseApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    @VisibleForTesting fun setTestComponent(component: AppComponent) {
        appComponent = component
    }

}