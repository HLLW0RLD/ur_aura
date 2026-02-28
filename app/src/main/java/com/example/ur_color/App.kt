package com.example.ur_color

import android.app.Application
import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.security.crypto.MasterKey
import com.example.ur_color.data.di.apiModule
import com.example.ur_color.data.di.appModule
import com.example.ur_color.data.di.dbModule
import com.example.ur_color.data.di.repoModule
import com.example.ur_color.data.di.viewModelModule
import com.example.ur_color.data.local.dataManager.AppDataManager
import com.example.ur_color.data.local.storage.TokenCipher
import com.example.ur_color.utils.logError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import java.security.KeyStore
import javax.crypto.KeyGenerator

class App : Application() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate() {
        super.onCreate()

        _instance  = this

        CoroutineScope(Dispatchers.Default).launch {
            AppDataManager.initialize()
        }

        initTokenCipher()

        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                apiModule,
                dbModule,
                repoModule,
                viewModelModule
            )
        }
    }

    private fun initTokenCipher() {
        try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)

            val entry = keyStore.getEntry("token_encryption_key", null) as? KeyStore.SecretKeyEntry

            val secretKey = entry?.secretKey ?: run {
                val keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore"
                )
                keyGenerator.init(
                    KeyGenParameterSpec.Builder(
                        "token_encryption_key",
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setKeySize(256)
                        .build()
                )
                keyGenerator.generateKey()
            }

            TokenCipher.init(secretKey)

        } catch (e: Exception) {
            logError(e, "Failed to init TokenCipher")
        }
    }

    companion object {
        private var _instance: App? = null
        val instance: App
        get() = _instance!!
    }
}