package com.example.ur_color.ui.screen.viewModel

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.response.UserContent
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.repo.PostRepository
import com.example.ur_color.data.repo.UserRepository
import com.example.ur_color.utils.logDebug
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
class ProfileViewModel(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) : BaseViewModel() {

    private val _profileCardsState = MutableStateFlow<List<UserContent.Post>?>(null)
    val profileCardsState = _profileCardsState.asStateFlow()

    private val _user = MutableStateFlow<UserData?>(null)
    val user = _user.asStateFlow()

    private val _aura = MutableStateFlow<Bitmap?>(null)
    val aura = _aura.asStateFlow()

    private var _postsFlow: Flow<PagingData<UserContent.Post>>? = null
    val postsFlow: Flow<PagingData<UserContent.Post>>?
        get() = _postsFlow


    fun init(id: String? = null) {
        viewModelScope.launch {
            if (id == null) {
                _user.value = userRepository.getMe(true)
                _aura.value = userRepository.getAura()

                _postsFlow = getUserPostsPagingData(_user.value?.id ?: return@launch)
            } else {
                _user.value = userRepository.getUserById(id)

                _postsFlow = getUserPostsPagingData(id)
            }

            logDebug(_user.value?.id)
        }

    }

    fun deleteUser() {
        viewModelScope.launch {
            PersonalDataManager.delete()
        }
    }

    private fun getUserPostsPagingData(userId: String): Flow<PagingData<UserContent.Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                postRepository.getUserPosts(userId)
            }
        ).flow.cachedIn(viewModelScope)
    }
}