    package com.victormoyano.circuitcatalunya

    import android.os.Bundle
    import android.util.Log
    import android.widget.ImageButton
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
        private lateinit var dataInicioTextView: TextView
        private lateinit var dataFinTextView: TextView
        private lateinit var prioritatTextView: TextView
        private lateinit var zonesTextView: TextView
        private lateinit var tipusMantenimentTextView: TextView
        private lateinit var asignarTextView: TextView
        private lateinit var imageButton: ImageButton
        private lateinit var imageView4: ImageView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.info_averia)

            problemTextView = findViewById(R.id.problem)
            descriptionTextView = findViewById(R.id.description)
            dataInicioTextView = findViewById(R.id.dataInicio)
            tipusMantenimentTextView = findViewById(R.id.TipusManteniment)
            zonesTextView = findViewById(R.id.Zones)
            asignarTextView = findViewById(R.id.Asignar)
            prioritatTextView = findViewById(R.id.Prioritat)
            imageButton = findViewById(R.id.imageButton)
            imageView4 = findViewById(R.id.imageView4)

            val averiaId = intent.getIntExtra("averiaId", 0)
            Log.d("InfoAveria", "Averia ID: $averiaId")

            CoroutineScope(Dispatchers.Main).launch {
                val response = RetrofitConnection.service.getAveria(averiaId)
                if (response.isSuccessful) {
                    val averia = response.body()?.firstOrNull()
                    updateUI(averia)
                } else {
                    Log.e("InfoAveria", "Error: ${response.code()}")
                }
            }
        }

        private fun updateUI(averia: Averias?) {
            averia?.let {
                problemTextView.text = it.Incidencia
                descriptionTextView.text = it.descripcion
                dataInicioTextView.text = it.data_inicio
                dataFinTextView.text = it.data_fin ?: "No definida"
                prioritatTextView.text = it.prioridad
                zonesTextView.text = it.zona_id.toString() // Asumiendo que `zona_id` es un identificador numérico
                tipusMantenimentTextView.text = it.tipo_averias_id.toString() // Asumiendo que `tipo_averias_id` es un identificador numérico
                asignarTextView.text = it.tecnico_asignado_id.toString() // Asumiendo que `tecnico_asignado_id` es un identificador numérico

                // Carga la imagen usando Picasso
                if (it.imagen.isNullOrEmpty()) {
                    imageButton.setImageResource(R.drawable.button_icon_selector) // Imagen por defecto si no hay URL
                } else {
                    Picasso.get().load(it.imagen).into(imageButton)
                }

                // Suponiendo que image_url se refiere a otra imagen, como un mapa o similar
                Picasso.get().load(it.image_url).into(imageView4)
            }
        }
    }
