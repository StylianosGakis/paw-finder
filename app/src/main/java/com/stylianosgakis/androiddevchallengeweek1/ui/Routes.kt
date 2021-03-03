package com.stylianosgakis.androiddevchallengeweek1.ui

sealed class Screen(
    val route: String,
) {
    object AnimalsScreen : Screen("Animals")

    object DetailsScreen : Screen("Details")
}