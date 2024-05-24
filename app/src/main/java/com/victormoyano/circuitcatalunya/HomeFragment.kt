package com.victormoyano.circuitcatalunya

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victormoyano.circuitcatalunya.adapters.ReparacionesAdapter
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ReparacionesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        CoroutineScope(Dispatchers.Main).launch {

            val averias = RetrofitConnection.service.getAverias()
            Log.d("Averias", averias.body()!!.toString())

        viewManager = LinearLayoutManager(context)
        viewAdapter = ReparacionesAdapter(requireContext(), averias)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
    return view
}

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            val averias = RetrofitConnection.service.getAverias()

            Log.d("Averias 2", averias.body()!!.toString())
            viewAdapter = ReparacionesAdapter(requireContext(), averias)
            viewAdapter.notifyDataSetChanged()

        }
    }
}