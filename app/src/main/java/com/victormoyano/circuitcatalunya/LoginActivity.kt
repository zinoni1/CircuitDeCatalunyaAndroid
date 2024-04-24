package com.victormoyano.circuitcatalunya

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = RetrofitConnection.service.verifyCredentials(email, password)
                        if (response.isSuccessful) {
                            val isSuccess = response.body() ?: false
                            if (isSuccess) {
                                withContext(Dispatchers.Main) {
                                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    mostrarToastPersonalizado(this@LoginActivity, "Credencials incorrectes")
                                }
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                mostrarToastPersonalizado(this@LoginActivity, "Error de connexió")
                            }
                        }


                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            mostrarToastPersonalizado(this@LoginActivity, "Error de connexió")
                        }
                    }
                }
            } else {
                mostrarToastPersonalizado(this, "Introdueix les dades")
            }
        }
    }



    fun mostrarToastPersonalizado(context: Context, mensaje: String) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.toast, null)

        // Encuentra el TextView en el diseño del Toast
        val textView = layout.findViewById<TextView>(R.id.title)
        // Establece el texto deseado
        textView.text = mensaje

        // Crea y muestra el Toast personalizado
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

}
