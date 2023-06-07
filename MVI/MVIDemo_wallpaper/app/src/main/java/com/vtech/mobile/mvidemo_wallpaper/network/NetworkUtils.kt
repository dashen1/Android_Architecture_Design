package com.vtech.mobile.mvidemo_wallpaper.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkUtils {

    private const val BASE_URL = "http://service.picasso.adesk.com/"

    /**
     * 通过Moshi,将JSON转变为Kotlin的Data class
     */
    private val moshi:Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * Build Retrofit
     */
    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val apiService:ApiService = getRetrofit().create(ApiService::class.java)
}