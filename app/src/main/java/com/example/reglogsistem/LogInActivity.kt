package com.example.reglogsistem

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.reglogsistem.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var email: String = ""
    private var pass: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        onClick()

    }

    private fun onClick() {

        binding.apply {

            imgBtnBack.setOnClickListener {
                finish()
            }
            btnNext.setOnClickListener {

                if (edEmail.text.toString().isEmpty() && edPass.text.toString().isEmpty()) {
                    Toast.makeText(this@LogInActivity, "please fill all inputs.",Toast.LENGTH_LONG).show()
                } else {
                    email = edEmail.text.toString()
                    pass = edPass.text.toString()

                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this@LogInActivity, "Success",Toast.LENGTH_LONG).show()
                            clicked(ProfileActivity())
                            this@LogInActivity.finish()
                        }else {
                            Toast.makeText(this@LogInActivity, "Log in error",Toast.LENGTH_LONG).show()
                        }
                    }

                }
            }
        }
    }


    private fun clicked(targetActivity: Activity) {
        startActivity(Intent(this, targetActivity::class.java))
    }
}