package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.SocialContent
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.model.user.toUser
import com.example.ur_color.data.repo.PostRepository
import com.example.ur_color.data.repo.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
class ProfileViewModel(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) : BaseViewModel() {

    private val _profileCardsState = MutableStateFlow<List<SocialContent.Post>?>(null)
    val profileCardsState = _profileCardsState.asStateFlow()

    private val _user = MutableStateFlow<UserData?>(null)
    val user = _user.asStateFlow()

    private val _aura = MutableStateFlow<Bitmap?>(null)
    val aura = _aura.asStateFlow()


    init {
        viewModelScope.launch {
            _user.value = userRepository.getUser()
            _aura.value = userRepository.getAura()

            getPostsByUser(_user.value?.id)
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            PersonalDataManager.delete()
        }
    }

    suspend fun getPostsByUser(userId: String?) {
        userId?.let { id ->
            postRepository.getPostsByUser(id).collect { posts ->
                _profileCardsState.value = posts
            }
        }
    }

    fun deletePost(postId: String) {
        viewModelScope.launch {
            postRepository.deletePost(postId)
        }
    }
}