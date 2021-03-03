package com.stylianosgakis.androiddevchallengeweek1.api.model.animal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Contact(
    val address: Address,
    val email: String?,
    val phone: String?,
) : Parcelable {
    @Parcelize
    @Serializable
    data class Address(
        @SerialName("address1")
        val address: String?,
        val city: String,
        val country: String,
        val postcode: String?,
        val state: String,
    ) : Parcelable
}