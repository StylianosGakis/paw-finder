package com.stylianosgakis.androiddevchallengeweek1.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.stylianosgakis.androiddevchallengeweek1.theme.floatingActionButtonDefaultElevation

@Composable
fun ExtendedFab(
    onClick: () -> Unit,
    text: String,
    imageVector: ImageVector,
) {
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp, vertical = 24.dp),
        text = {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.background,
            )
        },
        icon = {
            Icon(
                imageVector = imageVector,
                tint = MaterialTheme.colors.background,
                contentDescription = "Fab icon"
            )
        },
        elevation = floatingActionButtonDefaultElevation(),
        onClick = onClick
    )
}