package com.stylianosgakis.androiddevchallengeweek1.api.model.animal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Breeds(
    val mixed: Boolean,
    val primary: String,
    val secondary: String?,
    val unknown: Boolean,
) : Parcelable