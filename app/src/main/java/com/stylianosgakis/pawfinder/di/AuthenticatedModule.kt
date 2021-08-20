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
import com.stylianosgakis.pawfinder.api.PetFinderApi
import com.stylianosgakis.pawfinder.api.authenticator.ExistingTokenAppendingInterceptor
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
object AuthenticatedModule {
    private const val RETROFIT = "RETROFIT"
    private const val OKHTTP = "OKHTTP"
    private const val EXISTING_TOKEN_APPENDING_INTERCEPTOR = "EXISTING_TOKEN_APPENDING_INTERCEPTOR"

    @Singleton
    @Provides
    @Named(EXISTING_TOKEN_APPENDING_INTERCEPTOR)
    fun provideExistingTokenAppendingInterceptor(
        sessionManager: SessionManager,
    ): Interceptor =
        ExistingTokenAppendingInterceptor(sessionManager)

    @Singleton
    @Provides
    @Named(OKHTTP)
    fun providePetFinderOkHttpClient(
        @Named(NetworkModule.LOGGING_INTERCEPTOR_QUALIFIER) loggingInterceptor: Interceptor,
        @Named(EXISTING_TOKEN_APPENDING_INTERCEPTOR) existingTokenAppendingInterceptor: Interceptor,
        petFinderAuthenticator: Authenticator,
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(existingTokenAppendingInterceptor)
            .authenticator(petFinderAuthenticator)
            .build()

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    @Named(RETROFIT)
    fun provideRetrofit(
        @Named(NetworkModule.API_URL_QUALIFIER) baseApiUrl: String,
        @Named(OKHTTP) client: OkHttpClient,
        jsonSerializer: Json,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseApiUrl)
            .addConverterFactory(jsonSerializer.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()

    @Suppress("RemoveExplicitTypeArguments")
    @Singleton
    @Provides
    fun providePetFinderApi(
        @Named(RETROFIT) retrofit: Retrofit,
    ): PetFinderApi =
        retrofit.create<PetFinderApi>()
}
