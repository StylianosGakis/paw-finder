package com.stylianosgakis.androiddevchallengeweek1.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.stylianosgakis.androiddevchallengeweek1.theme.animalCardBackgroundColor

@Composable
fun BoxScope.LoadingImage() {
    Box(
        Modifier
            .matchParentSize()
            .background(color = animalCardBackgroundColor)
    ) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}