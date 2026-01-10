package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.repo.UserRepository
import com.example.ur_color.utils.logDebug
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

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
            val loadedUser = userRepository.getUser(context)
            logDebug("${loadedUser?.avatarUri}")
            _user.value = loadedUser
            _about.value = loadedUser?.about
            _avatar.value = loadedUser?.avatarUri
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

    // ддля оттправккки на бэк
    private fun uriToBitmap(
        context: Context,
        uri: Uri
    ): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    }

    private fun bitmapToBase64(
        bitmap: Bitmap,
        quality: Int = 85
    ): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        val bytes = outputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }
}