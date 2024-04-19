package com.victormoyano.circuitcatalunya

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victormoyano.circuitcatalunya.adapters.ReparacionesAdapter

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Obtener una referencia al RecyclerView desde el layout de HomeActivity
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Configurar un LinearLayoutManager para mostrar los elementos en una lista vertical
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Crear una lista de datos ficticia (puedes reemplazarla con tu propia lista)
        val dataList = listOf("Reparación 1", "Reparación 2", "Reparación 3")

        // Crear una instancia del adaptador ReparacionesAdapter y configurarla en el RecyclerView
        val adapter = ReparacionesAdapter(this, dataList)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

  /*  override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.configuracio -> {
                val intent = Intent(this, ConfiguracioActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }*/
}
