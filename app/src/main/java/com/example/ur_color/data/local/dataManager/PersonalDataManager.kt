package com.example.ur_color.data.local.dataManager

import android.content.Context
import android.graphics.Bitmap
import com.example.ur_color.data.local.storage.AuraStorage
//import com.example.ur_color.data.local.HistoryStorage
import com.example.ur_color.data.local.storage.UserStorage
import com.example.ur_color.data.user.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object PersonalDataManager {
    private var initialized = false

    private val _user = MutableStateFlow<UserData?>(null)
    val user: StateFlow<UserData?> = _user

    private val _aura = MutableStateFlow<Bitmap?>(null)
    val aura: StateFlow<Bitmap?> = _aura

    suspend fun initialize(context: Context) {
        if (initialized) return
        _user.value = UserStorage.load(context)
        _aura.value = AuraStorage.load(context)
        initialized = true
    }

    suspend fun save(context: Context, user: UserData? = null, aura: Bitmap? = null) {
        user?.let {
            UserStorage.save(context, it)
            _user.value = it
        }

        aura?.let {
            AuraStorage.save(context, it)
            _aura.value = it
        }
//        HistoryStorage.save(context, user)
    }

    suspend fun updateUser(context: Context, user: UserData) {
        save(context, user = user)
    }

    suspend fun updateAura(context: Context, bitmap: Bitmap) {
        save(context, aura = bitmap)
    }

    suspend fun delete(context: Context) {
        UserStorage.delete(context)
        AuraStorage.delete(context)
        _user.value = null
        _aura.value = null
        initialized = false
//        HistoryStorage.clear(context)
    }

//    fun loadHistory(context: Context): List<UserData> = HistoryStorage.load(context)
}