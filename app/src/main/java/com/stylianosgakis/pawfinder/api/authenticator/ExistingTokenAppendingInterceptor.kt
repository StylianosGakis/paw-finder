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
package com.stylianosgakis.pawfinder.api.authenticator

import com.stylianosgakis.pawfinder.session.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class ExistingTokenAppendingInterceptor(
    private val sessionManager: SessionManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentToken = runBlocking {
            sessionManager.getCurrentToken()
        }
        return if (currentToken == null) {
            chain.proceed(chain.request())
        } else {
            chain.proceed(chain.request().withAuthenticationToken(currentToken))
        }
    }
}
