package com.example.adminwaveoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.adminwaveoffood.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private  val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.CreateButton.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        binding.alreadyHaveAccountButton.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        val locationList = arrayOf("Nairobi","Nakuru","Kisumu","Mombasa")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
    }
}