package com.example.ur_color.data.local.dataManager

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AppDataManager {

    suspend fun initialize(context: Context) = withContext(Dispatchers.IO) {
        SystemDataManager.initialize(context)
    }

    suspend fun clearAll(context: Context) = withContext(Dispatchers.IO) {
        SystemDataManager.clear(context)
        PersonalDataManager.delete(context)
    }
}