package com.victormoyano.circuitcatalunya.api

import com.victormoyano.circuitcatalunya.models.Users
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import javax.security.auth.callback.Callback


interface RetrofitEndPoints {

    @GET("test")
    suspend fun test(): Response<List<Users>>

    @GET("login")
    suspend fun getUsers(): Response<List<Users>>
    @GET("verify/{email}/{password}")
    suspend fun verifyCredentials(
        @Path("email") email: String,
        @Path("password") password: String
    ): Response<Boolean>


}