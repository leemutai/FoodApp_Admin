package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.adapter.AddItemAdapter
import com.example.adminwaveoffood.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val menuFoodName = listOf("Burger","Sandwich","Momo","Item","Sandwich","Momo")
        val menuItemPrice = listOf("$5","$6","$10","$5","$6","$10")
        val menuImage = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6,
        )
        val adapter = AddItemAdapter(ArrayList(menuFoodName),
            ArrayList(menuItemPrice),ArrayList(menuImage))
        binding.MenuRecylerView.layoutManager = LinearLayoutManager(this)
        binding.MenuRecylerView.adapter = adapter

    }
}






























