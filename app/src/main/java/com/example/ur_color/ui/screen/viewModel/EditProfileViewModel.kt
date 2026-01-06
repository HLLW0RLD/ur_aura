package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.repo.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val userRepository: UserRepository
): BaseViewModel() {

    private val _user = MutableStateFlow<UserData?>(null)
    val user = _user.asStateFlow()

    private val _avatar = MutableStateFlow<String?>(null)
    val avatar = _avatar.asStateFlow()

    private val _about = MutableStateFlow<String?>(null)
    val about = _about.asStateFlow()

    fun init(context: Context) {
        viewModelScope.launch {
            _user.value = userRepository.getUser(context)
        }
    }

    fun setAbout(about: String) {
        _about.value = about
    }

    fun setAvatar(avatar: String) {
        _avatar.value = avatar
    }

    fun update(context: Context) {
        viewModelScope.launch {
            PersonalDataManager.updateUser(context, about.value, avatar.value)
        }
    }
}