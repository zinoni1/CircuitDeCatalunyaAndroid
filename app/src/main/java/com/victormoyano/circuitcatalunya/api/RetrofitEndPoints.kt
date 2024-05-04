package com.victormoyano.circuitcatalunya.api

import com.victormoyano.circuitcatalunya.models.Averias
import com.victormoyano.circuitcatalunya.models.Cargos
import com.victormoyano.circuitcatalunya.models.Sectores
import com.victormoyano.circuitcatalunya.models.TipoAverias
import com.victormoyano.circuitcatalunya.models.Users
import com.victormoyano.circuitcatalunya.models.UsersLista
import com.victormoyano.circuitcatalunya.models.Zonas
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import javax.security.auth.callback.Callback


interface RetrofitEndPoints {

    @GET("test")
    suspend fun getLogin(): Response<List<Users>>

    @GET("test")
    suspend fun getUsers(): Response<List<UsersLista>>
    @GET("verify/{email}/{password}")
    suspend fun verifyCredentials(
        @Path("email") email: String,
        @Path("password") password: String
    ): Response<Boolean>

    @GET("tipo-averias-android")
    suspend fun getTipoAverias(): Response<List<TipoAverias>>

    @GET("averias-android")
    public suspend fun getAverias(): Response<List<Averias>>

    @GET("cargos-android")
    suspend fun getCargos(): Response<List<Cargos>>

    @GET("zonas-android")
    suspend fun getZonas(): Response<List<Zonas>>

    @GET("sector-android")
    suspend fun getSector(): Response<List<Sectores>>

}

