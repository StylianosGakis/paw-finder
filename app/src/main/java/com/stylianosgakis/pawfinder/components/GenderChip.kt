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

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Male
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.stylianosgakis.pawfinder.theme.AnimalGenderFemale
import com.stylianosgakis.pawfinder.theme.AnimalGenderMale
import com.stylianosgakis.pawfinder.theme.AnimalGenderUnknown

@Composable
fun GenderChip(
    genderString: String,
) {
    val iconData = when (Gender.fromString(genderString)) {
        Gender.Female -> {
            IconData(
                imageVector = Icons.Default.Female,
                color = AnimalGenderFemale,
            )
        }
        Gender.Male -> {
            IconData(
                imageVector = Icons.Default.Male,
                color = AnimalGenderMale,
            )
        }
        Gender.Unknown -> {
            IconData(
                imageVector = Icons.Default.HelpOutline,
                color = AnimalGenderUnknown,
            )
        }
    }
    Icon(
        imageVector = iconData.imageVector,
        tint = iconData.color,
        contentDescription = "Gender Icon",
        modifier = Modifier.size(24.dp)
    )
}

private class IconData(
    val imageVector: ImageVector,
    val color: Color,
)

sealed class Gender {

    companion object {
        @Suppress("RemoveRedundantQualifierName")
        fun fromString(gender: String): Gender = when {
            gender.equals("male", ignoreCase = true) -> Gender.Male
            gender.equals("female", ignoreCase = true) -> Gender.Female
            else -> Gender.Unknown
        }
    }

    object Female : Gender()
    object Male : Gender()
    object Unknown : Gender()
}
