package com.victormoyano.circuitcatalunya.adapters

import android.app.Activity
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
import com.victormoyano.circuitcatalunya.chatFragment
import com.victormoyano.circuitcatalunya.models.UsersLista
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class AddGrupoAdapter(private val context: Context, private var response: List<Int>) :
    RecyclerView.Adapter<AddGrupoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cards_grups_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(response[position])
    }

    override fun getItemCount(): Int {
        return response.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titolGrup)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.ultim_miss)

        fun bindData(data: Int) {
            CoroutineScope(Dispatchers.Main).launch {
                val users: Response<List<UsersLista>> = RetrofitConnection.service.getUsers()
                val iterator = users.body()!!.iterator()
                while (iterator.hasNext()) {
                    val user = iterator.next()
                    if (user.id == data) {
                        titleTextView.text = user.name
                        break
                    }
                }
            }
            descriptionTextView.text = "No chat available"

            itemView.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    val idLogat = HomeActivity.IdLogatHolder.getIdLogat()
                    val nouGrupId: Int = RetrofitConnection.service.getNouGrup(idLogat)

                    val chatMessage = com.victormoyano.circuitcatalunya.adapters.ChatMessage(
                        id_grupo = nouGrupId,
                        id_enviat = HomeActivity.IdLogatHolder.getIdLogat()!!,
                        id_rebut = data,
                        missatge = "Grup Creat"
                    )
                    RetrofitConnection.service.enviarMiss(chatMessage)
                    Log.d("AddGrupo", "Missatge nou enviat $chatMessage")

                    val intent = Intent(context, ChatActivity::class.java).apply {
                        putExtra("idRebut", data)
                        putExtra("idGrupo", nouGrupId)
                    }
                    (context as Activity).startActivityForResult(intent, chatFragment.CHAT_REQUEST_CODE)
                }
            }
        }
    }

    fun updateData(newData: List<Int>) {
        response = newData
        notifyDataSetChanged()
    }
}
