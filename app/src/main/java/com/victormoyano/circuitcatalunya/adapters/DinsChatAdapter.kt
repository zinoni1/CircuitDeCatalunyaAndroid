package com.victormoyano.circuitcatalunya.adapters

import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.victormoyano.circuitcatalunya.ChatMessage
import com.victormoyano.circuitcatalunya.HomeActivity
import com.victormoyano.circuitcatalunya.InfoAveria
import com.victormoyano.circuitcatalunya.R
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DinsChatAdapter(private val chatMessages: List<ChatMessage>, private val userId: Int) :
    RecyclerView.Adapter<DinsChatAdapter.DinsChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DinsChatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layoutId = if (viewType == MessageType.SENT.ordinal)
            R.layout.message_card_jo
        else
            R.layout.message_card
        val view = inflater.inflate(layoutId, parent, false)
        return DinsChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: DinsChatViewHolder, position: Int) {
        val chatMessage = chatMessages[position]
        holder.bind(chatMessage)
    }

    override fun getItemViewType(position: Int): Int {
        val chatMessage = chatMessages[position]
        return if (chatMessage.senderId == userId) MessageType.SENT.ordinal else MessageType.RECEIVED.ordinal
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    class DinsChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.missatge)
        private val nom: TextView = itemView.findViewById(R.id.nomPersona)

        fun bind(chatMessage: ChatMessage) {
            messageTextView.text = chatMessage.content
            CoroutineScope(Dispatchers.Main).launch {
                val users = RetrofitConnection.service.getUsers()
                val iterator = users.body()!!.iterator()
                while (iterator.hasNext()) {
                    val user = iterator.next()
                    if (user.id == chatMessage.senderId) {
                        nom.text = user.name
                        break
                    }
                }

            }
        }
    }
        enum class MessageType {
            SENT,
            RECEIVED
        }
    }

