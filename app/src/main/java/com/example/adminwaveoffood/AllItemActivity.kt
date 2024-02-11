package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.adapter.MenuItemAdapter
import com.example.adminwaveoffood.databinding.ActivityAllItemBinding
import com.example.adminwaveoffood.model.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private   var menuItems : ArrayList<AllMenu> = ArrayList()
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveMenuItem()



//        val menuFoodName = listOf("Burger","Sandwich","Momo","Item","Sandwich","Momo")
//        val menuItemPrice = listOf("$5","$6","$10","$5","$6","$10")
//        val menuImage = listOf(
//            R.drawable.menu1,
//            R.drawable.menu2,
//            R.drawable.menu3,
//            R.drawable.menu4,
//            R.drawable.menu5,
//            R.drawable.menu6,
//
//        )
        binding.backButton.setOnClickListener {
            finish()
        }


    }

    private fun retrieveMenuItem() {
database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
        //fetch data from database
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                 //clear existing data before populating
                menuItems.clear()

                //loop for through each food item
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(AllMenu::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError","Error: ${error.message}")
            }
        })
    }

    private fun setAdapter() {

        val adapter = MenuItemAdapter(this@AllItemActivity,menuItems,databaseReference){position ->
            deleteMenuItems(position)
        }
        binding.MenuRecylerView.layoutManager = LinearLayoutManager(this)
        binding.MenuRecylerView.adapter = adapter
    }

    private fun deleteMenuItems(position: Int) {
         val menuItemToDelete = menuItems[position]
        val menuItemKey = menuItemToDelete.key
        val foodMenuReference:DatabaseReference = database.reference.child("menu").child(menuItemKey!!)
        foodMenuReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful){
                menuItems.removeAt(position)
                binding.MenuRecylerView.adapter?.notifyItemRemoved(position)
            }else{
                Toast.makeText(this,"Item Not Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}






























