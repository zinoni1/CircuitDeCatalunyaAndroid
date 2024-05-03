package com.victormoyano.circuitcatalunya

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.victormoyano.circuitcatalunya.ui.MainViewModel

class MainActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 3000 // 3 segundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var mainviewmodel = MainViewModel()
        mainviewmodel.listUsers()
        mainviewmodel.listAverias()
        // Creamos un Handler para manejar el retraso
        Handler().postDelayed({
            // Creamos un Intent para abrir LoginActivity
            val intent = Intent(this, Averia_sinlogin::class.java)
            startActivity(intent)
            // No necesitamos finalizar la MainActivity, para que el usuario pueda regresar a ella
        }, SPLASH_TIME_OUT)
    }
}
