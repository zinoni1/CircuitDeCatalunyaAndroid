package com.victormoyano.circuitcatalunya

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [statsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class statsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stats, container, false)

        // Obtiene una referencia al TextView
        val textView5: TextView = view.findViewById(R.id.tvPendents)
        val textView6: TextView = view.findViewById(R.id.tvProgramades)
        val textView7: TextView = view.findViewById(R.id.tvUrgents)
        CoroutineScope(Dispatchers.Main).launch {
            val idLogat = HomeActivity.IdLogatHolder.getIdLogat()
            if (idLogat != null) {
                    val averiasPendientes: Int = RetrofitConnection.service.getAveriesPendents()
                    textView5.text = averiasPendientes.toString()
                    val averiasProgramades: Int = RetrofitConnection.service.getAveriesProgramades()
                    textView6.text = averiasProgramades.toString()
                    val averiasUrgents: Int = RetrofitConnection.service.getAveriesUrgents()
                    textView7.text = averiasUrgents.toString()
            }
        }

        return view
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment statsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            statsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}