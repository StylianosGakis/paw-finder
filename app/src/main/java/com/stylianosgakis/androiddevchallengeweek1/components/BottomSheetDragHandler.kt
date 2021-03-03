package com.stylianosgakis.androiddevchallengeweek1.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetDragHandler(
    modifier: Modifier,
    color: Color,
) {
    Box(
        modifier = modifier
            .padding(top = 12.dp, bottom = 8.dp)
            .size(width = 60.dp, height = 4.dp)
            .background(
                color = color.copy(alpha = ContentAlpha.medium),
                shape = CircleShape
            )
    )
}