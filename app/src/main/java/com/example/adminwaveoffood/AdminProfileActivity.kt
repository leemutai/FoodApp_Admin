package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.adminwaveoffood.databinding.ActivityAdminProfileBinding
import com.example.adminwaveoffood.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminProfileActivity : AppCompatActivity() {
    private val binding:ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminReference = database.reference.child("user")


        binding.backButton.setOnClickListener {
            finish()
        }
        binding.saveInfoButton.setOnClickListener {
            updateUserData()
        }
        binding.name.isEnabled = false
        binding.address.isEnabled = false
        binding.email.isEnabled = false
        binding.phone.isEnabled = false
        binding.password.isEnabled = false
        binding.saveInfoButton.isEnabled = false

        var isEnable = false
        binding.editButton.setOnClickListener {
            isEnable = ! isEnable
            binding.name.isEnabled = isEnable
            binding.address.isEnabled = isEnable
            binding.email.isEnabled = isEnable
            binding.phone.isEnabled = isEnable
            binding.password.isEnabled = isEnable
            if (isEnable){
                binding.name.requestFocus()
            }
            binding.saveInfoButton.isEnabled = isEnable
        }
        retrieveUserData()

        }



    private fun retrieveUserData() {
        val currentUserUid:String? = auth.currentUser?.uid
        if (currentUserUid != null){
            val userReference:DatabaseReference = adminReference.child(currentUserUid)

            userReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var ownerName:Any? = snapshot.child("name").getValue()
                        var email:Any? = snapshot.child("email").getValue()
                        var password:Any? = snapshot.child("password").getValue()
                        var address:Any? = snapshot.child("address").getValue()
                        var phone :Any? = snapshot.child("phone").getValue()
                        Log.d("TAG","onDataChange: $ownerName,")
                        setDataToTextView(ownerName,email,password,address,phone)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }

    }

    private fun setDataToTextView(
        ownerName: Any?,
        email: Any?,
        password: Any?,
        address: Any?,
        phone: Any?,
    ) {
        binding.name.setText(ownerName.toString())
        binding.email.setText(email.toString())
        binding.password.setText(password.toString())
        binding.address.setText(address.toString())
        binding.phone.setText(phone.toString())

    }
    private fun updateUserData() {
         var updateName = binding.name.text.toString()
        var updateEmail = binding.email.text.toString()
        var updatePassword = binding.password.text.toString()
        var updateAddress = binding.address.text.toString()
        var updatePhone = binding.phone.text.toString()
        val currentUserUid:String? = auth.currentUser?.uid
        if (currentUserUid !=null){
            val userReference:DatabaseReference = adminReference.child(currentUserUid)
            userReference.child("name").setValue(updateName)
            userReference.child("email").setValue(updateEmail)
            userReference.child("password").setValue(updatePassword)
            userReference.child("address").setValue(updateAddress)
            userReference.child("phone").setValue(updatePhone)

            Toast.makeText( this,"Profile updated successfully", Toast.LENGTH_SHORT).show()
            //update email and password for firebase authentication
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)
        } else{
            Toast.makeText( this,"Profile update failed", Toast.LENGTH_SHORT).show()
        }
    }
}
















