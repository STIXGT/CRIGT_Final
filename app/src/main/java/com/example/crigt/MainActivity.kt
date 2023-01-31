package com.example.crigt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.crigt.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btshowregister.setOnClickListener{register()}
        binding.btloggin.setOnClickListener{inicio()}
    }

    private fun inicio() {
        val intent1 = Intent(this, Home::class.java)
        startActivity(intent1)
    }

    private fun register(){
        val intent2= Intent(this, Register::class.java)
        startActivity(intent2)
    }
}