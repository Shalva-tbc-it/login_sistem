package com.example.reglogsistem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.reglogsistem.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userNameFromDb()

    }

    private fun userNameFromDb() {
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val userId = user.uid

            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(userId)

            userRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val userData = documentSnapshot.data
                        val username = userData?.get("username")

                        if (username != null) {
                            binding.tvUserName.text = username.toString()
                        } else {
                            Toast.makeText(this, "Username not found", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this, "User data not found", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error while retrieving user data: $e", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this, "User is not authenticated", Toast.LENGTH_LONG).show()
        }
    }
}