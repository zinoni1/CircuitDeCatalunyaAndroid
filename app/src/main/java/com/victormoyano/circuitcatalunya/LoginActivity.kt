package com.victormoyano.circuitcatalunya

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
                                    showToast("Credencials invàlides")
                                }
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                showToast("Credencials invàlides")
                            }
                        }


                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            showToast("Error de conexió")
                        }
                    }
                }
            } else {
                showToast("Por favor ingresa correo electrónico y contraseña")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }
}
