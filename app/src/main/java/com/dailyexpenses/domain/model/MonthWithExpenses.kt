package com.dailyexpenses.domain.model

data class MonthWithExpenses(
    val month: Int,
    val expenses: List<Expenses>
)