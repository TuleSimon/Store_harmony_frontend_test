package com.simon.storeharmonytest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simon.storeharmonytest.ui.navigation.navGraph.DefaultNavGraph
import com.simon.storeharmonytest.ui.navigation.navGraph.MyScreens
import com.simon.storeharmonytest.ui.theme.StoreHarmonyTestTheme
import com.simon.storeharmonytest.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

var keepSplashScreen = true

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        var startDestination:MyScreens = MyScreens.WelcomeScreen
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val mainViewModel: MainViewModel by viewModels()
        lifecycleScope.launch(Dispatchers.Default) {
            mainViewModel.userProfile.takeIf { keepSplashScreen }?.collectLatest {
                if (it == null) {
                    keepSplashScreen = false
                    Timber.d("No profile")
                    return@collectLatest
                } else {
                    startDestination = MyScreens.UserHomeScreen
                    keepSplashScreen = false
                    Timber.d("Profile")
                    return@collectLatest
                }
            }
        }
        setContent {
            val navController = rememberNavController()
            StoreHarmonyTestTheme {
                // A surface container using the 'background' color from the theme
                CompositionLocalProvider(LocalNavControllerProvider provides navController) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        DefaultNavGraph(navController = navController, startDestination)
                    }
                }
            }
        }
    }
}

//passing the navcontroller to all composables
val LocalNavControllerProvider = compositionLocalOf<NavHostController> {
    error("No NavController provided")
}
