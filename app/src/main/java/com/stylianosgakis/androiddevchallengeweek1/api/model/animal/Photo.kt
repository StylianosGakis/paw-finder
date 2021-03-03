package com.stylianosgakis.androiddevchallengeweek1.api.model.animal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Photo(
    val full: String,
    val large: String,
    val medium: String,
    val small: String,
) : Parcelable