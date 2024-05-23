package com.victormoyano.circuitcatalunya

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import com.victormoyano.circuitcatalunya.models.Averias
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoAveria : AppCompatActivity() {

    private lateinit var problemTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var imageView: ImageView
    // Agrega aquí los demás elementos de la interfaz de usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_averia)

        problemTextView = findViewById(R.id.problem)
        descriptionTextView = findViewById(R.id.description)
        imageView = findViewById(R.id.imageButton)
        // Inicializa aquí los demás elementos de la interfaz de usuario

        val averiaId = intent.getIntExtra("averiaId", 0)

        CoroutineScope(Dispatchers.Main).launch {
            val response = RetrofitConnection.service.getAveria(averiaId)
            if (response.isSuccessful) {
                val averia = response.body()
                updateUI(averia)
            } else {
                // Maneja el error
            }
        }
    }
    private fun updateUI(averia: Averias?) {
        averia?.let {
            problemTextView.text = it.Incidencia
            descriptionTextView.text = it.descripcion
            Picasso.get().load(it.image_url).into(imageView)
            // Actualiza aquí los demás elementos de la interfaz de usuario
        }
    }
}