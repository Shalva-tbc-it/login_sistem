package com.example.reglogsistem

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.reglogsistem.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClick()

    }

    private fun onClick() {

        binding.apply {

            imgBtnBack.setOnClickListener {
                finish()
            }

        }
    }

    private fun clicked(targetActivity: Activity) {
        startActivity( Intent(this, targetActivity::class.java))
    }
}