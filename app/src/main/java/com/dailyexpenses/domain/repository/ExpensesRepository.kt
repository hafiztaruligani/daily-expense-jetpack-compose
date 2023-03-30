package com.dailyexpenses.domain.repository

import com.dailyexpenses.domain.model.Expenses

interface ExpensesRepository {

    suspend fun createExpenses(expenses: Expenses)


}