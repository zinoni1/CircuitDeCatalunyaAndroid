package com.victormoyano.circuitcatalunya.ui


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import com.victormoyano.circuitcatalunya.models.Averias
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _AveriasCount = MutableLiveData(0)
    public val averiascount: LiveData<Int> get() = _AveriasCount

    private val _AveriasList = MutableLiveData<List<Averias>>(emptyList())
    public val averiaslist: LiveData<List<Averias>> get() = _AveriasList
    fun listUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitConnection.service.getLogin()// URL del endpoint listUsers
                if (response.isSuccessful) {
                    val users = response.body()
                    // Imprimir el resultado en la consola
                    users?.forEach { user ->
                        println(" Email: ${user.email}")
                    }
                } else {
                    // Manejar el error de la solicitud
                    // Por ejemplo: Log.e("MainViewModel", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                // Manejar errores de red o de otra índole
                // Por ejemplo: Log.e("MainViewModel", "Error: ${e.message}")
            }
        }
    }
    fun listAverias() {
        CoroutineScope(Dispatchers.IO).launch {

            val response = RetrofitConnection.service.getAverias() // Cambiar a tu método de servicio que obtiene la lista de averías

            if (response.isSuccessful) {
                val averias = response.body()
                // Imprimir el resultado en la consola o realizar alguna acción con la lista de averías
                averias?.forEach { averia ->
                    println("ID: ${averia.id}, Descripción: ${averia.descripcion}")
                }
            } else {
                // Manejar el error de la solicitud
                Log.e("MainViewModel", "Error: ${response.code()}")

            }
        }
    }

}