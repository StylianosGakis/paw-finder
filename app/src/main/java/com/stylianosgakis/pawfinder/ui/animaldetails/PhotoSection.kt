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
package com.stylianosgakis.pawfinder.ui.animaldetails

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage
import com.stylianosgakis.pawfinder.api.animal.model.Animal
import com.stylianosgakis.pawfinder.api.animal.model.Photo
import com.stylianosgakis.pawfinder.components.LoadingImage
import com.stylianosgakis.pawfinder.theme.AppTheme
import com.stylianosgakis.pawfinder.util.previewAnimal

@Composable
fun PhotoSection(
    animal: Animal,
    selectedImageIndex: Int,
    setSelectedImageIndex: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        CoilImage(
            // todo don't pass empty string
            imageModel = animal.photos.getOrNull(selectedImageIndex)?.full ?: "",
            contentDescription = "${animal.description}",
            contentScale = ContentScale.FillBounds,
            loading = { LoadingImage() },
            failure = { LoadingImage() },
            modifier = Modifier.matchParentSize()
        )
        if (animal.photos.size > 1) {
            val miniPhotoSize = 75.dp
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(18.dp)
            ) {
                animal
                    .photos
                    .take(4)
                    .forEachIndexed { currentPhotoIndex: Int, currentPhoto: Photo ->
                        val photoShape = MaterialTheme.shapes.small
                        val secondaryColor = MaterialTheme.colors.secondary

                        val photoBorder by derivedStateOf {
                            if (currentPhotoIndex == selectedImageIndex) {
                                Modifier
                                    .border(
                                        width = 4.dp,
                                        color = secondaryColor,
                                        shape = photoShape, /*RoundedCornerShape(64.dp)*/
                                    )
                            } else {
                                Modifier
                            }
                        }
                        Box(
                            modifier = Modifier
                                .size(miniPhotoSize)
                                .clip(photoShape)
                                .then(photoBorder)
                                .clickable {
                                    setSelectedImageIndex(currentPhotoIndex)
                                },
                        ) {
                            CoilImage(
                                imageModel = currentPhoto.small,
                                contentDescription = "${animal.description}",
                                contentScale = ContentScale.FillBounds,
                                loading = { LoadingImage() },
                                failure = { LoadingImage() },
                                modifier = Modifier
                                    .size(miniPhotoSize)
                            )
                        }
                    }
            }
        }
    }
}

@Preview
@Composable
fun PhotoSectionPreview() {
    AppTheme {
        PhotoSection(
            animal = previewAnimal,
            selectedImageIndex = 0,
            setSelectedImageIndex = {}
        )
    }
}
