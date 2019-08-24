package com.example.sample.injection

import com.example.sample.views.weather.HomeActivity
import com.example.sample.SampleApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        ActivityModule::class,
        AndroidInjectionModule::class,
        ViewModelModule::class]
)
interface AppComponent {

    fun inject(application: SampleApplication)

    fun injectHomeActivity(homeActivity: HomeActivity)

}