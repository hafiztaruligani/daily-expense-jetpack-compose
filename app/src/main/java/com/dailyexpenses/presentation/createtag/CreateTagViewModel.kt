package com.dailyexpenses.presentation.createtag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailyexpenses.domain.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CreateTagViewModel @Inject constructor(
    private val tagRepository: TagRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTagUiState())
    val uiState: StateFlow<CreateTagUiState> = _uiState

    fun setTagName(it: String) {
        _uiState.value = uiState.value.copy(name = it)
    }

    fun addTag() = viewModelScope.launch {
        val tagName = uiState.value.name
        if (tagName.isNotBlank()) {
            _uiState.value = uiState.value.copy(close = true)
            tagRepository.createTag(tagName)
            delay(100)
            _uiState.value = uiState.value.copy(close = false)
        }
    }
}

data class CreateTagUiState(
    val name: String = "",
    val close: Boolean = false
)
