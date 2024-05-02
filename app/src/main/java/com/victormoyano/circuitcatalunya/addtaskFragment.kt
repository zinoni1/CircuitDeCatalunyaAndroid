package com.victormoyano.circuitcatalunya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.Fragment

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

        // Define las opciones para los Spinners
        val tipusMantenimentOptions = arrayOf("Opción 1", "Opción 2", "Opción 3")
        val zonesOptions = arrayOf("Zona 1", "Zona 2", "Zona 3")
        val asignarOptions = arrayOf("Asignar 1", "Asignar 2", "Asignar 3")

        // Crea los adaptadores para los Spinners
        val tipusMantenimentAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipusMantenimentOptions)
        val zonesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, zonesOptions)
        val asignarAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, asignarOptions)

        // Asigna los adaptadores a los Spinners
        tipusMantenimentSpinner.adapter = tipusMantenimentAdapter
        zonesSpinner.adapter = zonesAdapter
        asignarSpinner.adapter = asignarAdapter

        return view
    }
}
