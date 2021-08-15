package com.oli.weatherforecast.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.oli.weatherforecast.data.model.Daily
import com.oli.weatherforecast.databinding.ItemDailyBinding
import com.oli.weatherforecast.util.Constants
import com.oli.weatherforecast.util.TimeUtil

class DailyAdapter(private val list: List<Daily?>) :
    RecyclerView.Adapter<DailyAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemDailyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, daily: Daily) {
            binding.txtDay.text = TimeUtil.epochToDay(daily.datetime!!)
            val min = "${daily.temp!!.min!!.toInt()} ${Constants.DEGREE_SYMBOL}"
            val max = "${daily.temp!!.max!!.toInt()} ${Constants.DEGREE_SYMBOL}"
            binding.temp.text = "H:$max   L:$min"
            val icon = daily.weather[0]!!.icon

            Glide
                .with(context)
                .load(Constants.ICON_URL + icon + ".png")
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .into(binding.ivWeather)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemDailyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)

    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(holder.itemView.context, list[position]!!)

    override fun getItemCount(): Int = list.size
}