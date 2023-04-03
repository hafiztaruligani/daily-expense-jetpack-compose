package com.dailyexpenses.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.dailyexpenses.data.local.room.entity.ExpensesEntity
import com.dailyexpenses.data.local.room.entity.ExpensesTagCrossRef

@Dao
interface ExpensesDao {

    @Insert
    suspend fun insertExpenses(expensesEntity: ExpensesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpensesTagCrossRef(expensesTagCrossRef: ExpensesTagCrossRef)

}
