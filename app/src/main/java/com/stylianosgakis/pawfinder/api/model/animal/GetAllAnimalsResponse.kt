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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllAnimalsResponse(
    val animals: List<Animal>,
    val pagination: Pagination,
)

@Serializable
data class GetOneAnimalResponse(
    val animal: Animal,
)

@Serializable
data class Pagination(
    @SerialName("count_per_page")
    val countPerPage: Int,
    @SerialName("current_page")
    val currentPage: Int,
    @SerialName("_links")
    val links: Links,
    @SerialName("total_count")
    val totalCount: Int,
    @SerialName("total_pages")
    val totalPages: Int,
) {
    @Serializable
    data class Links(
        val next: Next,
        val previous: Previous? = null,
    ) {
        @Serializable
        data class Next(
            val href: String,
        )

        @Serializable
        data class Previous(
            val href: String,
        )
    }
}
