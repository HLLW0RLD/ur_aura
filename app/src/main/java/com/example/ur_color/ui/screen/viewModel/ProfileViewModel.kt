package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.SocialContent
import com.example.ur_color.data.model.User
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.model.user.toUser
import com.example.ur_color.data.repo.PostRepository
import com.example.ur_color.data.repo.UserRepository
import com.example.ur_color.utils.profileCards
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
class ProfileViewModel(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) : BaseViewModel() {

    private val _isDailyTestAvailable = MutableStateFlow(true)
    val isDailyTestAvailable = _isDailyTestAvailable.asStateFlow()

    private val _profileCardsState = MutableStateFlow(profileCards)
    val profileCardsState = _profileCardsState.asStateFlow()

    private val _user = MutableStateFlow<UserData?>(null)
    val user = _user.asStateFlow()

    private val _aura = MutableStateFlow<Bitmap?>(null)
    val aura= _aura.asStateFlow()

    private val _level = MutableStateFlow<Float>(1f)
    val level = _level

    fun init(context: Context) {
        viewModelScope.launch {
            _user.value = userRepository.getUser(context)
            _level.value = userRepository.getLvl(context)
            _aura.value = userRepository.getAura(context)
            getPostsByUser(_user.value?.id)
        }
    }

    fun checkDailyTestAvailability(context: Context) {
        viewModelScope.launch {

            val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
            val lastDate = PersonalDataManager.loadDailyTestDate(context)

            if (lastDate != today) {
                _isDailyTestAvailable.value = true
            } else {
                _isDailyTestAvailable.value = false
            }
        }
    }

    fun deleteUser(context: Context) {
        viewModelScope.launch {
            PersonalDataManager.delete(context)
        }
    }

    fun createPost(text: String, image: String? = null) {
        viewModelScope.launch {
            val user = user.value
            if (user != null) {
                val post = SocialContent.Post(
                    id = UUID.randomUUID().toString(),
                    text = text,
                    author = user.toUser(),
                    image = image
                )
                postRepository.savePost(post)
            }
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

//    suspend fun syncWithApi(apiService: YourApiService) {
//        val unsyncedPosts = postRepository.getUnsyncedPosts()
//
//        unsyncedPosts.forEach { post ->
//            try {
//                // Отправляем на сервер
//                apiService.createPost(post)
//
//                // Отмечаем как синхронизированный
//                postRepository.markAsSynced(post.id)
//
//            } catch (e: Exception) {
//                // Обработка ошибки
//                println("Failed to sync post: ${e.message}")
//            }
//        }
//    }
}