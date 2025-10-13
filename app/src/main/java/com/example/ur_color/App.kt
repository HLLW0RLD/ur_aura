package com.example.ur_color

import android.app.Application
import com.example.ur_color.data.di.apiModule
import com.example.ur_color.data.di.appModule
import com.example.ur_color.data.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                apiModule,
                viewModelModule
            )
        }
    }
}