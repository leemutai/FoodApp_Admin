package com.example.adminwaveoffood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwaveoffood.databinding.OrderDetailItemBinding

class OrderDetailsAdapter(
    private val context: Context,
    private var foodName: MutableList<String>,
    private var foodImage: MutableList<String>,
    private var foodQuantity: MutableList<Int>,
    private var foodPrice: MutableList<String>
): RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        val binding = OrderDetailItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderDetailsViewHolder(binding)

    }
    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = foodName.size
    class OrderDetailsViewHolder(private val binding: OrderDetailItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
             binding.apply {
                 foodName.text = foodName[position]
             }
        }
    }
}