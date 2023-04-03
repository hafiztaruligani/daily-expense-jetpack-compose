package com.dailyexpenses.data.local.room.entity

import androidx.room.ColumnInfo
import com.dailyexpenses.domain.model.MonthWithYear


data class MonthWithYearEntity(
    @ColumnInfo("month")
    val month : Int,
    @ColumnInfo("year")
    val year : Int
) {
    constructor(): this(0,0)

    fun toModel() = MonthWithYear(
        month = month,
        year = year
    )
}
