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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stylianosgakis.pawfinder.api.model.animal.Animal
import com.stylianosgakis.pawfinder.components.SingleLineText
import com.stylianosgakis.pawfinder.theme.AppTheme
import com.stylianosgakis.pawfinder.util.previewAnimal

@Composable
fun DetailsSection(
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

@Preview
@Composable
fun DetailsSectionPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colors.background,
        ) {
            DetailsSection(previewAnimal)
        }
    }
}
