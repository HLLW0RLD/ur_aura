package com.example.ur_color.data.local.dataManager

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AppDataManager {

    suspend fun initialize() = withContext(Dispatchers.IO) {
        SystemDataManager.initialize()
    }

    suspend fun clearAll() = withContext(Dispatchers.IO) {
        SystemDataManager.clear()
        PersonalDataManager.delete()
    }
}