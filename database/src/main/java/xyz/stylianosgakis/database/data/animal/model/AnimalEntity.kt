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
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animal")
data class AnimalEntity(
    val age: String,
    @Embedded val attributesEntity: AttributesEntity,
    @Embedded val breedsEntity: BreedsEntity,
    @Embedded val contactEntity: ContactEntity,
    val description: String?,
    val gender: String,
    @PrimaryKey val id: Long,
    @Embedded val linksEntity: LinksEntity,
    val name: String,
    val organizationAnimalId: String?,
    val organizationId: String,
    @Embedded val primaryPhotoCroppedEntity: PrimaryPhotoCroppedEntity?,
    val publishedAt: String,
    val size: String,
    val species: String,
    val status: String,
    val statusChangedAt: String,
    val type: String,
    val url: String,
)
