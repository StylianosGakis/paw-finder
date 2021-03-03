package com.stylianosgakis.androiddevchallengeweek1.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.viewModel(
    key: String? = null,
): T =
    viewModel(
        key = key,
        factory = HiltViewModelFactory(
            context = LocalContext.current,
            navBackStackEntry = this
        )
    )