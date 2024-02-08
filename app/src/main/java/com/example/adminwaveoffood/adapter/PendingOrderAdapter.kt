package com.example.adminwaveoffood.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminwaveoffood.databinding.PendingOrdersItemsBinding

class PendingOrderAdapter(
    private val context: Context,
    private val customerNames: MutableList<String>,
    private val quantity: MutableList<String>,
    private val foodImage: MutableList<String>,
    //private val itemClicked: OnItemClicked,
) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding =
            PendingOrdersItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderViewHolder(binding)
    }

    override fun getItemCount(): Int = customerNames.size
    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class PendingOrderViewHolder(private val binding: PendingOrdersItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                customerName.text = customerNames[position]
                pendingOrderQuantity.text = quantity[position]
                var uriString = foodImage[position]
                var uri = Uri.parse(uriString)
                 Glide.with(context).load(uri).into(orderedFoodImage)

                orderedAcceptButton.apply {
                    if (!isAccepted) {
                        text = "Accept"
                    } else {
                        text = "Dispatch"
                    }
                    setOnClickListener {
                        if (!isAccepted) {
                            text = "Dispatch"
                            isAccepted = true
                            showToast("Order is accepted")
                        } else {
                            customerNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order Is dispatched")

                        }
                    }
                }
            }
        }

        private fun showToast(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

    }
}





































