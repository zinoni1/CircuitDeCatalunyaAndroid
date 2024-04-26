package com.victormoyano.circuitcatalunya.models

import java.sql.Time

data class Users(
    var email: String,
    var password: String
)
data class UsersLista(
    var name : String,
    var email: String,
    var password: String,
    var remember_token: String,
    val profile_photo_path: String,
    val rol: String,
    val cargo_id: Int,
    val created_at: Time,
    val updated_at: Time
)
