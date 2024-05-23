package com.victormoyano.circuitcatalunya

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victormoyano.circuitcatalunya.adapters.ChatAdapter
import com.victormoyano.circuitcatalunya.adapters.ReparacionesAdapter
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import com.victormoyano.circuitcatalunya.models.Chat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class chatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewChat)

        CoroutineScope(Dispatchers.Main).launch {
            val idUser = HomeActivity.IdLogatHolder.getIdLogat()
            val chats = RetrofitConnection.service.getChats(idUser);
            val chatsResponse: Response<List<Chat>> = RetrofitConnection.service.getChats(idUser)
            val chatsss = chatsResponse.body()
            val uniqueGroupIds = chatsss?.map { it.id_grupo }?.distinct()
            Log.d("Chats", chats.body()!!.toString())
            Log.d("Unique", uniqueGroupIds.toString())
            viewManager = LinearLayoutManager(context)
            chatAdapter = ChatAdapter(requireContext(), chats, uniqueGroupIds ?: emptyList())

            recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewChat).apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = chatAdapter
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        // Llama a la función para cargar los datos de los chats cada vez que el fragmento se muestra
        loadChats()
    }

    private fun loadChats() {
        val userId = HomeActivity.IdLogatHolder.getIdLogat()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response: Response<List<Chat>> = RetrofitConnection.service.getChats(userId)
                if (response.isSuccessful) {
                    val chatResponse = response // Asignar directamente la respuesta
                    if (chatResponse.body() != null) {
                        // Actualizar el adaptador con los datos obtenidos
                        chatAdapter.response = chatResponse
                        chatAdapter.notifyDataSetChanged()
                    } else {
                        // Manejar el caso en que el cuerpo de la respuesta sea nulo
                    }
                } else {
                    // Manejar errores de respuesta no exitosa
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Manejar errores de red u otros errores
            }
        }
    }
}
