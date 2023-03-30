package com.dailyexpenses.presentation.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

fun horizontalGradient() =
    Brush.horizontalGradient(
        colors = listOf(
            Color(android.graphics.Color.parseColor("#20addd")),
            Color(android.graphics.Color.parseColor("#d06bef")),
            Color(android.graphics.Color.parseColor("#fb966f"))
        )
    )

fun verticalGradient() =
    Brush.verticalGradient(
        colors = listOf(
            Color(android.graphics.Color.parseColor("#20addd")),
            Color(android.graphics.Color.parseColor("#d06bef")),
            Color(android.graphics.Color.parseColor("#fb966f"))
        )
    )

fun angledGradient() =
    Brush.linearGradient(
        colors = listOf(
            Color(android.graphics.Color.parseColor("#20addd")),
            Color(android.graphics.Color.parseColor("#d06bef")),
            Color(android.graphics.Color.parseColor("#fb966f"))
        ),
        end = Offset(Float.MIN_VALUE, Float.POSITIVE_INFINITY),
        start = Offset(Float.POSITIVE_INFINITY, Float.MIN_VALUE)
    )
