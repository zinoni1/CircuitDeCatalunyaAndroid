package com.victormoyano.circuitcatalunya

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class calendarioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendario, container, false)

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            // Build and show a dialog for adding an event
            showAddEventDialog(selectedDate)
        }

        return view
    }

    private fun showAddEventDialog(selectedDate: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Event")
        val input = EditText(requireContext())
        builder.setView(input)
        builder.setPositiveButton("Add") { dialog, which ->
            val eventName = input.text.toString()
            // Here you can add code to save the event to a database or perform any other action
            Toast.makeText(requireContext(), "Event '$eventName' added on $selectedDate", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }
}
