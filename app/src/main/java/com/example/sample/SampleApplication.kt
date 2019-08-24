package com.example.sample

import android.app.Application
import android.content.Context
import com.example.sample.injection.AppComponent
import com.example.sample.injection.AppModule
import com.example.sample.injection.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class SampleApplication : Application(), HasAndroidInjector {

    override fun androidInjector(): AndroidInjector<Any>? {
        return activityDispatchingAndroidInjector
    }


    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Any>


    override fun onCreate() {
        super.onCreate()
        var appComponent: AppComponent = DaggerAppComponent.builder().appModule(AppModule(this))
            .build()
        appComponent.inject(this)
    }

}