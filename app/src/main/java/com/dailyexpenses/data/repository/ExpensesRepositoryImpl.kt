package com.dailyexpenses.data.repository

import com.dailyexpenses.data.local.room.ExpensesDao
import com.dailyexpenses.data.local.room.entity.ExpensesTagCrossRef
import com.dailyexpenses.domain.model.Expenses
import com.dailyexpenses.domain.model.MonthWithExpenses
import com.dailyexpenses.domain.model.MonthWithYear
import com.dailyexpenses.domain.repository.ExpensesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun getExpensesByMonth(month: Int, year: Int): Flow<MonthWithExpenses> {
        return expensesDao.getExpensesByMonth(month, year).map { expensesEntities ->
            MonthWithExpenses(
                month,
                expensesEntities.map { it.toModel( listOf() ) }
            )
        }
    }

    override fun getAllMonth(): Flow<List<MonthWithYear>> =
        expensesDao.getAllMonth().map { data ->
            data.map { it.toModel() }
                .distinctBy { it.month }
        }

}