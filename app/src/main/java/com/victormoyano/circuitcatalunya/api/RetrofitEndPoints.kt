package com.victormoyano.circuitcatalunya.api

import com.victormoyano.circuitcatalunya.adapters.ChatMessage
import com.victormoyano.circuitcatalunya.models.Averias
import com.victormoyano.circuitcatalunya.models.Cargos
import com.victormoyano.circuitcatalunya.models.Chat
import com.victormoyano.circuitcatalunya.models.Grups
import com.victormoyano.circuitcatalunya.models.Sectores
import com.victormoyano.circuitcatalunya.models.TipoAverias
import com.victormoyano.circuitcatalunya.models.Users
import com.victormoyano.circuitcatalunya.models.UsersGrups
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
    suspend fun getAveria(@Path("id") id: Int): Response<List<Averias>>

    @GET("chats-android/{id}")
    suspend fun getChats(@Path("id") id: Int): Response<List<Chat>>


    @GET("grupos-android/{id}")
    suspend fun getGrups(@Path("id") id: Int): Response<List<Grups>>

    @GET("chats-android/{id}")
    suspend fun getChatsList(@Path("id") id: Int): List<Chat>

    @GET("grupos-android/{id}")
    suspend fun getGrupsList(@Path("id") id: Int): List<Grups>

    @GET("chats-android/{idGrup}/{id}")
    suspend fun getChatGrup(
        @Path("idGrup") idGrup: Int,
        @Path("id") id: Int): Response<List<Chat>>

    @GET("chats-android/{idGrup}/{id}")
    suspend fun getChatGrupList(
        @Path("idGrup") idGrup: Int,
        @Path("id") id: Int): List<Chat>

    @GET("get-ultim-grup/{id}")
    suspend fun getNouGrup(
        @Path("id") id: Int): Int
    @GET("get-users-grup/{id}")
    suspend fun getusersGrup(
        @Path("id") id: Int): List<UsersGrups>


    @PUT("change-password/{id}/{pass}")
    suspend fun changePass(
        @Path("id") id: Int,
        @Path("pass") password: String): Int

    @GET("get-users-sinchat/{id}")
    suspend fun getUsersSinChat(
        @Path("id") id: Int): List<Int>

    @GET("averias-pendents")
    suspend fun getAveriesPendents(): Int
    @GET("averias-programades")
    suspend fun getAveriesProgramades(): Int
    @GET("averias-urgents")
    suspend fun getAveriesUrgents(): Int

    @POST("enviar-miss-android")
    suspend fun enviarMiss(@Body chat: ChatMessage): Response<Chat>
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

