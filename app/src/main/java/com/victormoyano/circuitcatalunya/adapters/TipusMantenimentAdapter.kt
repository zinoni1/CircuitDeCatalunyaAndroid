package com.victormoyano.circuitcatalunya.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.victormoyano.circuitcatalunya.models.TipoAverias

class TipusMantenimentAdapter(context: Context, resource: Int, private val tipusmanteniment: List<TipoAverias>) : ArrayAdapter<TipoAverias>(context, resource, tipusmanteniment) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        Log.d("ZonasAdapter", tipusmanteniment[position].nombre)
        textView.text = tipusmanteniment[position].nombre // Mostrar la descripción en lugar del nombre
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = tipusmanteniment[position].nombre // Mostrar la descripción en lugar del nombre
        return view
    }
}
