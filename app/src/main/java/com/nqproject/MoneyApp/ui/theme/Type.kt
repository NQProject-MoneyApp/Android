package com.nqproject.MoneyApp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nqproject.MoneyApp.R

val PoppinsFont = FontFamily(
    Font(R.font.poppins_regular),
    Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(R.font.poppins_light, FontWeight.Light),
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = PoppinsFont,
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 60.sp,
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    )
)