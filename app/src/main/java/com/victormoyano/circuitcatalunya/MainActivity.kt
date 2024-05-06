package com.victormoyano.circuitcatalunya

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
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
        mainviewmodel.listCargos()
        mainviewmodel.listZonas()
        mainviewmodel.listSector()
        // Creamos un Handler para manejar el retraso
        Handler().postDelayed({
            // Creamos un Intent para abrir LoginActivity
            val intent = Intent(this, Averia_sinlogin::class.java)
            startActivity(intent)
            // No necesitamos finalizar la MainActivity, para que el usuario pueda regresar a ella
        }, SPLASH_TIME_OUT)
    }
    public fun mostrarToastPersonalizado(context: Context, mensaje: String) {
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
