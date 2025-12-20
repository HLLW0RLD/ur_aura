package com.example.ur_color.data.di

import com.example.ur_color.data.remote.HoroscopeApi
import com.example.ur_color.data.repo.HoroscopeRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    single {
        Retrofit.Builder()
            .baseUrl("https://horoscope-app-api.vercel.app/api/v1/") // ← замени на реальный базовый URL
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(HoroscopeApi::class.java) }
}