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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage
import com.stylianosgakis.pawfinder.api.model.animal.Animal
import com.stylianosgakis.pawfinder.theme.AppTheme
import com.stylianosgakis.pawfinder.theme.animalCardBackgroundColor
import com.stylianosgakis.pawfinder.util.previewAnimal

private enum class PhotoPosition {
    Left, Right
}

@Composable
fun AnimalCard(
    cardIndex: Int,
    animal: Animal,
    goToDetailsScreen: () -> Unit,
) {
    val photoPosition = when (cardIndex % 2 == 0) {
        true -> PhotoPosition.Left
        false -> PhotoPosition.Right
    }
    AnimalCard(
        photoPosition = photoPosition,
        photo = {
            CoilImage(
                imageModel = animal.photos.firstOrNull()?.medium ?: "",
                contentDescription = "Animal photo",
                contentScale = ContentScale.Crop,
                loading = { LoadingImage() },
                failure = { LoadingImage() },
                modifier = Modifier
                    .size(width = 150.dp, height = 200.dp)
                    .clip(MaterialTheme.shapes.small)
                    .clickable { goToDetailsScreen() },
            )
        },
        animalDetails = { clipShape ->
            Surface(
                shape = clipShape,
                color = animalCardBackgroundColor,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .clip(clipShape)
                    .clickable { goToDetailsScreen() }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SingleLineText(
                            text = animal.simpleOneWordCapitalizedName,
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                        )
                        GenderChip(animal.gender)
                    }
                    val species = animal.species
                    val primaryBreed = animal.breeds.primary
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        SingleLineText(
                            text = species,
                            style = MaterialTheme.typography.body1,
                        )
                    }
                    if (species != primaryBreed) {
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                            SingleLineText(
                                text = primaryBreed,
                                style = MaterialTheme.typography.body1,
                            )
                        }
                    }
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                        SingleLineText(
                            text = animal.age,
                            style = MaterialTheme.typography.body2,
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    SingleLineText(
                        text = animal.contact.address.run {
                            "$city, $state"
                        },
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    )
}

@Composable
private fun AnimalCard(
    photoPosition: PhotoPosition,
    photo: @Composable RowScope.() -> Unit,
    animalDetails: @Composable RowScope.(clipShape: Shape) -> Unit,
) {
    when (photoPosition) {
        PhotoPosition.Left -> {
            val clipShape = MaterialTheme.shapes.small.copy(
                topStart = CornerSize(0.dp),
                bottomStart = CornerSize(0.dp),
            )
            Row {
                photo()
                animalDetails(clipShape)
            }
        }
        PhotoPosition.Right -> {
            val clipShape = MaterialTheme.shapes.small.copy(
                topEnd = CornerSize(0.dp),
                bottomEnd = CornerSize(0.dp),
            )
            Row {
                animalDetails(clipShape)
                photo()
            }
        }
    }
}

@Preview
@Composable
fun AnimalCardPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colors.background,
        ) {
            AnimalCard(cardIndex = 0, animal = previewAnimal, goToDetailsScreen = {})
        }
    }
}
