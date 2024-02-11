package com.example.adminwaveoffood

import OrderDetailsActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.adapter.PendingOrderAdapter
import com.example.adminwaveoffood.databinding.ActivityPendingOrderBinding
import com.example.adminwaveoffood.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOrderActivity : AppCompatActivity(),PendingOrderAdapter.OnItemClicked {
    private lateinit var binding : ActivityPendingOrderBinding
    private var listOfName:MutableList<String> = mutableListOf()
    private var listOfTotalPrice:MutableList<String> = mutableListOf()
    private var listOfImagesFirstFoodOrder:MutableList<String> = mutableListOf()
    private var listOrderItem :ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //initialization of database
        database = FirebaseDatabase.getInstance()
        //initialization of database reference
        databaseOrderDetails = database.reference.child("OrderDetails")

        getOrderDetails()
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun getOrderDetails() {
         //retrieve order details from firebase database
        databaseOrderDetails.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                 for (orderSnapshot in snapshot.children){
                     val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                     orderDetails?.let {
                         listOrderItem.add(it)

                     }
                 }
                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

             }

        })
    }

    private fun addDataToListForRecyclerView() {
        for (orderItem in listOrderItem){
            //add data to respective list for populating recycler view
            orderItem.userName?.let { listOfName.add(it) }
            orderItem.totalPrice?.let { listOfTotalPrice.add(it) }
            orderItem.foodImages?.filterNot {it.isEmpty()  }?.forEach {
                listOfImagesFirstFoodOrder.add(it)
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
         binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PendingOrderAdapter(this,listOfName,listOfTotalPrice,listOfImagesFirstFoodOrder,this)
        binding.pendingOrderRecyclerView.adapter = adapter
    }

    override fun onItemClickListener(position: Int){
        val intent = Intent(this,OrderDetailsActivity::class.java)
        val userOrderDetails = listOrderItem[position]
        intent.putExtra("UserOrderDetails",userOrderDetails)
        startActivity(intent)
    }
}





















































