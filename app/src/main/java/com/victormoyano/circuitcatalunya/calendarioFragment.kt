package com.victormoyano.circuitcatalunya

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.CalendarConstraints;
import android.util.Pair;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle
import android.text.Selection.setSelection
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.victormoyano.circuitcatalunya.adapters.IncidenciaCalendarAdapter
import com.victormoyano.circuitcatalunya.api.RetrofitConnection
import com.victormoyano.circuitcatalunya.models.Averias
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class calendarioFragment : Fragment() {
    private lateinit var supportFragmentManager: FragmentActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendario, container, false)
        MainActivity().mostrarToastPersonalizado(requireContext(), "Selecciona el día per veure les incidències")

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
              calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            // Build and show a dialog for adding an event

            showIncidenciesDia(selectedDate)
            Log.d("CalendarioFragment", "Selected date: $selectedDate")
        }

        return view
    }

    private fun showIncidenciesDia(selectedDate: String) {
        // Aquí llamarías a tu API para obtener las averías del día seleccionado
        obtenerAveriasDelDia(selectedDate) { averiasDelDia ->
            // Configurar el RecyclerView con el adaptador
            val recycler = view?.findViewById<RecyclerView>(R.id.rvCards)
            Log.d("Averiasdfa 1", averiasDelDia.toString())
            if(averiasDelDia.isEmpty()){
                MainActivity().mostrarToastPersonalizado(requireContext(), "No hi ha incidències per el día seleccionat")
            }
            recycler?.adapter = IncidenciaCalendarAdapter(requireContext(), averiasDelDia)
        }
    }

    private fun obtenerAveriasDelDia(selectedDate: String, callback: (List<Averias>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val averiasResponse = RetrofitConnection.service.getAverias()
            Log.d("Averias 2", averiasResponse.body().toString())

            val sdfInput = SimpleDateFormat("yyyy-MM-dd")
            val sdfOutput = SimpleDateFormat("d/M/yyyy")

            val averias = averiasResponse.body()!!.map { averia ->
                // Convertir el formato de fecha de "yyyy-MM-dd" a "d/M/yyyy"
                val date = sdfInput.parse(averia.data_inicio)
                val nuevaFechaInicio = sdfOutput.format(date)
                // Crear un nuevo objeto Averias con la fecha modificada
                averia.copy(data_inicio = nuevaFechaInicio)
            }
            Log.d("Averias 13", selectedDate)
            Log.d("Averias 23", averias.toString())
            val averiasDelDia = averias.filter { averia -> averia.data_inicio == selectedDate }
            Log.d("Averias 33", averiasDelDia.toString())
            callback(averiasDelDia)
        }
    }
}
