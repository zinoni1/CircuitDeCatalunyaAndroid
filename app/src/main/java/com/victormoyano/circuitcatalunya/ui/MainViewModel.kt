package com.victormoyano.circuitcatalunya.ui


import androidx.lifecycle.ViewModel
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    fun listUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitConnection.service.test()// URL del endpoint listUsers
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

}