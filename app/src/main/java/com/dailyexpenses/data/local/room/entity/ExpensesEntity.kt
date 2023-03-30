package com.dailyexpenses.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dailyexpenses.domain.model.Expenses
import com.dailyexpenses.domain.model.Tag


@Entity(tableName = "expenses")
data class ExpensesEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val amount: String,
    val title: String,
    val note: String,
    val day: Int,
    val month: Int,
    val year: Int
) {
    fun toModel(tagList: List<Tag>) = Expenses(
        id,
        amount,
        title,
        note,
        tagList,
        day,
        month,
        year
    )
}
