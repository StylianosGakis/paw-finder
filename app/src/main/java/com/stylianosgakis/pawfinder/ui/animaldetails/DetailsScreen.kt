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
package com.stylianosgakis.pawfinder.ui.animaldetails

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Pets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.stylianosgakis.pawfinder.api.animal.model.Animal
import com.stylianosgakis.pawfinder.components.ExtendedFab
import com.stylianosgakis.pawfinder.components.LoadingScreen
import com.stylianosgakis.pawfinder.theme.AppTheme
import com.stylianosgakis.pawfinder.util.exhaustive
import com.stylianosgakis.pawfinder.util.previewAnimal

@Composable
fun DetailsScreen() {

    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    DisposableEffect(Unit) {
        val window = (context as? Activity)?.window
        val oldColor = window?.statusBarColor
        systemUiController.setStatusBarColor(Color.Transparent, true)
        onDispose {
            if (oldColor != null) {
                window.statusBarColor = oldColor
            }
        }
    }

    val viewModel: DetailsScreenViewModel = hiltViewModel()
    val viewState: DetailsScreenViewState by viewModel.viewStateFlow.collectAsState()

    DetailsScreen(viewState)
}

@Composable
private fun DetailsScreen(detailsScreenViewState: DetailsScreenViewState) {
    when (detailsScreenViewState) {
        DetailsScreenViewState.Loading -> {
            Box(Modifier.fillMaxSize()) {
                LoadingScreen()
            }
        }
        is DetailsScreenViewState.Loaded -> {
            DetailsScreen(detailsScreenViewState.animal)
        }
    }.exhaustive
}

@Composable
private fun DetailsScreen(animal: Animal) {

    var selectedImageIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            PhotoSection(
                animal = animal,
                selectedImageIndex = selectedImageIndex,
                setSelectedImageIndex = {
                    selectedImageIndex = it
                },
                modifier = Modifier
                    .weight(0.45f)
                    .clip(
                        RoundedCornerShape(24.dp).copy(
                            topStart = ZeroCornerSize,
                            topEnd = ZeroCornerSize
                        )
                    )
            )
            DetailsSection(
                animal = animal,
                modifier = Modifier.weight(0.55f)
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
        ) {
            ExtendedFab(
                onClick = {},
                text = "ADOPT ME!",
                imageVector = Icons.TwoTone.Pets
            )
        }
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colors.background,
        ) {
            DetailsScreen(previewAnimal)
        }
    }
}
