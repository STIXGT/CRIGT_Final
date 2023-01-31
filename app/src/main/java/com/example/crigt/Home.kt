package com.example.crigt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.crigt.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth


class Home : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
            
            
    private lateinit var binding3: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding3 = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding3.root)


        emailFocusListener()
        passwordFocusListener()



        firebaseAuth= Firebase.auth
        binding3.btningresar.setOnClickListener()
        {
                if (binding3.EditTextCorreo.text.toString().isEmpty() || binding3.EditTextContra.text.toString().isEmpty()) {

                    Toast.makeText(baseContext, "Rellena los campos con un correo y una contraseña", Toast.LENGTH_SHORT).show()
                }
                else {singIn(binding3.EditTextCorreo.text.toString(), binding3.EditTextContra.text.toString())
                }

        }
    }


    private fun singIn(email: String, password: String)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "A iniciado sesión con el correo: \n ${binding3.EditTextCorreo.text.toString()}", Toast.LENGTH_SHORT).show()
                    val intentFinal = Intent(this, Final::class.java)
                    startActivity(intentFinal)
                    send()
                }
                else
                {
                    Toast.makeText(baseContext,"Ingrese un correo o contraña válidos" , Toast.LENGTH_SHORT).show()
                }
            }
    }



    private fun emailFocusListener() {
        binding3.EditTextCorreo.setOnFocusChangeListener { _, focuses ->
            if (!focuses) {
                binding3.CorreoContainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding3.EditTextCorreo.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Correo Electrónico no válido"
        }
        return null
    }

    private fun passwordFocusListener() {
        binding3.EditTextContra.setOnFocusChangeListener { _, focuses ->
            if (!focuses) {
                binding3.ContraContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val passwordText = binding3.EditTextContra.text.toString()
        if (passwordText.length < 8) {
            return "Minimo 8 caracteres"
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return "Minimo una letra mayúscula"
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return "Minimo una letra minúscula"
        }
        if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Minimo un caracter especial"
        }
        return null
    }

    private fun send(){
        val intentSend = Intent(this, Final::class.java)
        val email = binding3.EditTextCorreo.text.toString()
        val password = binding3.EditTextContra.text.toString()
        intentSend.putExtra("correo", email)
        intentSend.putExtra("contraseña", password)
        startActivity(intentSend)
    }
}


