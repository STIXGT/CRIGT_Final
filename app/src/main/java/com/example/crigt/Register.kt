package com.example.crigt


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.crigt.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {

    private lateinit var binding1: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding1 = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding1.root)

        emailFocusListener()
        passwordFocusListener()
        phoneFocusListener()

        binding1.btCrearCuenta.setOnClickListener{end()}

        binding1.btIngresar.setOnClickListener{home()}

    }


    private fun emailFocusListener() {
        binding1.tvemail.setOnFocusChangeListener{_, focuses ->
            if (!focuses){
                binding1.emailcontainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding1.tvemail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Correo Electrónico no válido"
        }
        return null
    }


    private fun passwordFocusListener() {
        binding1.tvpassword.setOnFocusChangeListener{_, focuses ->
            if (!focuses){
                binding1.passwordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val passwordText = binding1.tvpassword.text.toString()
        if (passwordText.length < 8)
        {
            return "Minimo 8 caracteres"
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Minimo una letra mayúscula"
        }
        if (!passwordText.matches(".*[a-z].*".toRegex()))
        {
            return "Minimo una letra minúscula"
        }
        if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex()))
        {
            return "Minimo un caracter especial"
        }
        return null
    }


    private fun phoneFocusListener() {
        binding1.tvcell.setOnFocusChangeListener{_, focuses ->
            if (!focuses){
                binding1.numberContainer.helperText = validNumber()
            }
        }
    }

    private fun validNumber(): String? {
        val numberText = binding1.tvcell.text.toString()
        if (!numberText.matches(".*[0-8].*".toRegex()))
        {
            return "Solo debe contener dígitos"
        }

        if (numberText.length != 10)
        {
            return "Debe tener 10 dígitos"
        }
        return null
    }


    private fun end()
    {
        binding1.emailcontainer.helperText = validEmail()
        binding1.numberContainer.helperText = validNumber()
        binding1.passwordContainer.helperText = validPassword()


        val validEmail = binding1.emailcontainer.helperText == null
        val validNumber = binding1.numberContainer.helperText == null
        val validPassword = binding1.passwordContainer.helperText == null

        if(validEmail && validNumber && validPassword){
            resetForm()
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                binding1.tvemail.text.toString(),
                binding1.tvpassword.text.toString()).addOnCompleteListener{task ->
                if (task.isComplete){
                    Toast.makeText(this,"Su cuenta se ha creado con éxito",Toast.LENGTH_SHORT).show()
                }
                else {
                    invalidForm()
                }
            }
        }
    }



    private fun invalidForm() {
        var message = ""
        if(binding1.emailcontainer.helperText != null)
            message += "\n\nCorreo: " + binding1.emailcontainer.helperText
        if(binding1.numberContainer.helperText != null)
            message += "\n\nNúmero: " + binding1.numberContainer.helperText
        if(binding1.passwordContainer.helperText != null)
            message += "\n\nContraseña: " + binding1.passwordContainer.helperText

        AlertDialog.Builder(this)
            .setTitle("Registro Incorrecto")
            .setMessage(message)
            .setPositiveButton("Okey"){ _,_ ->
                //nada
            }

            .show()
    }

    private fun resetForm() {
        var message = "Correo: " + binding1.tvemail.text
        message += "\nNúmero: " + binding1.tvcell.text
        message += "\nContraseña: " + binding1.tvpassword.text

        AlertDialog.Builder(this)
            .setTitle("Registro culminado")
            .setMessage(message)
            .setPositiveButton("Okey"){ _,_ ->
                binding1.tvemail.text = null
                binding1.tvcell.text = null
                binding1.tvpassword.text = null

                binding1.emailcontainer.helperText = getString(R.string.required)
                binding1.numberContainer.helperText = getString(R.string.required)
                binding1.passwordContainer.helperText = getString(R.string.required)

            }

            .show()
    }

    private fun home(){
        val IntentHome = Intent(this, Home::class.java)
        startActivity(IntentHome)
    }

}