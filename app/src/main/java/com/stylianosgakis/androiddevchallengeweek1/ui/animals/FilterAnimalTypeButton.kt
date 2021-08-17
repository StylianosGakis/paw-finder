package com.stylianosgakis.androiddevchallengeweek1.ui.animals

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.FilterAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stylianosgakis.androiddevchallengeweek1.components.ExtendedFab
import com.stylianosgakis.androiddevchallengeweek1.theme.AppTheme

@Suppress("TransitionPropertiesLabel", "UpdateTransitionLabel")
@Composable
fun FilterAnimalTypeButton(
    onClick: () -> Unit,
    visible: Boolean,
    modifier: Modifier = Modifier,
) {
    val transition: Transition<Boolean> = updateTransition(targetState = visible)

    val alpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 280, easing = FastOutSlowInEasing) },
        label = "",
    ) { isVisible ->
        when (isVisible) {
            true -> 1f
            false -> 0f
        }
    }

    val translationY by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 280, easing = FastOutSlowInEasing) },
    ) { isVisible ->
        when (isVisible) {
            true -> 0f
            false -> with(LocalDensity.current) { 40.dp.toPx() }
        }
    }

    Box(
        modifier = modifier
            .graphicsLayer(
                clip = false,
                alpha = alpha,
                translationY = translationY,
            )
    ) {
        ExtendedFab(
            onClick = onClick,
            text = "CHANGE ANIMAL TYPE",
            imageVector = Icons.TwoTone.FilterAlt
        )
    }
}

@Preview
@Composable
fun FilterAnimalTypeButtonPreview() {
    AppTheme {
        FilterAnimalTypeButton(
            onClick = {},
            visible = true,
        )
    }
}
