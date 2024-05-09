package com.victormoyano.circuitcatalunya

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var olvidado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
olvidado = findViewById(R.id.forgotPasswordTextView)
        olvidado.setOnClickListener {
            val intent = Intent(this, Averia_sinlogin::class.java)
            startActivity(intent)
        }


        emailEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

        /*loginButton.setOnClickListener {
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
                                    MainActivity().mostrarToastPersonalizado(this@LoginActivity, "Credencials incorrectes")
                                }
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                MainActivity().mostrarToastPersonalizado(this@LoginActivity, "Error de connexió")
                            }
                        }


                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            MainActivity().mostrarToastPersonalizado(this@LoginActivity, "Error de connexió")
                        }
                    }
                }
            } else {
                MainActivity().mostrarToastPersonalizado(this, "Introdueix les dades")
            }
        }
    }*/
}