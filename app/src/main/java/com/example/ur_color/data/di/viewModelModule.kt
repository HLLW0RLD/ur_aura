package com.example.ur_color.data.di

import com.example.ur_color.ui.screen.viewModel.LoginViewModel
import com.example.ur_color.ui.screen.viewModel.MainViewModel
import com.example.ur_color.ui.screen.viewModel.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel { ProfileViewModel() }
    viewModel { MainViewModel(get()) }
    viewModel { LoginViewModel(/*get()*/) }

}