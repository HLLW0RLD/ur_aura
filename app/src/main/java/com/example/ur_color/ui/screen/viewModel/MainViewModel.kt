package com.example.ur_color.ui.screen.viewModel

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.model.response.Horoscope
import com.example.ur_color.data.model.response.UserContent
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.repo.HoroscopeDate
import com.example.ur_color.data.repo.PostRepository
import com.example.ur_color.data.repo.UserRepository
import com.example.ur_color.utils.feedCards
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class HoroscopeUiState {
    object Loading : HoroscopeUiState()
    data class Success(val horoscope: Horoscope) : HoroscopeUiState()
    data class Error(val message: String) : HoroscopeUiState()
}

class MainViewModel(
//    private val horoscopeRepository: HoroscopeRepository
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) : BaseViewModel() {

    private val _horoscopeState = MutableStateFlow<HoroscopeUiState>(HoroscopeUiState.Loading)
    val horoscopeState: StateFlow<HoroscopeUiState> = _horoscopeState

    private val _feedCardsState = MutableStateFlow<List<UserContent>>(feedCards)
    val feedCardsState = _feedCardsState.asStateFlow()

    private val _user = MutableStateFlow<UserData?>(null)
    val user = _user.asStateFlow()

    private val _aura = MutableStateFlow<Bitmap?>(null)
    val aura = _aura.asStateFlow()

    private var _postsFlow: Flow<PagingData<UserContent.Post>>? = null
    val postsFlow: Flow<PagingData<UserContent.Post>>?
        get() = _postsFlow

    fun init() {
        viewModelScope.launch {
            // getMe убрать true после добавления апи обновления характеристик
            _user.value = userRepository.getMe(true)
            _aura.value = userRepository.getAura()

            _postsFlow = getUserPostsPagingData(_user.value?.id ?: return@launch)
        }
    }


    fun loadDailyHoroscope(sign: String, day: HoroscopeDate = HoroscopeDate.TODAY) {
        _horoscopeState.value = HoroscopeUiState.Loading

//        viewModelScope.launch {
//            horoscopeRepository
//                .getDailyHoroscope(sign, day)
//                .fold(
//                    onSuccess = { horoscope ->
//                        _horoscopeState.value = HoroscopeUiState.Success(horoscope)
//                    },
//                    onFailure = { error ->
//                        _horoscopeState.value = HoroscopeUiState.Error(error.localizedMessage ?: "Unknown error")
//                    }
//                )
//        }
    }

    private fun getUserPostsPagingData(userId: String): Flow<PagingData<UserContent.Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                postRepository.getFeed()
            }
        ).flow.cachedIn(viewModelScope)
    }
}