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
package com.stylianosgakis.pawfinder.di

import android.content.Context
import com.stylianosgakis.pawfinder.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val API_URL_QUALIFIER = "API_URL_QUALIFIER"
    const val LOGGING_INTERCEPTOR_QUALIFIER = "LOGGING_INTERCEPTOR_QUALIFIER"

    @Singleton
    @Provides
    @Named(API_URL_QUALIFIER)
    fun providePetFinderBaseApiRoute(): String = "https://api.petfinder.com/v2/"

    @Singleton
    @Provides
    fun provideSessionManager(
        @ApplicationContext context: Context,
    ): SessionManager =
        SessionManager(context)

    @Singleton
    @Provides
    @Named(LOGGING_INTERCEPTOR_QUALIFIER)
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

    @Singleton
    @Provides
    fun provideKotlinxSerializer(): Json =
        Json {
            ignoreUnknownKeys = true
        }
}
