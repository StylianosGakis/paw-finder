package com.stylianosgakis.androiddevchallengeweek1.api.model.animal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Links(
    val organization: Organization,
    val self: Self,
    val type: Type,
) : Parcelable {
    @Parcelize
    @Serializable
    data class Organization(
        val href: String,
    ) : Parcelable

    @Parcelize
    @Serializable
    data class Self(
        val href: String,
    ) : Parcelable

    @Parcelize
    @Serializable
    data class Type(
        val href: String,
    ) : Parcelable
}