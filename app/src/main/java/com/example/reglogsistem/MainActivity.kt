package com.example.reglogsistem

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reglogsistem.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


open class MainActivity : AppCompatActivity() {

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
//                clicked(LogInActivity())
                scannerLauncher.launch(ScanOptions().setPrompt("Qr Scan")
                    .setDesiredBarcodeFormats(ScanOptions.EAN_13))
            }

        }
    }

    private val scannerLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { resulst ->
        if (resulst == null) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, resulst.contents, Toast.LENGTH_SHORT).show()
        }
    }


    private fun clicked(targetActivity: Activity) {
        startActivity(Intent(this, targetActivity::class.java))
    }


}
