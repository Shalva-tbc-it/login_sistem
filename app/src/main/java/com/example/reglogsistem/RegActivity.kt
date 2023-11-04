package com.example.reglogsistem

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.reglogsistem.databinding.ActivityRegBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.security.cert.CertStore

class RegActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var email: String = " "
    private var pass: String = " "
    private var username: String = " "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        onClick()

    }

    private fun onClick() {

        binding.apply {

            imgBtnBack.setOnClickListener {
                if (btnNext.visibility == View.GONE) {
                    edPass.visibility = View.VISIBLE
                    btnNext.visibility = View.VISIBLE
                    edEmail.hint = getString(R.string.email)
                    btnSignUp.visibility = View.GONE
                    tvPrivecy.visibility = View.GONE
                } else{
                    finish()
                }
            }

            btnNext.setOnClickListener {
                if (!isValidEmail(edEmail.text) && !isStrongPassword(edPass.text.toString())) {

                }else {
                    edPass.visibility = View.GONE
                    btnNext.visibility = View.GONE
                    edEmail.hint = getString(R.string.user)
                    btnSignUp.visibility = View.VISIBLE
                    tvPrivecy.visibility = View.VISIBLE


                    email = edEmail.text.toString()
                    pass = edPass.text.toString()

//                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            Toast.makeText(this@RegActivity, "Success",Toast.LENGTH_LONG).show()
//                        }else {
//                            Toast.makeText(this@RegActivity, "Error sign",Toast.LENGTH_LONG).show()
//                        }
//                    }

                }

            }

            btnSignUp.setOnClickListener {

                username = edEmail.text.toString()

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Регистрация прошла успешно
                            val user = FirebaseAuth.getInstance().currentUser
                            // Добавление дополнительного поля в базу данных Firestore
                            if (user != null) {
                                val userData = hashMapOf(
                                    "email" to email,
                                    "username" to username
                                )

                                FirebaseFirestore.getInstance().collection("users")
                                    .document(user.uid)
                                    .set(userData)
                                    .addOnSuccessListener {
                                        // Поле успешно добавлено в Firestore
                                    }
                                    .addOnFailureListener { e ->
                                        // Ошибка при добавлении поля
                                    }
                            }
                        } else {
                            // Ошибка при регистрации
                        }
                    }
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