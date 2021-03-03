package com.stylianosgakis.androiddevchallengeweek1.api.model.animal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Attributes(
    val declawed: Boolean?,
    @SerialName("house_trained")
    val houseTrained: Boolean,
    @SerialName("shots_current")
    val shotsCurrent: Boolean,
    @SerialName("spayed_neutered")
    val spayedNeutered: Boolean,
    @SerialName("special_needs")
    val specialNeeds: Boolean,
) : Parcelable