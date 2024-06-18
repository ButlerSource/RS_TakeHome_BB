package com.butler.takehome.ui.driverlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.butler.takehome.domain.model.Driver
import com.butler.takehome.databinding.ItemDriverBinding

class DriverAdapter(private val onClick: (Driver) -> Unit) :
    RecyclerView.Adapter<DriverAdapter.DriverViewHolder>() {

    private var driverList = listOf<Driver>()

    fun addDrivers(drivers: List<Driver>) {
        val oldPosition = driverList.size
        driverList = drivers
        notifyItemRangeChanged(oldPosition, driverList.size)
    }

    fun clearList() {
        driverList = listOf<Driver>()
        notifyItemRangeRemoved(0, 0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val binding = ItemDriverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DriverViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        with(holder) {
            with(driverList[position]) {
                binding.driverName.text = name
                binding.driverId.text = "ID: $id"
                itemView.setOnClickListener {
                    onClick(this)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return driverList.size
    }

    fun getList(): List<Driver> {
        return driverList
    }

    inner class DriverViewHolder(val binding: ItemDriverBinding) :
        RecyclerView.ViewHolder(binding.root)
}