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
package com.stylianosgakis.pawfinder.api

import com.stylianosgakis.pawfinder.api.model.animal.GetAllAnimalsResponse
import com.stylianosgakis.pawfinder.api.model.animal.GetOneAnimalsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PetFinderApi {
    @GET("animals")
    suspend fun searchAnimals(
        @Query("type") animalType: String,
        @Query("page") page: Int,
    ): GetAllAnimalsResponse

    @GET("animals/{id}")
    suspend fun searchAnimal(
        @Path("id") id: Int
    ): GetOneAnimalsResponse
}