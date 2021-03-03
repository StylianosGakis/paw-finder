package com.stylianosgakis.androiddevchallengeweek1.components

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun SingleLineText(
    text: String,
    style: TextStyle = LocalTextStyle.current,
    fontWeight: FontWeight? = null,
) = Text(
    text = text,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
    style = style,
    fontWeight = fontWeight,
)