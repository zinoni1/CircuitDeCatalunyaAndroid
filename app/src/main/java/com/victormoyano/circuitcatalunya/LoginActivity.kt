package com.victormoyano.circuitcatalunya

import android.content.Intent
import android.os.Bundle
import android.widget.Button // Asegúrate de importar Button
import androidx.appcompat.app.AppCompatActivity
import com.victormoyano.circuitcatalunya.HomeActivity
import com.victormoyano.circuitcatalunya.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Encuentra el botón de inicio de sesión en tu layout
        val loginButton: Button = findViewById(R.id.loginButton)

        // Agrega un OnClickListener al botón de inicio de sesión
        loginButton.setOnClickListener {
            // Crea un Intent para iniciar la HomeActivity
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)

            // Inicia la HomeActivity
            startActivity(intent)
        }
    }
}
