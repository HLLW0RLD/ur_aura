package com.example.ur_color.data.di

import com.example.ur_color.data.repo.HoroscopeRepository
import com.example.ur_color.data.repo.PostRepository
import com.example.ur_color.data.repo.UserRepository
import org.koin.dsl.module

val repoModule = module {
//    single { HoroscopeRepository(get()) }
    single { UserRepository(get()) }
    single { PostRepository(get()) }
}