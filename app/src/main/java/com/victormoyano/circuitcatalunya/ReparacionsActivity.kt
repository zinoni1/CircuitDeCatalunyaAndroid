package com.victormoyano.circuitcatalunya

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victormoyano.circuitcatalunya.adapters.ReparacionesAdapter

class ReparacionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reparaciones)

        // Referencia al RecyclerView desde el layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Configuración del LinearLayoutManager para mostrar los elementos en una lista vertical
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Creación de un adaptador personalizado y configuración del adaptador en el RecyclerView
        val dataList = listOf("Item 1", "Item 2", "Item 3") // Aquí deberías tener tu lista de datos
        val adapter = ReparacionesAdapter(this, dataList)
        recyclerView.adapter = adapter
    }
}
