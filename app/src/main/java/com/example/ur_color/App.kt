package com.example.ur_color

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ur_color.data.di.apiModule
import com.example.ur_color.data.di.appModule
import com.example.ur_color.data.di.repoModule
import com.example.ur_color.data.di.viewModelModule
import com.example.ur_color.data.local.dataManager.AppDataManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.Default).launch {
            AppDataManager.initialize(this@App)
        }

        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                apiModule,
                repoModule,
                viewModelModule
            )
        }
    }
}