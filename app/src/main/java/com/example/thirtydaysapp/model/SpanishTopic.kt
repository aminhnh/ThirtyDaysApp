package com.example.thirtydaysapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SpanishTopic(
    @StringRes val nameRes: Int,
    @StringRes val descriptionRes: Int,
    val day: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val imageCreditsRes: Int
)