package com.nqproject.MoneyApp.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp


@Suppress("UnnecessaryComposedModifier")
fun Modifier.bottomRectBorder(width: Dp = Dp.Hairline, color: Color = Color.Black):
        Modifier = composed(
    factory = {
        this.then(
            Modifier.drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawLine(
                        color,
                        Offset(width.value, size.height - width.value),
                        Offset(size.width - width.value, size.height - width.value),
                        strokeWidth = width.value
                    )
                }
            }
        )
    },
)