package com.example.ur_color.data.di

import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.remote.AuthApi
import com.example.ur_color.data.remote.AuthInterceptor
import com.example.ur_color.data.remote.ContentApi
import com.example.ur_color.data.remote.UserApi
import com.example.ur_color.utils.logDebug
import kotlinx.coroutines.runBlocking
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

    single {
        AuthInterceptor {
            runBlocking {
                PersonalDataManager.getTokenFromCache()
            }
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(logging)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://feel-u.ru/api/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(ContentApi::class.java) }
    single { get<Retrofit>().create(UserApi::class.java) }
    single { get<Retrofit>().create(AuthApi::class.java) }
}