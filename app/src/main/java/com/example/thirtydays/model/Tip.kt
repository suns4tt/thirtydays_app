package com.example.thirtydays.model
import androidx.annotation.DrawableRes
data class Tip(
    val day: Int,
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int
)