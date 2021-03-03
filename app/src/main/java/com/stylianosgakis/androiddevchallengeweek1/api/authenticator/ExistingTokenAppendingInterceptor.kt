/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
