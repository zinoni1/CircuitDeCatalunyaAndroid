package com.victormoyano.circuitcatalunya

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victormoyano.circuitcatalunya.adapters.AddGrupoAdapter
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddGrupo : AppCompatActivity() {
    private lateinit var adapter: AddGrupoAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_grupo)
        recyclerView = findViewById(R.id.recycler_afegirgrups)
        recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.Main).launch {

            try {
                val idLogat = HomeActivity.IdLogatHolder.getIdLogat()
                if (idLogat != null) {
                    val usersSinChat: List<Int> = withContext(Dispatchers.IO) {
                        RetrofitConnection.service.getUsersSinChat(idLogat)
                    }
                    adapter = AddGrupoAdapter(this@AddGrupo, usersSinChat)
                    recyclerView.adapter = adapter
                } else {
                    // Maneja el caso donde idLogat es null
                    // Por ejemplo, muestra un mensaje de error o redirige al usuario a otra actividad
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Maneja el error, por ejemplo, mostrando un mensaje al usuario
            }
        }
    }
}
