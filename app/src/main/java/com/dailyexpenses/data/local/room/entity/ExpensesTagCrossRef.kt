package com.dailyexpenses.data.local.room.entity

import androidx.room.Entity


@Entity(primaryKeys = ["expensesId", "tagId"])
data class ExpensesTagCrossRef(
    val expensesId: Int,
    val tagId: Int
)
