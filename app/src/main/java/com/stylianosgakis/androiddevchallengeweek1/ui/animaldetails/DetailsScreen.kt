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
package com.stylianosgakis.androiddevchallengeweek1.ui.animaldetails

import android.app.Activity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Pets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.stylianosgakis.androiddevchallengeweek1.R
import com.stylianosgakis.androiddevchallengeweek1.api.model.animal.Animal
import com.stylianosgakis.androiddevchallengeweek1.api.model.animal.Photo
import com.stylianosgakis.androiddevchallengeweek1.components.ExtendedFab
import com.stylianosgakis.androiddevchallengeweek1.components.LoadingImage
import com.stylianosgakis.androiddevchallengeweek1.components.SingleLineText
import com.stylianosgakis.androiddevchallengeweek1.util.themeColor
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

@Composable
fun DetailsScreen(
    animal: Animal,
    navigateBack: () -> Unit, // Just go back using back button for now, it's Android :)
) {
    var selectedImageIndex by remember { mutableStateOf(0) }

    // TODO fix: Super ugly hack to change the status bar color because I am running out of time. Chris banes I have failed you.
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val window = (context as Activity).window
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        onDispose {
            window.statusBarColor = context.themeColor(R.attr.colorPrimary)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            PhotoSection(
                animal = animal,
                selectedImageIndex = selectedImageIndex,
                setSelectedImageIndex = {
                    selectedImageIndex = it
                },
                modifier = Modifier
                    .weight(0.45f)
                    .clip(
                        RoundedCornerShape(24.dp).copy(
                            topStart = ZeroCornerSize,
                            topEnd = ZeroCornerSize
                        )
                    )
            )
            DetailsSection(
                animal = animal,
                modifier = Modifier.weight(0.55f)
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
        ) {
            ExtendedFab(
                onClick = {
                },
                text = "ADOPT ME!",
                imageVector = Icons.TwoTone.Pets
            )
        }
    }
}

@Composable
private fun PhotoSection(
    animal: Animal,
    selectedImageIndex: Int,
    setSelectedImageIndex: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        CoilImage(
            data = animal.photos[selectedImageIndex].full,
            contentDescription = "${animal.description}",
            contentScale = ContentScale.FillBounds,
            loading = { LoadingImage() },
            error = { LoadingImage() },
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
                animal.photos.take(4).mapIndexed { currentPhotoIndex: Int, currentPhoto: Photo ->
                    val photoShape = MaterialTheme.shapes.small
                    val photoBorder = when (currentPhotoIndex == selectedImageIndex) {
                        // TODO Fix border is small when first loading the screen for some reason
                        true -> Modifier.border(
                            width = 4.dp,
                            color = MaterialTheme.colors.secondary,
                            shape = photoShape
                        )
                        false -> Modifier
                    }
                    CoilImage(
                        data = currentPhoto.full,
                        contentDescription = "${animal.description}",
                        contentScale = ContentScale.FillBounds,
                        loading = { LoadingImage() },
                        error = { LoadingImage() },
                        modifier = photoBorder
                            .clip(photoShape)
                            .size(miniPhotoSize)
                            .clickable {
                                setSelectedImageIndex(currentPhotoIndex)
                            }
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailsSection(
    animal: Animal,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SingleLineText(
                text = animal.simpleOneWordCapitalizedName,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold
            )
            Row {
                val detailsList = listOf(
                    "Age:" to animal.age,
                    "Sex:" to animal.gender,
                    "Breed:" to animal.breeds.primary,
                    "Size:" to animal.size
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Column {
                        detailsList.map { (description, _) ->
                            Text(description)
                        }
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    detailsList.map { (_, text) ->
                        Text(text)
                    }
                }
            }
            Text(
                text = "Description",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            if (animal.description != null) {
                Text(text = animal.description)
            }
        }
    }
}
