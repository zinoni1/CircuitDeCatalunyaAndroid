package com.victormoyano.circuitcatalunya.models

import java.time.LocalDateTime

data class Chat(
    var id: Int = 0,
    var createdAt: String = "",
    var updatedAt: String = "",
    var missatge: String = "",
    var id_enviat: Int = 1,
    var id_rebut: Int = 1,
    var id_grupo: Int = 0
)
