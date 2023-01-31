package com.example.photogenerator.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://api.thecatapi.com/v1/images/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CatApiService {
    @GET("search")
    suspend fun getCat(): List<CatProperty>

}

object CatApi {
    val retrofitService : CatApiService by lazy {
        retrofit.create(CatApiService::class.java) }
}