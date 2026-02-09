package com.example.ur_color.data.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ur_color.ui.screen.viewModel.AuraDetailsViewModel
import com.example.ur_color.ui.screen.viewModel.BazarViewModel
import com.example.ur_color.ui.screen.viewModel.CreatePostViewModel
import com.example.ur_color.ui.screen.viewModel.CustomTestViewModel
import com.example.ur_color.ui.screen.viewModel.RegistrationViewModel
import com.example.ur_color.ui.screen.viewModel.MainViewModel
import com.example.ur_color.ui.screen.viewModel.ProfileViewModel
import com.example.ur_color.ui.screen.viewModel.DailyTestViewModel
import com.example.ur_color.ui.screen.viewModel.EditProfileViewModel
import com.example.ur_color.ui.screen.viewModel.LabViewModel
import com.example.ur_color.ui.screen.viewModel.LoginViewModel
import com.example.ur_color.ui.screen.viewModel.OnboardingViewModel
import com.example.ur_color.ui.screen.viewModel.SettingsViewModel
import com.example.ur_color.ui.screen.viewModel.TestConstructorViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
val viewModelModule = module {

    viewModel { RegistrationViewModel() }
    viewModel { LoginViewModel() }
    viewModel { OnboardingViewModel() }
    viewModel { MainViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { EditProfileViewModel(get()) }
    viewModel { AuraDetailsViewModel() }
    viewModel { DailyTestViewModel(get()) }
    viewModel { CustomTestViewModel(get()) }
    viewModel { TestConstructorViewModel() }
    viewModel { BazarViewModel() }
    viewModel { SettingsViewModel() }
    viewModel { LabViewModel() }
    viewModel { CreatePostViewModel(get()) }
}