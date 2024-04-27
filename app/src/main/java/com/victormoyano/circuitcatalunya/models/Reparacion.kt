package com.victormoyano.circuitcatalunya.models

import com.victormoyano.circuitcatalunya.R

data class Reparacion(
    val topText1: String,
    val title: String,
    val subtitle: String,
    val button1Text: String,
    val button2Text: String,
    val imageResource: Int
)
val myDataset = listOf(
    Reparacion("SEGÜENT REPARACIÓN", "ZONA: CAMBI DE TECPRO", "MANTENIMIENTO PREVENTIVO", "POSPOSAR", "ASSIGNAR", R.drawable.info),
    Reparacion("SEGÜENT REPARACIÓN", "ZONA: CAMBI DE TECPRO", "MANTENIMIENTO PREVENTIVO", "POSPOSAR", "ASSIGNAR", R.drawable.info),
    // Agrega más objetos Reparacion aquí...
)