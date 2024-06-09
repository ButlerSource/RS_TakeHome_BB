package com.butler.takehome.ui.driverlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.butler.takehome.data.model.Driver
import com.butler.takehome.databinding.ItemDriverBinding

class DriverAdapter(private val onClick: (Driver) -> Unit) :
    RecyclerView.Adapter<DriverAdapter.DriverViewHolder>() {

    private val driverList = mutableListOf<Driver>()

    fun addDriver(driver: Driver) {
        val oldPosition = driverList.size
        driverList.add(driver)
        notifyItemRangeChanged(oldPosition, driverList.size)
    }

    fun clearList() {
        driverList.clear()
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