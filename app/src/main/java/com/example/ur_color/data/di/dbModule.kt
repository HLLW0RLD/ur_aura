package com.example.ur_color.data.di

import androidx.room.Room
import com.example.ur_color.data.local.db.PostDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            PostDatabase::class.java,
            "posts.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<PostDatabase>().postDao() }
}