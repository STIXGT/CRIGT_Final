package com.example.crigt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.crigt.databinding.ActivityFinalBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class Final : AppCompatActivity() {
    private lateinit var binding4: ActivityFinalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding4= ActivityFinalBinding.inflate(layoutInflater)
        setContentView(binding4.root)
        generador()

    }

    private fun generador(){
        try {
            val email = intent.getStringExtra("correo")
            val password = intent.getStringExtra("contraseña")
            val barcodeEncoder = BarcodeEncoder()
            val qrfoto = barcodeEncoder.encodeBitmap(email, BarcodeFormat.QR_CODE, 750, 750)

            binding4.imageqr.setImageBitmap(qrfoto)
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }
}