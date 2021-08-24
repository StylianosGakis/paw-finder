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

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.stylianosgakis.pawfinder.api.TokenApi
import com.stylianosgakis.pawfinder.api.authenticator.TokenAuthenticator
import com.stylianosgakis.pawfinder.data.authentication.TokenRepository
import com.stylianosgakis.pawfinder.data.authentication.TokenRepositoryImpl
import com.stylianosgakis.pawfinder.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {
    private const val RETROFIT_FOR_AUTHENTICATION = "RETROFIT_FOR_AUTHENTICATION"
    private const val OKHTTP_FOR_AUTHENTICATION = "OKHTTP_FOR_AUTHENTICATION"

    @Singleton
    @Provides
    @Named(OKHTTP_FOR_AUTHENTICATION)
    fun provideAuthenticatingOkHttpClient(
        @Named(NetworkModule.LOGGING_INTERCEPTOR_QUALIFIER) loggingInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .build()

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    @Named(RETROFIT_FOR_AUTHENTICATION)
    fun provideAuthenticatingRetrofit(
        @Named(NetworkModule.API_URL_QUALIFIER) baseApiUrl: String,
        jsonSerializer: Json,
        @Named(OKHTTP_FOR_AUTHENTICATION) client: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseApiUrl)
            .addConverterFactory(
                jsonSerializer.asConverterFactory("application/json".toMediaType())
            )
            .client(client)
            .build()

    @Suppress("RemoveExplicitTypeArguments")
    @Singleton
    @Provides
    fun provideTokenApi(
        @Named(RETROFIT_FOR_AUTHENTICATION) retrofit: Retrofit,
    ): TokenApi =
        retrofit.create<TokenApi>()

    @Singleton
    @Provides
    fun provideTokenRepository(
        tokenApi: TokenApi,
        sessionManager: SessionManager,
    ): TokenRepository =
        TokenRepositoryImpl(
            tokenApi,
            sessionManager
        )

    @Singleton
    @Provides
    fun providePetFinderAuthenticator(
        tokenRepository: TokenRepository,
    ): Authenticator =
        TokenAuthenticator(tokenRepository)
}
