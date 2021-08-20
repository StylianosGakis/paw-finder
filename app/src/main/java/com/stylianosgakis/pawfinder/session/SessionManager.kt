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
package com.stylianosgakis.pawfinder.session

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val AUTHENTICATION_PREFERENCES_NAME = "authentication_preferences"
private const val AUTHENTICATION_TOKEN_KEY = "authentication_token"

class SessionManager @Inject constructor(
    private val context: Context,
) {
    private val authenticationStringKey = stringPreferencesKey(AUTHENTICATION_TOKEN_KEY)

    private val Context.dataStore by preferencesDataStore(
        name = AUTHENTICATION_PREFERENCES_NAME
    )

    fun getTokenFlow(): Flow<String> =
        context.dataStore.data
            .map { preferences: Preferences ->
                preferences[authenticationStringKey] ?: ""
            }

    suspend fun getCurrentToken(): String {
        return getTokenFlow().first()
    }

    suspend fun setToken(
        token: String,
    ) {
        context.dataStore.edit { mutablePreferences: MutablePreferences ->
            mutablePreferences[authenticationStringKey] = token
        }
    }
}
