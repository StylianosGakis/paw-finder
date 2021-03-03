package com.stylianosgakis.androiddevchallengeweek1.data.authentication

interface TokenRepository {
    suspend fun getToken(): String
    suspend fun refreshAndGetToken(): String
}