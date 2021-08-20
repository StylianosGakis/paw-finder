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
package com.stylianosgakis.pawfinder.api.model.animal

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Animal(
    val age: String,
    val attributes: Attributes,
    val breeds: Breeds,
    val contact: Contact,
    val description: String?,
    val gender: String,
    val id: Int,
    @SerialName("_links")
    val links: Links,
    val name: String,
    @SerialName("organization_animal_id")
    val organizationAnimalId: String?,
    @SerialName("organization_id")
    val organizationId: String,
    val photos: List<Photo>,
    @SerialName("primary_photo_cropped")
    val primaryPhotoCropped: PrimaryPhotoCropped?,
    @SerialName("published_at")
    val publishedAt: String,
    val size: String,
    val species: String,
    val status: String,
    @SerialName("status_changed_at")
    val statusChangedAt: String,
    val type: String,
    val url: String,
) {
    val simpleOneWordCapitalizedName: String
        get() = name.toLowerCase(Locale("en"))
            .split(" ")
            .first()
            .run {
                replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(java.util.Locale.ENGLISH) else it.toString()
                }
            }
}
