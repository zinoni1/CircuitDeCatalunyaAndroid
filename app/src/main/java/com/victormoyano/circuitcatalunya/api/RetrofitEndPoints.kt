package com.victormoyano.circuitcatalunya.api

import com.victormoyano.circuitcatalunya.models.Averias
import com.victormoyano.circuitcatalunya.models.Cargos
import com.victormoyano.circuitcatalunya.models.Sectores
import com.victormoyano.circuitcatalunya.models.TipoAverias
import com.victormoyano.circuitcatalunya.models.Users
import com.victormoyano.circuitcatalunya.models.UsersLista
import com.victormoyano.circuitcatalunya.models.Zonas
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*


interface RetrofitEndPoints {

    @GET("test")
    suspend fun getLogin(): Response<List<Users>>

    @GET("test")
    suspend fun getUsers(): Response<List<UsersLista>>
    @GET("verify/{email}/{password}")
    suspend fun verifyCredentials(
        @Path("email") email: String,
        @Path("password") password: String
    ): Number

    @GET("tipo-averias-android")
    suspend fun getTipoAverias(): Response<List<TipoAverias>>

    @GET("averias-android")
    public suspend fun getAverias(): Response<List<Averias>>

    @GET("averias-android/{id}")
    suspend fun getAveria(@Path("id") id: Int): Response<Averias>
    @GET("cargos-android")
    suspend fun getCargos(): Response<List<Cargos>>

    @GET("zonas-android")
    suspend fun getZonas(): Response<List<Zonas>>

    @GET("sector-android")
    suspend fun getSector(): Response<List<Sectores>>

    @POST("add-averia-android")
    suspend fun addAveria(@Body averia: Averias): Response<Averias>

    @Multipart
    @POST("upload-image")
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<Any>

}

