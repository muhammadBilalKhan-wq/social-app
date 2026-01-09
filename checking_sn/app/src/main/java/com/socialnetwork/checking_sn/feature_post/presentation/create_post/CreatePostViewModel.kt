package com.socialnetwork.checking_sn.feature_post.presentation.create_post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialnetwork.checking_sn.core.domain.models.Post
import com.socialnetwork.checking_sn.feature_post.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CreatePostUiEvent {
    data class PostCreated(val post: Post) : CreatePostUiEvent()
}

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _state = mutableStateOf(CreatePostState())
    val state: State<CreatePostState> = _state

    private val _eventFlow = MutableSharedFlow<CreatePostUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: CreatePostEvent) {
        when (event) {
            is CreatePostEvent.EnteredContent -> {
                _state.value = state.value.copy(content = event.value)
            }
            is CreatePostEvent.SelectedImage -> {
                _state.value = state.value.copy(selectedImageUri = event.imageUri)
            }
            is CreatePostEvent.RemoveImage -> {
                _state.value = state.value.copy(selectedImageUri = null)
            }
            is CreatePostEvent.Post -> {
                post()
            }
        }
    }

    private fun post() {
        if (state.value.content.isBlank()) {
            return
        }
        viewModelScope.launch {
            _state.value = state.value.copy(isSubmitting = true)
            val result = repository.createPost(state.value.content, state.value.selectedImageUri)
            when (result) {
                is com.socialnetwork.checking_sn.core.util.Resource.Success -> {
                    val newPost = result.data
                    if (newPost != null) {
                        _eventFlow.emit(CreatePostUiEvent.PostCreated(newPost))
                    }
                    _state.value = state.value.copy(isSubmitting = false)
                }
                is com.socialnetwork.checking_sn.core.util.Resource.Error -> {
                    _state.value = state.value.copy(
                        isSubmitting = false,
                        error = result.uiText
                    )
                }
            }
        }
    }
}
