package com.victormoyano.circuitcatalunya.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.victormoyano.circuitcatalunya.R
import com.victormoyano.circuitcatalunya.models.Reparacion

class ReparacionesAdapter(private val context: Context, private val dataList: List<Reparacion>) :
    RecyclerView.Adapter<ReparacionesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.reparacionscard, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]
        holder.bindData(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val topText1: TextView = itemView.findViewById(R.id.topText1)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val subtitleTextView: TextView = itemView.findViewById(R.id.subtitleTextView)
        private val button1: Button = itemView.findViewById(R.id.button1)
        private val button2: Button = itemView.findViewById(R.id.button2)
        private val imageView2: ImageView = itemView.findViewById(R.id.imageView2)

        fun bindData(data: Reparacion) {
            topText1.text = data.topText1
            titleTextView.text = data.title
            subtitleTextView.text = data.subtitle
            button1.text = data.button1Text
            button2.text = data.button2Text
            imageView2.setImageResource(data.imageResource)
        }
    }
}