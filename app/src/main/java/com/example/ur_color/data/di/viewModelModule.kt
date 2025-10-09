package com.example.ur_color.data.di

import com.example.ur_color.ui.screen.viewModel.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel { UserViewModel() }

}