package com.stylianosgakis.androiddevchallengeweek1.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.stylianosgakis.androiddevchallengeweek1.api.PetFinderApi
import com.stylianosgakis.androiddevchallengeweek1.api.authenticator.ExistingTokenAppendingInterceptor
import com.stylianosgakis.androiddevchallengeweek1.data.PetFinderRepository
import com.stylianosgakis.androiddevchallengeweek1.session.SessionManager
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

    @Singleton
    @Provides
    fun providePetFinderRepository(
        petFinderApi: PetFinderApi,
    ): PetFinderRepository =
        PetFinderRepository(petFinderApi)
}