package com.victormoyano.circuitcatalunya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victormoyano.circuitcatalunya.adapters.ReparacionesAdapter
import com.victormoyano.circuitcatalunya.models.Reparacion

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ReparacionesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Populate the list with Reparacion objects
        val myDataset = listOf(
            Reparacion("SEGÜENT REPARACIÓN", "ZONA: CAMBI DE TECPRO", "MANTENIMIENTO PREVENTIVO", "POSPOSAR", "ASSIGNAR", R.drawable.info),
            Reparacion("SEGÜENT REPARACIÓN", "ZONA: CAMBI DE TECPRO", "MANTENIMIENTO PREVENTIVO", "POSPOSAR", "ASSIGNAR", R.drawable.info)
            // Add more Reparacion objects here...
        )

        viewManager = LinearLayoutManager(context)
        viewAdapter = ReparacionesAdapter(requireContext(), myDataset)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }
}