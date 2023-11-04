package com.example.reglogsistem

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import com.example.reglogsistem.databinding.ActivityRegBinding

class RegActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.edEmail.requestFocus()
        onClick()

    }

    private fun onClick() {

        binding.apply {


            imgBtnBack.setOnClickListener {
                finish()
            }

            btnNext.setOnClickListener {
                edPass.visibility = View.GONE
                btnNext.visibility = View.GONE
                edEmail.hint = getString(R.string.user)
                btnSignUp.visibility = View.VISIBLE
                tvPrivecy.visibility = View.VISIBLE

            }

        }
    }

    private fun clicked(targetActivity: Activity) {
        startActivity( Intent(this, targetActivity::class.java))
    }

    private fun isStrongPassword(password: String): Boolean {
        val pattern = Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@$!%*?&]{8,}\$")
        return pattern.matches(password)
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }



}