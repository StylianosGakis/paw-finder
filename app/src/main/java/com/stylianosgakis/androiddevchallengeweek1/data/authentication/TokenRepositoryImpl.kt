package com.stylianosgakis.androiddevchallengeweek1.data.authentication

import com.stylianosgakis.androiddevchallengeweek1.api.TokenApi
import com.stylianosgakis.androiddevchallengeweek1.session.SessionManager

class TokenRepositoryImpl(
    private val tokenApi: TokenApi,
    private val sessionManager: SessionManager,
) : TokenRepository {
    override suspend fun getToken(): String {
        val currentToken = sessionManager.getCurrentToken()
        return currentToken
    }

    override suspend fun refreshAndGetToken(): String {
        val refreshedToken = tokenApi.getToken().accessToken
        sessionManager.setToken(refreshedToken)
        return getToken()
    }
}