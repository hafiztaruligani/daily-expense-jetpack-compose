package com.dailyexpenses.domain.model

import com.dailyexpenses.data.local.room.entity.ExpensesEntity
import java.util.*

data class Expenses(
    val id: String = UUID.randomUUID().toString(),
    val amount: String = "",
    val title: String = "",
    val note: String = "",
    val tagList: List<Tag> = listOf(),
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0,
) {

    var timeTitle: String = "$day/$month/$year"

    fun toEntity() = ExpensesEntity(
        id,
        amount,
        title,
        note,
        day,
        month,
        year,
    )
}