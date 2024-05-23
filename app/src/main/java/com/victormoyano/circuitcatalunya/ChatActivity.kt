package com.victormoyano.circuitcatalunya

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.victormoyano.circuitcatalunya.adapters.ChatAdapter
import com.victormoyano.circuitcatalunya.adapters.DinsChatAdapter
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: DinsChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat)

        recyclerView = findViewById(R.id.recyclerViewChat)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val userId = HomeActivity.IdLogatHolder.getIdLogat() // Obtener el ID del usuario actual

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val idUser = HomeActivity.IdLogatHolder.getIdLogat()
                val chatsResponse = RetrofitConnection.service.getChats(idUser)
                val idGrup = intent.getIntExtra("idGrupo", 0)
                Log.d("ChatActivity", "idGrup: $idGrup")
                val GrupsChatResponse = RetrofitConnection.service.getChatGrup(idGrup, idUser)
                Log.d("ChatActivity", "GrupsChatResponse: $GrupsChatResponse")
                if (chatsResponse.isSuccessful && GrupsChatResponse.isSuccessful) {
                    val GrupsChat = GrupsChatResponse.body()

                    // Aquí puedes procesar los datos obtenidos y crear la lista de chatMessages
                    val chatMessages = mutableListOf<ChatMessage>()

                    GrupsChat?.forEach { chat ->
                        chatMessages.add(
                            ChatMessage(
                                chat.missatge,
                                chat.id_enviat,
                                chat.id_enviat == idUser // Comprueba si el mensaje fue enviado por el usuario actual
                            )
                        )
                    }

                    chatAdapter = DinsChatAdapter(chatMessages, userId)
                    recyclerView.adapter = chatAdapter
                } else {
                    // Manejar la respuesta fallida, si es necesario
                }
            } catch (e: Exception) {
                // Manejar la excepción, si ocurre
                e.printStackTrace()
            }
        }
    }
}

data class ChatMessage(val content: String, val senderId: Int, val sentByCurrentUser: Boolean)
data class Group(val groupId: Int, val lastMessage: String)