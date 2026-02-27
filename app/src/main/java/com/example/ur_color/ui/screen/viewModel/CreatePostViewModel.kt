package com.example.ur_color.ui.screen.viewModel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.model.response.UserContent
import com.example.ur_color.data.model.response.UserModel
import com.example.ur_color.data.repo.PostRepository
import com.example.ur_color.utils.getCurrentDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class CreatePostViewModel(
    private val postRepository: PostRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow(CreatePostState.Empty)
    val state: StateFlow<CreatePostState> = _state.asStateFlow()

    fun onTextChanged(text: String) {
        _state.update { currentState ->
            val isButtonEnabled = text.isNotBlank()
            currentState.copy(
                text = text,
                isButtonEnabled = isButtonEnabled
            )
        }
    }

    fun onImageSelected(uri: Uri?) {
        _state.update { it.copy(selectedImageUri = uri) }
    }

    fun clearImage() {
        _state.update { it.copy(selectedImageUri = null) }
    }

    fun createPost(currentUserModel: UserModel) {
        try {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true, error = null) }

                val post = UserContent.Post(
                    created = getCurrentDateTime(),
                    id = UUID.randomUUID().toString(),
                    text = _state.value.text.trim(),
                    author = currentUserModel,
                    image = null
//                    image = _state.value.selectedImageUri?.toString()
                )

                postRepository.createPost(post)

                _state.update { CreatePostState.Empty }
            }
        } catch (e: Exception) {
            _state.update { it.copy(error = e.message, isLoading = false) }
        }
    }

    fun reset() {
        _state.update { CreatePostState.Empty }
    }
}

data class CreatePostState(
    val text: String = "",
    val selectedImageUri: Uri? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isButtonEnabled: Boolean = false
) {
    companion object {
        val Empty = CreatePostState()
    }
}