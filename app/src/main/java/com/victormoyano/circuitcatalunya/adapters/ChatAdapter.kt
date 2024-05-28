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
import com.victormoyano.circuitcatalunya.R
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import com.victormoyano.circuitcatalunya.models.Chat
import com.victormoyano.circuitcatalunya.models.Grups
import com.victormoyano.circuitcatalunya.models.UsersGrups
import com.victormoyano.circuitcatalunya.models.UsersLista
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ChatAdapter(private val context: Context, var response: List<Chat>, private val groups: List<Grups>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cards_grups_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = groups[position]
        holder.bindData(group)
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.titolGrup)
        private val idTextView: TextView = itemView.findViewById(R.id.ultim_miss)
        private  val card_grups: View = itemView.findViewById(R.id.card_grups)

        fun bindData(group: Grups) {

            CoroutineScope(Dispatchers.Main).launch {
                val idUser = HomeActivity.IdLogatHolder.getIdLogat()
                val usersGrup: List<UsersGrups> = RetrofitConnection.service.getusersGrup(idUser)
                val users: Response<List<UsersLista>> = RetrofitConnection.service.getUsers()
                val iterator = usersGrup.iterator()
                while (iterator.hasNext()) {
                    val user = iterator.next()
                    if (user.id_grupo == group.id_recibido ) {
                        for(usuari in users.body()!!){
                            if(user.id_usuario == usuari.id){
                                messageTextView.text = usuari.name
                                break
                            }
                    }
                }
            }

            idTextView.text = "${group.ultimo_mensaje}"

            // Asignar el clic del icono a la actividad del chat
            card_grups.setOnClickListener {
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("idGrupo", group.id_grupo)
                intent.putExtra("idRebut", group.id_recibido) // Pasar el id_rebut al ChatActivity
                context.startActivity(intent)
            }
            }
        }
}}