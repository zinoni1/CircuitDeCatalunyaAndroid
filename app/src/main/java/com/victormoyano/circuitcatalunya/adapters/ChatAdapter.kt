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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatAdapter(private val context: Context, var response: List<Chat>, private val groups: List<Grups>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.reparacionscard, parent, false)
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
        private val messageTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val idTextView: TextView = itemView.findViewById(R.id.subtitleTextView)
        private val infoImageView: View = itemView.findViewById(R.id.info)

        fun bindData(group: Grups) {
            messageTextView.text = "Grupo: ${group.id_grupo}"
            idTextView.text = "${group.ultimo_mensaje}"

            // Asignar el clic del icono a la actividad del chat
            infoImageView.setOnClickListener {
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("idGrupo", group.id_grupo)
                intent.putExtra("idRebut", group.id_recibido) // Pasar el id_rebut al ChatActivity
                context.startActivity(intent)
            }
            }
        }
}