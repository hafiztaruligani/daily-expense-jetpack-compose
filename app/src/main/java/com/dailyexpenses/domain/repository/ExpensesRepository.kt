package com.dailyexpenses.domain.repository

import com.dailyexpenses.domain.model.Expenses
import com.dailyexpenses.domain.model.MonthWithExpenses
import com.dailyexpenses.domain.model.MonthWithYear
import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {

    suspend fun createExpenses(expenses: Expenses)
    fun getExpensesByMonth(month: Int, year: Int): Flow<MonthWithExpenses>
    fun getAllMonth(): Flow<List<MonthWithYear>>


}