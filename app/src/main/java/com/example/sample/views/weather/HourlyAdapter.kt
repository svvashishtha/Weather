package com.example.sample.views.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.data.models.HourlyWeather
import com.example.sample.databinding.HourlyWeatherLayoutBinding
import com.example.sample.utils.ResourceUtils


class HourlyAdapter(var resourceUtils: ResourceUtils) :
    ListAdapter<HourlyWeather, HourlyViewHolder>(HourlyDataDiffCalculator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        return HourlyViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.hourly_weather_layout,
                parent,
                false
            ), resourceUtils
        )
    }


    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

class HourlyViewHolder(var binding: HourlyWeatherLayoutBinding, resourceUtils: ResourceUtils) :
    RecyclerView.ViewHolder(binding.root) {
    private var hourlyWeatherViewModel: HourlyWeatherViewModel = HourlyWeatherViewModel(resourceUtils)

    fun bind(item: HourlyWeather) {
        hourlyWeatherViewModel.data = item
        binding.viewModel = hourlyWeatherViewModel
        binding.executePendingBindings()
    }
}

class HourlyDataDiffCalculator : DiffUtil.ItemCallback<HourlyWeather>() {
    override fun areItemsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
        return oldItem.time == newItem.time
    }

}