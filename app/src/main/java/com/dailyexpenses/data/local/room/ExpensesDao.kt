package com.dailyexpenses.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import com.dailyexpenses.data.local.room.entity.ExpensesEntity
import com.dailyexpenses.data.local.room.entity.ExpensesTagCrossRef

@Dao
interface ExpensesDao {

    @Insert
    suspend fun insertExpenses(expensesEntity: ExpensesEntity)

    @Insert
    suspend fun insertExpensesTagCrossRef(expensesTagCrossRef: ExpensesTagCrossRef)

}
