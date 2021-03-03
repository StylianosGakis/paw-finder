package com.stylianosgakis.androiddevchallengeweek1.api.model.token

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("access_token")
    val accessToken: String,
)