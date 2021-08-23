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
package com.stylianosgakis.pawfinder.api.animal.model

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.stylianosgakis.database.data.animal.model.AnimalEntity
import xyz.stylianosgakis.database.data.animal.model.relations.AnimalWithPhotos
import xyz.stylianosgakis.database.data.animal.model.AttributesEntity
import xyz.stylianosgakis.database.data.animal.model.BreedsEntity
import xyz.stylianosgakis.database.data.animal.model.ContactEntity
import xyz.stylianosgakis.database.data.animal.model.LinksEntity
import xyz.stylianosgakis.database.data.animal.model.PhotoEntity
import xyz.stylianosgakis.database.data.animal.model.PrimaryPhotoCroppedEntity

@Serializable
data class Animal(
    val age: String,
    val attributes: Attributes,
    val breeds: Breeds,
    val contact: Contact,
    val description: String?,
    val gender: String,
    val id: Long,
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

fun AnimalWithPhotos.toAnimal(): Animal {
    val photoList: List<Photo> = this.photos.map(PhotoEntity::toPhotos)
    return animalEntity.run {
        Animal(
            age = age,
            attributes = attributesEntity.run {
                Attributes(
                    declawed = declawed,
                    houseTrained = houseTrained,
                    shotsCurrent = shotsCurrent,
                    spayedNeutered = spayedNeutered,
                    specialNeeds = specialNeeds,
                )
            },
            breeds = breedsEntity.run {
                Breeds(
                    mixed,
                    primary,
                    secondary,
                    unknown,
                )
            },
            contact = contactEntity.run {
                Contact(
                    address = addressEntity.run {
                        Contact.Address(
                            address = address,
                            city = city,
                            country = country,
                            postcode = postcode,
                            state = state,
                        )
                    },
                    email = email,
                    phone = phone,
                )
            },
            description = description,
            gender = gender,
            id = id,
            links = linksEntity.run {
                Links(
                    organization = organizationEntity.run {
                        Links.Organization(
                            href = href
                        )
                    },
                    self = selfEntity.run {
                        Links.Self(
                            href = href
                        )
                    },
                    type = typeEntity.run {
                        Links.Type(
                            href = href
                        )
                    },
                )
            },
            name = name,
            organizationAnimalId = organizationAnimalId,
            organizationId = organizationId,
            photos = photoList,
            primaryPhotoCropped = primaryPhotoCroppedEntity?.run {
                PrimaryPhotoCropped(
                    full = full,
                    large = large,
                    medium = medium,
                    small = small,
                )
            },
            publishedAt = publishedAt,
            size = size,
            species = species,
            status = status,
            statusChangedAt = statusChangedAt,
            type = type,
            url = url,
        )
    }
}

fun Animal.getAnimalEntity(): AnimalEntity {
    return AnimalEntity(
        age = age,
        attributesEntity = attributes.run {
            AttributesEntity(
                declawed = declawed,
                houseTrained = houseTrained,
                shotsCurrent = shotsCurrent,
                spayedNeutered = spayedNeutered,
                specialNeeds = specialNeeds,
            )
        },
        breedsEntity = breeds.run {
            BreedsEntity(
                mixed,
                primary,
                secondary,
                unknown,
            )
        },
        contactEntity = contact.run {
            ContactEntity(
                addressEntity = address.run {
                    ContactEntity.AddressEntity(
                        address = address,
                        city = city,
                        country = country,
                        postcode = postcode,
                        state = state,
                    )
                },
                email = email,
                phone = phone,
            )
        },
        description = description,
        gender = gender,
        id = id,
        linksEntity = links.run {
            LinksEntity(
                organizationEntity = organization.run {
                    LinksEntity.OrganizationEntity(
                        href = href
                    )
                },
                selfEntity = self.run {
                    LinksEntity.SelfEntity(
                        href = href
                    )
                },
                typeEntity = type.run {
                    LinksEntity.TypeEntity(
                        href = href
                    )
                },
            )
        },
        name = name,
        organizationAnimalId = organizationAnimalId,
        organizationId = organizationId,
        primaryPhotoCroppedEntity = primaryPhotoCropped?.run {
            PrimaryPhotoCroppedEntity(
                full = full,
                large = large,
                medium = medium,
                small = small,
            )
        },
        publishedAt = publishedAt,
        size = size,
        species = species,
        status = status,
        statusChangedAt = statusChangedAt,
        type = type,
        url = url,
    )
}

fun Animal.getPhotoEntityList(animalId: Long): List<PhotoEntity> {
    return this.photos.map { photo ->
        photo.toEntity(animalId = animalId)
    }
}
