package com.stylianosgakis.androiddevchallengeweek1.api

import com.stylianosgakis.androiddevchallengeweek1.api.model.token.TokenRequest
import com.stylianosgakis.androiddevchallengeweek1.api.model.token.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApi {
    @POST("oauth2/token")
    suspend fun getToken(
        @Body tokenRequest: TokenRequest = TokenRequest.create(),
    ): TokenResponse
}