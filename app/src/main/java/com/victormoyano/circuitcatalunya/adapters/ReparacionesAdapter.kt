package com.victormoyano.circuitcatalunya.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.victormoyano.circuitcatalunya.InfoAveria
import com.victormoyano.circuitcatalunya.R
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import com.victormoyano.circuitcatalunya.models.Averias
import com.victormoyano.circuitcatalunya.models.Zonas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ReparacionesAdapter(private val context: Context, var response: Response<List<Averias>>) :
    RecyclerView.Adapter<ReparacionesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.reparacionscard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = response.body()?.get(position)
        val zona = response.body()?.get(position)?.zona_id

        data?.let { holder.bindData(it) }
    }

    override fun getItemCount(): Int {
        return response.body()?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.subtitleTextView)
        private val zonaTextView: TextView = itemView.findViewById(R.id.zonaTextView)
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val infoImageView: ImageView = itemView.findViewById(R.id.info)

        fun bindData(data: Averias) {
            CoroutineScope(Dispatchers.Main).launch {
                val zonas: Response<List<Zonas>> = RetrofitConnection.service.getZonas()
                val iterator = zonas.body()!!.iterator()
                while (iterator.hasNext()) {
                    val zona = iterator.next()
                    if (zona.id == data.zona_id) {
                        zonaTextView.text = "Zona: " + zona.nombre
                        break
                    }
                }
            }

            titleTextView.text = data.Incidencia
            descriptionTextView.text = "Descripció: " + data.descripcion
            Picasso.get().load(data.image_url).into(image)

            infoImageView.setOnClickListener {
                val intent = Intent(context, InfoAveria::class.java)
                intent.putExtra("averiaId", data.id)
                context.startActivity(intent)
            }
        }
    }
}
