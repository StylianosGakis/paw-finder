package com.stylianosgakis.androiddevchallengeweek1.api

import com.stylianosgakis.androiddevchallengeweek1.api.model.animal.PetFinderAnimalsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PetFinderApi {
    @GET("animals")
    suspend fun searchAnimals(
        @Query("type") animalType: String,
        @Query("page") page: Int,
    ): PetFinderAnimalsResponse
}

