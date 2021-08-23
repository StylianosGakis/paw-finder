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
package xyz.stylianosgakis.database.data.animal.model

import androidx.room.Embedded

data class LinksEntity(
    @Embedded(prefix = "organization_") val organizationEntity: OrganizationEntity,
    @Embedded(prefix = "self_") val selfEntity: SelfEntity,
    @Embedded(prefix = "type_") val typeEntity: TypeEntity,
) {
    data class OrganizationEntity(
        val href: String,
    )

    data class SelfEntity(
        val href: String,
    )

    data class TypeEntity(
        val href: String,
    )
}
