package com.stylianosgakis.androiddevchallengeweek1.api.model.token

import com.stylianosgakis.androiddevchallengeweek1.BuildConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TokenRequest private constructor(
    @SerialName("grant_type")
    val grantType: String,
    @SerialName("client_id")
    val clientId: String,
    @SerialName("client_secret")
    val clientSecret: String,
) {
    companion object {
        fun create(): TokenRequest =
            TokenRequest(
                grantType = "client_credentials",
                clientId = BuildConfig.PET_FINDER_CLIENT_ID,
                clientSecret = BuildConfig.PET_FINDER_CLIENT_SECRET,
            )
    }
}