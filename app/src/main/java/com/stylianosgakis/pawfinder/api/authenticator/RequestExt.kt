package com.stylianosgakis.pawfinder.api.authenticator

import okhttp3.Request

private const val AUTHORIZATION_HEADER_NAME = "Authorization"

fun Request.withAuthenticationToken(
    token: String,
): Request {
    return this.newBuilder()
        .header(AUTHORIZATION_HEADER_NAME, "Bearer $token")
        .build()
}
