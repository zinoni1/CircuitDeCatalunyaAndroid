package com.victormoyano.circuitcatalunya

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victormoyano.circuitcatalunya.adapters.ChatAdapter
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import com.victormoyano.circuitcatalunya.models.Chat
import com.victormoyano.circuitcatalunya.models.Grups
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class chatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var context: Context

    companion object {
        const val CHAT_REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewChat)
        context = requireContext()
        val btnAfegirGrup: Button = view.findViewById(R.id.btn_AfegirGrup)
        btnAfegirGrup.setOnClickListener {
            val intent = Intent(context, AddGrupo::class.java)
            startActivity(intent)
        }

        loadChats()

        return view
    }

    override fun onResume() {
        super.onResume()
        // Llama a la función para cargar los datos de los chats cada vez que el fragmento se muestra
        loadChats()
    }

    private fun loadChats() {
        CoroutineScope(Dispatchers.Main).launch {
            val idUser = HomeActivity.IdLogatHolder.getIdLogat()
            val chatsResponse: List<Chat> = RetrofitConnection.service.getChatsList(idUser)
            val grups: List<Grups> = RetrofitConnection.service.getGrupsList(idUser)

            viewManager = LinearLayoutManager(context)
            chatAdapter = ChatAdapter(requireContext(), chatsResponse, grups)

            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = chatAdapter
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHAT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadChats()
        }
    }
}
