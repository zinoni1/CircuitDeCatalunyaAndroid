package com.victormoyano.circuitcatalunya

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConfiguracioActivity : AppCompatActivity() {
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuracio)
        context = this
        //boton cerrar sesion
        val btnCerrarSesion = findViewById<Button>(R.id.buttonLogout)
        val btnChangePass = findViewById<Button>(R.id.buttonChangePassword)
        val textPass = findViewById<EditText>(R.id.editTextUsername)
        btnCerrarSesion.setOnClickListener {
            //cerrar sesion
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnChangePass.setOnClickListener {
            //cambiar contraseña
            val pass = textPass.text.toString()
            if (pass.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    val idLogat = HomeActivity.IdLogatHolder.getIdLogat()
                    val response : Int = RetrofitConnection.service.changePass(idLogat!!,pass)
                    if (response == 1) {
                        MainActivity().mostrarToastPersonalizado(context, "Contrasenya canviada")

                    }else {
                        MainActivity().mostrarToastPersonalizado(
                            context,
                            "Error al canviar la contrasenya"
                        )
                    }
            }
        }else {
                MainActivity().mostrarToastPersonalizado(context, "Introdueix una contrasenya")
            }


    }
}
}