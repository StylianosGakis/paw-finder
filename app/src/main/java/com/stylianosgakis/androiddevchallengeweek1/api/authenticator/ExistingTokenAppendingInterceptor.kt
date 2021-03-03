package com.stylianosgakis.androiddevchallengeweek1.api.authenticator

import com.stylianosgakis.androiddevchallengeweek1.session.SessionManager
import com.stylianosgakis.androiddevchallengeweek1.util.withAuthenticationToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.Interceptor
import okhttp3.Response

class ExistingTokenAppendingInterceptor(
    sessionManager: SessionManager,
) : Interceptor {
    private var currentToken: String? = null

    init {
        sessionManager.getTokenFlow()
            .onEach { token ->
                currentToken = token
            }
            .launchIn(GlobalScope) // TODO Pass appropriate scope? Handle this differently?
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            currentToken?.let { token ->
                chain.request().withAuthenticationToken(token)
            } ?: chain.request()
        )
    }
}