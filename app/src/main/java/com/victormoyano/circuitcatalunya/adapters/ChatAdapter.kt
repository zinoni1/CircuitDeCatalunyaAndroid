package com.victormoyano.circuitcatalunya.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.victormoyano.circuitcatalunya.ChatActivity
import com.victormoyano.circuitcatalunya.HomeActivity
import com.victormoyano.circuitcatalunya.InfoAveria
import com.victormoyano.circuitcatalunya.R
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import com.victormoyano.circuitcatalunya.models.Chat
import com.victormoyano.circuitcatalunya.models.UsersLista
import com.victormoyano.circuitcatalunya.models.Zonas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

// Modificación
class ChatAdapter(private val context: Context, var response: Response<List<Chat>>, private val groups: List<Int>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private val chat: Response<List<Chat>>? = response

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.reparacionscard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        chat?.let {
            holder.bindData(it.body()!![position], groups[position])
        }
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val idTextView: TextView = itemView.findViewById(R.id.subtitleTextView)
        private val infoImageView: View = itemView.findViewById(R.id.info)

        fun bindData(data: Chat, groupId: Int) {
            CoroutineScope(Dispatchers.Main).launch {
                // Obtener el id del usuario logueado
                val idLogat = HomeActivity.IdLogatHolder.getIdLogat()

                // Realizar la solicitud para obtener la lista de usuarios
                val usersResponse = RetrofitConnection.service.getUsers()

                // Obtener la lista de usuarios
                val users = usersResponse.body()

                // Obtener el nombre del usuario receptor del mensaje
                val receiverName = users?.find { it.id == data.id_rebut }?.name

                // Verificar si el remitente del mensaje es el usuario logueado
                if (data.id_enviat == idLogat) {
                    // Si el remitente es el usuario logueado, mostrar el nombre del receptor
                    messageTextView.text = receiverName
                } else {
                    // Si el remitente no es el usuario logueado, mostrar tu nombre
                    val senderName = users?.find { it.id == data.id_enviat }?.name
                    messageTextView.text = senderName
                }
            }

            // Asignar el clic del icono a la actividad del chat
            infoImageView.setOnClickListener {
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("idGrupo", groupId)
                context.startActivity(intent)
            }
        }

    }
}
