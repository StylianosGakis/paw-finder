/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stylianosgakis.pawfinder.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Pets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.stylianosgakis.pawfinder.R
import com.stylianosgakis.pawfinder.theme.AppTheme
import com.stylianosgakis.pawfinder.theme.MyHappyEnding

@Composable
fun FindMyPetAppBar() {
    Surface(
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary,
    ) {
        TopAppBar(
            title = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        space = (-4).dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.Pets,
                        contentDescription = "Paw icon",
                        tint = MaterialTheme.colors.secondaryVariant,
                        modifier = Modifier
                            .scale(1.25f)
                            .graphicsLayer(
                                translationX = -5f,
                                rotationZ = -20f
                            )
                    )
                    Text(
                        text = stringResource(id = R.string.app_name),
                        textAlign = TextAlign.Center,
                        fontFamily = MyHappyEnding,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colors.secondaryVariant,
                        style = MaterialTheme.typography.h3,
                    )
                }
            },
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.onSurface,
            elevation = 0.dp,
            modifier = Modifier.statusBarsPadding()
        )
    }
}

@Preview
@Composable
fun FindMyPetAppBarPreview() {
    AppTheme {
        FindMyPetAppBar()
    }
}
