package com.example.sample.injection

import com.example.sample.AppExecutors
import com.example.sample.SampleApplication
import com.example.sample.network.WeatherApi
import com.example.sample.network.WeatherApiService
import com.example.sample.utils.ResourceUtils
import com.example.sample.views.weather.DailyAdapter
import com.example.sample.views.weather.HourlyAdapter
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import javax.inject.Inject
import javax.inject.Singleton

@Module(
    includes = [
        ActivityModule::class,
        AndroidInjectionModule::class,
        ViewModelModule::class]
)
class AppModule(val application: SampleApplication) {
    @Provides
    @Singleton
    fun provideApplication() = application

    @Inject
    @Provides
    @Singleton
    fun provideResourcerUtils(application: SampleApplication): ResourceUtils {
        return ResourceUtils(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return WeatherApiService.create()
    }

    @Provides
    @Singleton
    fun provideAppExecutor(): AppExecutors {
        return AppExecutors()
    }

    @Provides
    @Inject
    fun provideHourlyAdapter(resourceUtils: ResourceUtils): HourlyAdapter {
        return HourlyAdapter(resourceUtils)
    }

    @Provides
    @Inject
    fun provideDailyAdapter(resourceUtils: ResourceUtils): DailyAdapter {
        return DailyAdapter(resourceUtils)
    }

}