package com.stylianosgakis.pawfinder.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.stylianosgakis.database.data.PawFinderDatabase
import xyz.stylianosgakis.database.data.animal.AnimalDao

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DatabaseModuleDependencies {

    fun pawFinderDatabase(): PawFinderDatabase

    fun animalDao(): AnimalDao
}
