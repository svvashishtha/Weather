package com.example.sample.injection

import androidx.lifecycle.ViewModelProvider
import com.example.sample.views.weather.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class) // PROVIDE YOUR OWN MODELS HERE
    internal abstract fun bindWeatherViewModel(editPlaceViewModel: WeatherViewModel): WeatherViewModel

}