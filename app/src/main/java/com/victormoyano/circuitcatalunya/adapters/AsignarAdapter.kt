package com.victormoyano.circuitcatalunya.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.victormoyano.circuitcatalunya.models.UsersLista

class AsignarAdapter(context: Context, resource: Int, private val asignarList: List<UsersLista>) : ArrayAdapter<UsersLista>(context, resource, asignarList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = asignarList[position].name // Mostrar el nombre del elemento asignado
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = asignarList[position].name // Mostrar el nombre del elemento asignado
        return view
    }
}
