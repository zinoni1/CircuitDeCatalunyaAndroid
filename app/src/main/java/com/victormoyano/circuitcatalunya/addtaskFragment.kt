package com.victormoyano.circuitcatalunya

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.victormoyano.circuitcatalunya.adapters.AsignarAdapter
import com.victormoyano.circuitcatalunya.adapters.TipusMantenimentAdapter
import com.victormoyano.circuitcatalunya.adapters.ZonasAdapter
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import com.victormoyano.circuitcatalunya.models.Averias
import com.victormoyano.circuitcatalunya.models.TipoAverias
import com.victormoyano.circuitcatalunya.models.UsersLista
import com.victormoyano.circuitcatalunya.models.Zonas
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
    private lateinit var PrioritatSpinner: Spinner
    private lateinit var ButtonEnviar: Button
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
        PrioritatSpinner = view.findViewById(R.id.Prioritat)
        ButtonEnviar = view.findViewById(R.id.btnEnviar)

        // Aquí puedes agregar la lógica para manejar los eventos de los elementos de la interfaz de usuario.
        // Por ejemplo, puedes agregar un OnClickListener al ImageButton para cambiar la imagen cuando se haga clic en él.

        imageButton.setOnClickListener {
            // Cambiar la imagen o hacer algo cuando se haga clic en el ImageButton
        }
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val responseZonas = RetrofitConnection.service.getZonas()
                val responseTipusManteniment = RetrofitConnection.service.getTipoAverias()
                val responseAsignar = RetrofitConnection.service.getUsers()
                val zonasObject = responseZonas.body()
                val tipusMantenimentObject = responseTipusManteniment.body()
                val asignarObject = responseAsignar.body()

                val prioridadOptions = arrayOf("alta", "media", "baja")
                val prioridadAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    prioridadOptions
                )
                PrioritatSpinner.adapter = prioridadAdapter

                // Verificamos si la respuesta no es nula y si contiene la lista de zonas
                if (zonasObject != null && zonasObject.isNotEmpty()) {
                    val zonasAdapter = ZonasAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        zonasObject
                    )
                    zonesSpinner.adapter = zonasAdapter
                } else {
                    // Manejar el caso en el que la respuesta no contiene la lista de zonas esperada
                    Log.e(
                        "addtaskFragment",
                        "La respuesta del servicio no contiene la lista de zonas esperada"
                    )
                }
                Log.d("addtaskFragmentTipus", tipusMantenimentObject.toString())
                if (tipusMantenimentObject != null && tipusMantenimentObject != null) {
                    val tipusMantenimentAdapter = TipusMantenimentAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        tipusMantenimentObject
                    )
                    tipusMantenimentSpinner.adapter = tipusMantenimentAdapter
                } else {
                    // Manejar el caso en el que la respuesta no contiene la lista de zonas esperada
                    Log.e(
                        "addtaskFragment",
                        "La respuesta del servicio no contiene la lista de zonas esperada"
                    )
                }
                Log.d("addtaskFragment", asignarObject.toString())
                if (asignarObject != null && asignarObject != null) {
                    val asignarAdapter = AsignarAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        asignarObject
                    )
                    asignarSpinner.adapter = asignarAdapter
                } else {
                    // Manejar el caso en el que la respuesta no contiene la lista de zonas esperada
                    Log.e(
                        "addtaskFrag",
                        "La respuesta del servicio no contiene la lista de zonas esperada"
                    )
                }
            } catch (e: Exception) {
                // Manejar cualquier error que pueda ocurrir al obtener los datos de Retrofit
                Log.e("addtask", e.toString())
            }
            ButtonEnviar.setOnClickListener {
                val problem = problemEditText.text.toString()
                val description = descriptionEditText.text.toString()
                val tipusManteniment = tipusMantenimentSpinner.selectedItem as TipoAverias
                val zona = zonesSpinner.selectedItem as Zonas
                val asignar = asignarSpinner.selectedItem as UsersLista
                val prioridad = PrioritatSpinner.selectedItem as String
                val fecha_hoy = java.time.LocalDate.now().toString()
                val averia = Averias(
                    Incidencia = problem,
                    descripcion = description,
                    data_inicio = fecha_hoy,
                    data_fin = null,
                    prioridad = prioridad,
                    imagen = null,
                    creator_id = 1,
                    tecnico_asignado_id = asignar.id,
                    asignador = 1,
                    zona_id = zona.id,
                    tipo_averias_id = tipusManteniment.id
                )
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val response = RetrofitConnection.service.addAveria(averia)
                        if (response.isSuccessful) {
                            //poner un toast
                           MainActivity().mostrarToastPersonalizado(requireContext(), "Tarea añadida correctamente")
                            //limpiar campos
                            problemEditText.text.clear()
                            descriptionEditText.text.clear()
                        } else {
                            // toast
                            MainActivity().mostrarToastPersonalizado(requireContext(), "Error al añadir la tarea")
                        }
                    } catch (e: Exception) {
                            MainActivity().mostrarToastPersonalizado(requireContext(), "Error al añadir la tarea")
                    }
                }
            }
        }
        return view
    }
}