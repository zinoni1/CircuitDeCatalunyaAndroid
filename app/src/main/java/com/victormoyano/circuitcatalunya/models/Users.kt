package com.victormoyano.circuitcatalunya.models

import java.sql.Time

data class Users(
    var email: String,
    var password: String
)
data class UsersLista(
    var id: Int = 0,
    var name : String = "",
    var email: String = "",
    var password: String = "",
    var remember_token: String  = "",
    val profile_photo_path: String = "",
    val rol: String = "",
    val cargo_id: Int = 1,
    val created_at: String = "",
    val updated_at: String = ""
)
