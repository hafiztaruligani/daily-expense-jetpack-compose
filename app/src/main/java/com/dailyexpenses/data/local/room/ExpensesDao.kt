package com.dailyexpenses.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dailyexpenses.data.local.room.entity.ExpensesEntity
import com.dailyexpenses.data.local.room.entity.ExpensesTagCrossRef
import com.dailyexpenses.data.local.room.entity.MonthWithYearEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensesDao {

    @Insert
    suspend fun insertExpenses(expensesEntity: ExpensesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpensesTagCrossRef(expensesTagCrossRef: ExpensesTagCrossRef)


    @Query(
        "SELECT * FROM expenses WHERE month=:month AND year=:year "+
                "ORDER BY year, month, day DESC"
    )
    fun getExpensesByMonth(month: Int, year: Int): Flow<List<ExpensesEntity>>

    @Query(
        "SELECT DISTINCT * FROM expenses"
    )
    fun getAllMonth(): Flow<List<MonthWithYearEntity>>

}
