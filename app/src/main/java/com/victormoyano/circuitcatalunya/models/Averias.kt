package com.victormoyano.circuitcatalunya.models

data class Averias(
    var id: Int = 0,
    var createdAt: String = "",
    var updatedAt: String = "",
    var Incidencia: String = "",
    var descripcion: String = "",
    var data_inicio: String = "",
    var data_fin: String? = "",
    var prioridad: String = "",
    var imagen: String? = "",
    var creator_id: Int = 0,
    var tecnico_asignado_id: Int = 0,
    var asignador: Int = 0,
    var zona_id: Int = 0,
    var tipo_averias_id: Int = 0,
    var image_url: String = ""
)


