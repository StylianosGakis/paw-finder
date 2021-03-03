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
package com.stylianosgakis.androiddevchallengeweek1.ui.animals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Pets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.stylianosgakis.androiddevchallengeweek1.components.BottomSheetDragHandler
import com.stylianosgakis.androiddevchallengeweek1.data.AnimalType
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

@Composable
fun AnimalTypePickerSheet(
    selectedAnimalType: AnimalType,
    setSelectedAnimalType: (AnimalType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colors.primary)
            .navigationBarsPadding()
    ) {
        Column {
            BottomSheetDragHandler(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = when (MaterialTheme.colors.isLight) {
                    true -> MaterialTheme.colors.onPrimary
                    false -> MaterialTheme.colors.onSurface
                }
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp)
            ) {
                items(AnimalType.values()) { animalType: AnimalType ->
                    AnimalTypeCard(
                        animalType = animalType,
                        isSelected = selectedAnimalType == animalType,
                        onClick = {
                            if (selectedAnimalType != animalType) {
                                setSelectedAnimalType(animalType)
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimalTypeCard(
    animalType: AnimalType,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val animalCardColor = when (isSelected) {
        true -> MaterialTheme.colors.secondaryVariant
        false -> MaterialTheme.colors.secondary
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .clip(CircleShape)
                .background(color = animalCardColor)
        ) {
            Icon(
                imageVector = Icons.TwoTone.Pets,
                tint = MaterialTheme.colors.onSecondary,
                contentDescription = "Select this animal type"
            )
        }
        Text(
            text = animalType.singleWordRepresentation,
            style = MaterialTheme.typography.subtitle1,
            color = animalCardColor,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
