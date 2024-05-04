package com.victormoyano.circuitcatalunya

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.victormoyano.circuitcatalunya.adapters.AsignarAdapter
import com.victormoyano.circuitcatalunya.adapters.TipusMantenimentAdapter
import com.victormoyano.circuitcatalunya.adapters.ZonasAdapter
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class addtaskFragment : Fragment() {

    private lateinit var imageButton: ImageButton
    private lateinit var problemEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var tipusMantenimentSpinner: Spinner
    private lateinit var zonesSpinner: Spinner
    private lateinit var asignarSpinner: Spinner
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_addtask, container, false)

        imageButton = view.findViewById(R.id.imageButton)
        problemEditText = view.findViewById(R.id.problem)
        descriptionEditText = view.findViewById(R.id.description)
        tipusMantenimentSpinner = view.findViewById(R.id.TipusManteniment)
        zonesSpinner = view.findViewById(R.id.Zones)
        asignarSpinner = view.findViewById(R.id.Asignar)

        // Aquí puedes agregar la lógica para manejar los eventos de los elementos de la interfaz de usuario.
        // Por ejemplo, puedes agregar un OnClickListener al ImageButton para cambiar la imagen cuando se haga clic en él.

        imageButton.setOnClickListener {
            // Cambiar la imagen o hacer algo cuando se haga clic en el ImageButton
        }
Log.d("addtaskFragment", "onCreateView")
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val responseZonas = RetrofitConnection.service.getZonas()
                val responseTipusManteniment = RetrofitConnection.service.getTipoAverias()
                val responseAsignar = RetrofitConnection.service.getUsers()
                val zonasObject = responseZonas.body()
               val tipusMantenimentObject = responseTipusManteniment.body()
                val asignarObject = responseAsignar.body()

                // Verificamos si la respuesta no es nula y si contiene la lista de zonas
                if (zonasObject != null && zonasObject.isNotEmpty()) {
                    val zonasAdapter = ZonasAdapter(requireContext(), android.R.layout.simple_spinner_item, zonasObject)
                    zonesSpinner.adapter = zonasAdapter
                } else {
                    // Manejar el caso en el que la respuesta no contiene la lista de zonas esperada
                    Log.e("addtaskFragment", "La respuesta del servicio no contiene la lista de zonas esperada")
                }
                Log.d("addtaskFragmentTipus", tipusMantenimentObject.toString())
                if (tipusMantenimentObject != null && tipusMantenimentObject != null) {
                    val tipusMantenimentAdapter = TipusMantenimentAdapter(requireContext(), android.R.layout.simple_spinner_item, tipusMantenimentObject)
                    tipusMantenimentSpinner.adapter = tipusMantenimentAdapter
                } else {
                    // Manejar el caso en el que la respuesta no contiene la lista de zonas esperada
                    Log.e("addtaskFragment", "La respuesta del servicio no contiene la lista de zonas esperada")
                }
                Log.d("addtaskFragment", asignarObject.toString())
                if (asignarObject != null && asignarObject != null) {
                   val asignarAdapter = AsignarAdapter(requireContext(), android.R.layout.simple_spinner_item, asignarObject)
                  asignarSpinner.adapter = asignarAdapter
                } else {
                    // Manejar el caso en el que la respuesta no contiene la lista de zonas esperada
                   Log.e("addtaskFrag", "La respuesta del servicio no contiene la lista de zonas esperada")
              }
            } catch (e: Exception) {
                // Manejar cualquier error que pueda ocurrir al obtener los datos de Retrofit
               Log.e("addtask", e.toString())
            }
        }
        return view
    }
}