package com.dailyexpenses.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailyexpenses.domain.model.Expenses
import com.dailyexpenses.domain.model.MonthWithExpenses
import com.dailyexpenses.domain.model.MonthWithYear
import com.dailyexpenses.domain.model.Tag
import com.dailyexpenses.domain.repository.ExpensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository,
    private val calendar: Calendar
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState : StateFlow<HomeUiState> = _uiState

    init {
        getMonth()
        //createExampleData()
    }

    private fun createExampleData() = viewModelScope.launch{
        repeat(12){ m ->
            repeat(50){ d->
                expensesRepository.createExpenses(
                    Expenses(
                        amount = kotlin.random.Random.nextInt().mod(1000000).toString(),
                                title = "This is Title",
                                note = "This is Note",
                                tagList = listOf(Tag(0,""), Tag(1,"")),
                                day = d.mod(30),
                                month = m,
                                year = 2023
                    )
                )
            }
        }
    }

    private fun getMonth() = viewModelScope.launch {
        expensesRepository.getAllMonth().collect {
            _uiState.value = uiState.value.copy(monthList = it)
            if (it.isNotEmpty()){
                setSelectedMonth(it.size-1)
            }
        }
    }


    private fun getExpensesByMonth(month: Int, year: Int) = viewModelScope.launch{
        expensesRepository.getExpensesByMonth(month, year).collect {
            Log.d(javaClass.simpleName, "testtt getExpensesByMonth: ${it.month}")
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH) +1
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
            val mapTitle = it.expenses.map { expenses ->
                if ( expenses.month == currentMonth && expenses.year == currentYear ){
                    if (expenses.day==currentDay) expenses.timeTitle = "Today"
                    else if (expenses.day==currentDay-1) expenses.timeTitle = "Yesterday"
                }
                expenses
            }
            _uiState.value = uiState.value.copy(
                expensesList =
                MonthWithExpenses(
                    month = month,
                    expenses = mapTitle
                )
            )
        }
    }

    fun setSelectedMonth(page: Int){
        val months = uiState.value.monthList
        if (months.isNotEmpty()) {
            val item = months[page]
            getExpensesByMonth(item.month, item.year)
        }
    }


}

data class HomeUiState(
    val monthList: List<MonthWithYear> = listOf() ,
    val expensesList: MonthWithExpenses? = null
) {
    fun getTotal() = expensesList?.expenses?.sumOf { it.amount.toBigDecimal() }
}
