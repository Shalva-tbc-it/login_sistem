package com.example.reglogsistem

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.reglogsistem.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClick()

    }


    private fun onClick() {

        binding.apply {

            btnReg.setOnClickListener {
                clicked(RegActivity())
            }

            btnLog.setOnClickListener {
                clicked(LogInActivity())
            }

        }
    }


    private fun clicked(targetActivity: Activity) {
        startActivity( Intent(this, targetActivity::class.java))
    }


}