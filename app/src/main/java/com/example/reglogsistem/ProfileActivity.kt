package com.example.reglogsistem

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.reglogsistem.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        userNameFromDb()
        onClick()

    }

    private fun onClick() {

        binding.apply {
            btnLogOut.setOnClickListener {
                auth.signOut()
                clicked(MainActivity())
                this@ProfileActivity.finish()
            }
        }
    }

    private fun userNameFromDb() {
        val user = auth.currentUser

        if (user != null) {
            val userId = user.uid

            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(userId)
                userRef.get()
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {
                            val userData = documentSnapshot.data
                            val username = userData?.get("username")
                            val email = userData?.get("email")

                            if (username != null) {
                                binding.tvUserName.text = username.toString()
                                binding.tvEmail.text = email.toString()
                            } else {
                                Toast.makeText(this@ProfileActivity, "Username not found", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(this@ProfileActivity, "User data not found", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this@ProfileActivity, "Error while retrieving user data: $e", Toast.LENGTH_LONG).show()
                    }
        } else {
            Toast.makeText(this, "User is not authenticated", Toast.LENGTH_LONG).show()
        }
    }

    private fun clicked(targetActivity: Activity) {
        startActivity(Intent(this, targetActivity::class.java))
    }

}