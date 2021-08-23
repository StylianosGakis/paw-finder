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

import com.stylianosgakis.pawfinder.data.authentication.TokenRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val tokenRepository: TokenRepository,
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(
        route: Route?,
        response: Response,
    ): Request? {
        return try {
            return runBlocking {
                val token: String? = tokenRepository.getToken()
                return@runBlocking mutex.withLock {
                    val newToken: String? = tokenRepository.getToken()
                    if (newToken != null && token != newToken) {
                        // It was refreshed, so go ahead and just make the call
                        return@withLock response.request.withAuthenticationToken(newToken)
                    }

                    // Still same old token, so fetch new one
                    val refreshedToken = tokenRepository.refreshAndGetToken()
                    return@withLock response.request.withAuthenticationToken(refreshedToken)
                }
            }
        } catch (e: Exception) {
            null
        }
    }
}
