package com.dailyexpenses.data.repository

import com.dailyexpenses.data.local.room.ExpensesDao
import com.dailyexpenses.data.local.room.entity.ExpensesTagCrossRef
import com.dailyexpenses.domain.model.Expenses
import com.dailyexpenses.domain.repository.ExpensesRepository

class ExpensesRepositoryImpl(
    private val expensesDao: ExpensesDao
) : ExpensesRepository {

    override suspend fun createExpenses(expenses: Expenses) {
        expensesDao.insertExpenses(expenses.toEntity())
        insertCrossRef(expenses)
    }

    private suspend fun insertCrossRef(expenses: Expenses) {
        expenses.tagList.forEach {
            expensesDao.insertExpensesTagCrossRef(
                ExpensesTagCrossRef(
                    expensesId = expenses.id,
                    tagId = it.id
                )
            )
        }
    }

}