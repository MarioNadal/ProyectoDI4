package com.iessanalberto.dam2.proyectodi4.data

import com.iessanalberto.dam2.proyectodi4.data.model.ProyectosFP
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {
    @GET("my-resource")
    suspend fun listData(): ProyectosFP
    @POST("my-other-resource")
    suspend fun sendData(
        @Query("queryStringParameters") param: String = "Hola",
        @Body data: ProyectosFP
    ): Response<ProyectosFP>

    companion object

}

object RetrofitServiceFactory{
    fun makeRetrofitService(): RetrofitService{
        return Retrofit.Builder()
            .baseUrl("https://ppsy752wr5.execute-api.us-east-1.amazonaws.com/dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}