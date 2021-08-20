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
package com.stylianosgakis.pawfinder.components

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
import com.stylianosgakis.pawfinder.theme.floatingActionButtonDefaultElevation

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
