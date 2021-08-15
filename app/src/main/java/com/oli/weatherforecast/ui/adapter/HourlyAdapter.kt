package com.oli.weatherforecast.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.oli.weatherforecast.data.model.Hourly
import com.oli.weatherforecast.databinding.ItemHourlyBinding
import com.oli.weatherforecast.util.Constants
import com.oli.weatherforecast.util.TimeUtil

class HourlyAdapter(private val list: List<Hourly?>) :
    RecyclerView.Adapter<HourlyAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemHourlyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, hourly: Hourly) {
            binding.txtTime.text = TimeUtil.epochToShortTime(hourly.datetime!!)
            binding.txtTemp.text = "${hourly.temp!!.toInt()} ${Constants.DEGREE_SYMBOL}"
            val icon = hourly.weather[0]!!.icon

            Glide
                .with(context)
                .load(Constants.ICON_URL + icon + ".png")
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .into(binding.ivWeather)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)

    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(holder.itemView.context, list[position]!!)

    override fun getItemCount(): Int = list.size
}