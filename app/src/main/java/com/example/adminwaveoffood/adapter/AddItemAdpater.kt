package com.example.adminwaveoffood.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwaveoffood.databinding.ItemItemBinding

class AddItemAdapter(
    private val menuItemName: ArrayList<String>,
    private val menuItemPrice: ArrayList<String>,
    private val menuItemImage: ArrayList<Int>
) : RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>() {

    private val itemQuantities = IntArray(menuItemName.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItemName.size

    inner class AddItemViewHolder(private val binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                foodNameTextView.text = menuItemName[position]
                priceTextView.text = menuItemPrice[position]
                foodImageView.setImageResource(menuItemImage[position])
quantityTextView.text = quantity.toString()

                minusButton.setOnClickListener {
                    decreaseQuantity(position)
                }
                plusButton.setOnClickListener {
                    increaseQuantity(position)
                }
                deleteButton.setOnClickListener {
                    deleteQuantity(position)
                }
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.quantityTextView.text=itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position]>1){
                itemQuantities[position]--
                binding.quantityTextView.text=itemQuantities[position].toString()
            }
        }

        private fun deleteQuantity(position: Int) {
            menuItemName.removeAt(position)
            menuItemPrice.removeAt(position)
            menuItemImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuItemName.size)
        }
    }
}
























