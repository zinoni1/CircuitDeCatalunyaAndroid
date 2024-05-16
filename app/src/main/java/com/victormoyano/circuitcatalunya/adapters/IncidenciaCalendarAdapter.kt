package com.victormoyano.circuitcatalunya.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.victormoyano.circuitcatalunya.R
import com.victormoyano.circuitcatalunya.models.Averias
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IncidenciaCalendarAdapter(private val context: Context, private val averias: List<Averias>) :
    RecyclerView.Adapter<IncidenciaCalendarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_calendari, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = averias[position]
        Log.d("data1",data.toString())
        holder.bindData(data)
    }

    override fun getItemCount(): Int {
        return averias.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.subtitleTextView)
        private val zonaTextView: TextView = itemView.findViewById(R.id.zonaTextView)
        private val image: ImageView = itemView.findViewById(R.id.image)

        fun bindData(data: Averias) {
            CoroutineScope(Dispatchers.Main).launch {
                // Aquí podrías obtener la zona correspondiente si lo necesitas
                zonaTextView.text = "Zona: ${data.zona_id}"
                Log.d("data",data.toString())
                titleTextView.text = data.Incidencia
                descriptionTextView.text = "Descripción: ${data.descripcion}"
                Picasso.get().load(data.image_url).into(image)
            }
        }
    }
}
