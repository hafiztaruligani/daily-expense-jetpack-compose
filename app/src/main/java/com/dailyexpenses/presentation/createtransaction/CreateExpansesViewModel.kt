package com.dailyexpenses.presentation.createtransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailyexpenses.domain.model.Expenses
import com.dailyexpenses.domain.model.Tag
import com.dailyexpenses.domain.repository.ExpensesRepository
import com.dailyexpenses.domain.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateExpansesViewModel @Inject constructor(
    private val tagRepository: TagRepository,
    private val expensesRepository: ExpensesRepository
) : ViewModel() {

    init {
        getTagList()
    }

    private val mCalendar by lazy { Calendar.getInstance() }

    private val year: Int by lazy { mCalendar.get(Calendar.YEAR) }
    private val month: Int by lazy { mCalendar.get(Calendar.MONTH) + 1 }
    private val day: Int by lazy { mCalendar.get(Calendar.DAY_OF_MONTH) }
    private val number = "12345678990."

    private val _uiState = MutableStateFlow(
        CreateExpansesUiState(
            day = day,
            month = month,
            year = year
        )
    )
    val uiState: StateFlow<CreateExpansesUiState> = _uiState

    val selectedTag = MutableStateFlow(mutableListOf<Tag>())

    private fun getTagList() = viewModelScope.launch {
        tagRepository.getTagList().collect {
            _uiState.value = uiState.value.copy(tagList = it.filterNotNull())
        }
    }

    fun setAmount(text: String) {
        text.lastOrNull()?.let {
            if (number.contains(it) && text != "Rp.")
                _uiState.value = uiState.value.copy(amount = text)
        }
    }

    fun setTitle(text: String) {
        _uiState.value = uiState.value.copy(title = text)
    }

    fun setNote(text: String) {
        _uiState.value = uiState.value.copy(note = text)
    }

    fun showDatePicker() = viewModelScope.launch {
        _uiState.value = uiState.value.copy(needDatePicker = true)
        delay(100)
        _uiState.value = uiState.value.copy(needDatePicker = false)
    }

    fun setDate(day: Int, month: Int, year: Int) {
        _uiState.value = uiState.value.copy(
            day = day,
            month = month,
            year = year
        )
    }

    fun setSelectedTag(tag: Tag, it: Boolean) {
        val list = selectedTag.value
        if (it && !list.contains(tag))
            list.add(tag)
        else if (list.contains(tag))
            list.remove(tag)
        selectedTag.value = list
    }

    fun save() = viewModelScope.launch{
        when {
            selectedTag.value.size <= 0 -> error("Select at least 1 Tag")
            uiState.value.amount == "" -> error("Amount cannot 0")
            else -> {
                if (uiState.value.title.isBlank())
                    _uiState.value = uiState.value.copy(title = selectedTag.value.first().name)
                expensesRepository.createExpenses(
                    uiState.value.getExpenses()
                )
            }
        }
    }

    private suspend fun error(message: String) {
        _uiState.value = uiState.value.copy(error = message)
        delay(100)
        _uiState.value = uiState.value.copy(error = "")
    }

}

data class CreateExpansesUiState(
    val amount: String = "0",
    val title: String = " ",
    val note: String = " ",
    val day: Int,
    val month: Int,
    val year: Int,
    val needDatePicker: Boolean = false,
    val tagList: List<Tag> = listOf(),
    val error: String = ""
) {
    fun getExpenses() = Expenses(
        amount = amount,
        title = title,
        note = note,
        tagList = tagList,
        day = day,
        month = month,
        year = year
    )
}
