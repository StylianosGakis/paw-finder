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
package xyz.stylianosgakis.database.data.animal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import xyz.stylianosgakis.database.data.animal.model.AnimalEntity
import xyz.stylianosgakis.database.data.animal.model.relations.AnimalWithPhotos
import xyz.stylianosgakis.database.data.animal.model.PhotoEntity

@Dao
interface AnimalDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimalEntity(animal: AnimalEntity): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotoEntity(photos: List<PhotoEntity>)

    @Transaction
    @Query("SELECT * FROM animal WHERE id=:id LIMIT 1")
    suspend fun get(id: Long): List<AnimalWithPhotos?>
}