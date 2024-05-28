package com.victormoyano.circuitcatalunya

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victormoyano.circuitcatalunya.adapters.DinsChatAdapter
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: DinsChatAdapter
    private val chatMessages = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat)

        recyclerView = findViewById(R.id.recyclerViewChat)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val userId = HomeActivity.IdLogatHolder.getIdLogat() // Obtener el ID del usuario actual

        CoroutineScope(Dispatchers.Main).launch {
            val enviarBtn: Button = findViewById(R.id.button3)
            val missatge: EditText = findViewById(R.id.editTextText)

            try {
                val idUser = HomeActivity.IdLogatHolder.getIdLogat()
                val Rebut = intent.getIntExtra("idRebut", 0)
                val idGrup = intent.getIntExtra("idGrupo", 0)
                Log.d("ChatActivity", "idGrup: $idGrup")
                val GrupsChatResponse = RetrofitConnection.service.getChatGrup(idGrup, idUser)
                Log.d("ChatActivity", "GrupsChatResponse: $GrupsChatResponse")

                enviarBtn.setOnClickListener {
                    val message = missatge.text.toString()
                    if (message.isNotEmpty()) {
                        val chatMessage = com.victormoyano.circuitcatalunya.adapters.ChatMessage(
                            id_grupo = idGrup,
                            id_enviat = idUser,
                            id_rebut = Rebut,
                            missatge = message
                        )
                        CoroutineScope(Dispatchers.Main).launch {
                            RetrofitConnection.service.enviarMiss(chatMessage)
                            Log.d("ChatActivity", "Missatge enviat $chatMessage")
                            missatge.text.clear()

                            // Añadir el mensaje a la lista y notificar al adaptador
                            chatMessages.add(
                                ChatMessage(
                                    message,
                                    idUser,
                                    true // El mensaje fue enviado por el usuario actual
                                )
                            )
                            chatAdapter.notifyItemInserted(chatMessages.size - 1)
                            recyclerView.scrollToPosition(chatMessages.size - 1)

                            // Set result to inform that new message is sent
                            setResult(Activity.RESULT_OK)
                        }
                    }
                }

                if (GrupsChatResponse.isSuccessful) {
                    val GrupsChat = GrupsChatResponse.body()

                    GrupsChat?.forEach { chat ->
                        chatMessages.add(
                            ChatMessage(
                                chat.missatge,
                                chat.id_enviat,
                                chat.id_enviat == idUser // Comprueba si el mensaje fue enviado por el usuario actual
                            )
                        )
                    }

                    chatAdapter = DinsChatAdapter(chatMessages, userId, idGrup)
                    recyclerView.adapter = chatAdapter
                    recyclerView.scrollToPosition(chatMessages.size - 1)
                } else {
                    // Manejar la respuesta fallida, si es necesario
                }
            } catch (e: Exception) {
                // Manejar la excepción, si ocurre
                e.printStackTrace()
            }
        }
    }

    override fun onBackPressed() {
        // Set result to inform that user is going back
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }
}

data class ChatMessage(val content: String, val senderId: Int, val sentByCurrentUser: Boolean)
