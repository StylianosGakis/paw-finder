package com.stylianosgakis.androiddevchallengeweek1.session

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