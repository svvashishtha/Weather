package com.example.sample.views.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.data.models.DailyWeather
import com.example.sample.databinding.DailyWeatherLayoutBinding
import com.example.sample.utils.ResourceUtils

class DailyAdapter(var resourceUtils: ResourceUtils) :
    ListAdapter<DailyWeather, DailyViewHolder>(DailyDataDiffCalculator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        return DailyViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.daily_weather_layout,
                parent,
                false
            ), resourceUtils
        )
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class DailyViewHolder(var binding: DailyWeatherLayoutBinding, resourceUtils: ResourceUtils) :
    RecyclerView.ViewHolder(binding.root) {
    private var dailyViewModel: DailyViewModel =
        DailyViewModel(resourceUtils)

    fun bind(item: DailyWeather) {
        dailyViewModel.data = item
        binding.viewModel = dailyViewModel
        binding.executePendingBindings()
    }
}

class DailyDataDiffCalculator : DiffUtil.ItemCallback<DailyWeather>() {
    override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
        return oldItem.time == newItem.time
    }

}