package com.stylianosgakis.androiddevchallengeweek1

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.stylianosgakis.androiddevchallengeweek1.theme.AppTheme
import com.stylianosgakis.androiddevchallengeweek1.theme.navigationBarAndroidColor
import com.stylianosgakis.androiddevchallengeweek1.theme.statusBarAndroidColor
import com.stylianosgakis.androiddevchallengeweek1.ui.AppScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                EdgeToEdgeContent {
                    Surface(
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        AppScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun EdgeToEdgeContent(content: @Composable () -> Unit) {
    val view = LocalView.current
    val window = (LocalContext.current as Activity).window
    window.statusBarColor = statusBarAndroidColor.toArgb()
    window.navigationBarColor = navigationBarAndroidColor.toArgb()
    val insetsController = remember(view, window) {
        WindowCompat.getInsetsController(window, view)
    }
    val isLightTheme = MaterialTheme.colors.isLight
    insetsController?.run {
        isAppearanceLightNavigationBars = isLightTheme
        isAppearanceLightStatusBars = isLightTheme
    }
    ProvideWindowInsets(content = content)
}