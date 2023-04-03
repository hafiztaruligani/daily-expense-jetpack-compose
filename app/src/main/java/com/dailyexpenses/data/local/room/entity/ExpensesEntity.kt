package com.dailyexpenses.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dailyexpenses.domain.model.Expenses
import com.dailyexpenses.domain.model.Tag


@Entity(tableName = "expenses")
data class ExpensesEntity(

    @PrimaryKey(autoGenerate = false)
    val id: String,
    val amount: String,
    val title: String,
    val note: String,
    val day: Int,
    val month: Int,
    val year: Int
) {
    constructor():this("","","","",0,0,0)

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
