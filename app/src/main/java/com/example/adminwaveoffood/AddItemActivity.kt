package com.example.adminwaveoffood

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwaveoffood.databinding.ActivityAddItemBinding
import com.example.adminwaveoffood.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddItemActivity : AppCompatActivity() {
    // Food item Details
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private lateinit var foodIngredients: String
    private var foodImageUri: Uri? = null

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()

        // Initialize Firebase database instance
        database = FirebaseDatabase.getInstance()

        binding.AddItemButton.setOnClickListener {
            // Get Data From Field
            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngredients = binding.ingredient.text.toString().trim()

            if (!foodName.isBlank() && !foodPrice.isBlank() && !foodDescription.isBlank() && !foodIngredients.isBlank()) {
                uploadData()
                Toast.makeText(this, "Item Added Successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show()
            }
        }

        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun uploadData() {
        //get a referrence to the menu
        // Implement your data upload logic here
        // Use foodName, foodPrice, foodDescription, foodIngredients, and foodImageUri as needed
        val menuRef: DatabaseReference = database.getReference("menu")
        //generate a unique key for the new item
        val newItemKey: String? = menuRef.push().key

        if (foodImageUri != null) {
            val storageRef: StorageReference = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    //crate new item menu
                    val newItem = AllMenu(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDescription = foodDescription,
                        foodIngredient = foodIngredients,
                        foodImage = downloadUrl.toString(),
                    )
                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "data uploaded successfully", Toast.LENGTH_SHORT)
                                .show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this, "data uploaded failed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }
                }

            }.addOnFailureListener {
                Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
            }

        } else{
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null)
                binding.selectedImage.setImageURI(uri)
                foodImageUri = uri
        }
}


















