package com.stylianosgakis.androiddevchallengeweek1.api.model.animal

import android.os.Parcelable
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
// TODO remove Parcelable from Animal and pass it to the other composable just by ID
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
) : Parcelable {
    val simpleOneWordCapitalizedName: String
        get() = name.toLowerCase(Locale("en"))
            .split(" ")
            .first()
            .run {
                capitalize(java.util.Locale.ENGLISH)
            }
}