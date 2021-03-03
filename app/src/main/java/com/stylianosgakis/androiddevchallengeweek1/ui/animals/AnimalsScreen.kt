package com.stylianosgakis.androiddevchallengeweek1.ui.animals

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.FilterAlt
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.stylianosgakis.androiddevchallengeweek1.api.model.animal.Animal
import com.stylianosgakis.androiddevchallengeweek1.components.AnimalCard
import com.stylianosgakis.androiddevchallengeweek1.components.ExtendedFab
import com.stylianosgakis.androiddevchallengeweek1.data.AnimalType
import com.stylianosgakis.androiddevchallengeweek1.theme.bottomSheetShape
import com.stylianosgakis.androiddevchallengeweek1.ui.FindMyPetAppBar
import com.stylianosgakis.androiddevchallengeweek1.util.isScrollingForwardsAsState
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun AnimalsScreen(
    animalList: List<Animal>,
    selectedAnimalType: AnimalType,
    setSelectedAnimalType: (AnimalType) -> Unit,
    goToDetailsScreen: (Animal) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val isScrollingForwards by lazyListState.isScrollingForwardsAsState()

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { FindMyPetAppBar() },
        scaffoldState = scaffoldState,
    ) { paddingValues ->
        val modalSheetState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        ModalBottomSheetLayout(
            sheetState = modalSheetState,
            sheetContent = {
                AnimalTypePickerSheet(
                    selectedAnimalType = selectedAnimalType,
                    setSelectedAnimalType = { animalType ->
                        scope.launch {
                            modalSheetState.hide()
                        }
                        setSelectedAnimalType(animalType)
                    },
                )
            },
            sheetShape = bottomSheetShape(),
            sheetBackgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                var fabHeight by remember { mutableStateOf(0) }

                if (animalList.isEmpty()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(
                            BiasAlignment(verticalBias = -0.4f, horizontalBias = 0f)
                        )

                    )
                }

                LazyColumn(
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier)
                    }
                    itemsIndexed(
                        items = animalList,
                        key = { _, animal -> animal.id }
                    ) { index: Int, animal: Animal ->
                        AnimalCard(
                            cardIndex = index,
                            animal = animal,
                            goToDetailsScreen = { goToDetailsScreen(animal) },
                        )
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .height(fabHeight.dp) //TODO make the space follow the fab size properly
                                .border(width = 1.dp, color = Color.Red)
                        )
                    }
                }

                FilterAnimalTypeButton(
                    onClick = {
                        scope.launch {
                            modalSheetState.show()
                        }
                    },
                    visible = !modalSheetState.isVisible
                        && (animalList.isEmpty() || isScrollingForwards),
                    modifier = Modifier
                        .onSizeChanged {
                            fabHeight = it.height
                        }
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding(),
                )
            }
        }
    }
}

@Composable
private fun FilterAnimalTypeButton(
    onClick: () -> Unit,
    visible: Boolean,
    modifier: Modifier = Modifier,
) {
    val transition: Transition<Boolean> = updateTransition(targetState = visible)

    val alpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 280, easing = FastOutSlowInEasing) },
    ) { isVisible ->
        when (isVisible) {
            true -> 1f
            false -> 0f
        }
    }

    val translationY by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 280, easing = FastOutSlowInEasing) }
    ) { isVisible ->
        when (isVisible) {
            true -> 0f
            false -> with(LocalDensity.current) { 40.dp.toPx() }
        }
    }

    Box(
        modifier = modifier
            .graphicsLayer(
                clip = false,
                alpha = alpha,
                translationY = translationY,
            )
    ) {
        ExtendedFab(
            onClick = onClick,
            text = "CHANGE ANIMAL TYPE",
            imageVector = Icons.TwoTone.FilterAlt
        )
    }
}