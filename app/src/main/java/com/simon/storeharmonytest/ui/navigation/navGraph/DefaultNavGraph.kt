package com.simon.storeharmonytest.ui.navigation.navGraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.simon.storeharmonytest.LocalNavControllerProvider
import com.simon.storeharmonytest.ui.screens.cart.CartScreen
import com.simon.storeharmonytest.ui.screens.homescreen.HomeScreen
import com.simon.storeharmonytest.ui.screens.profile.UserProfileScreen
import com.simon.storeharmonytest.ui.screens.viewProducts.ViewProductsScreen
import com.simon.storeharmonytest.ui.screens.welcome.WelcomeScreen
import com.simon.storeharmonytest.ui.viewmodels.MainViewModel

@Composable
fun DefaultNavGraph(navController: NavHostController, startDestination: MyScreens) {

    NavHost(
        navController = navController,
        route = MyScreens.RootScreen.route,
        startDestination = startDestination.route
    ) {

        composable(
            route = MyScreens.WelcomeScreen.route
        ) {
            WelcomeScreen(onGoToHome = {
                navController.navigate(MyScreens.UserHomeScreen.route) {
                    launchSingleTop = true
                    try {
                        navController.popBackStack(MyScreens.WelcomeScreen.route, true)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }) {
                navController.navigate(MyScreens.UserProfileScreen.route)
            }
        }

        composable(route = MyScreens.UserProfileScreen.route) {
            UserProfileScreen() {
                navController.navigateUp()
            }
        }
        composable(route = MyScreens.UserHomeScreen.route) {
            val mainViewModel: MainViewModel = it.getViewModel(route = MyScreens.RootScreen.route)
            HomeScreen(mainViewModel, navigateToProduct = {
                navController.navigate(MyScreens.ViewProductScreen.route)
            },
                navigateToCart = {
                    navController.navigate(MyScreens.UserCartScreen.route)
                }, navigateToProfile = {
                    navController.navigate(MyScreens.UserProfileScreen.route)
                })
        }

        composable(route = MyScreens.ViewProductScreen.route) {
            val mainViewModel: MainViewModel = it.getViewModel(route = MyScreens.RootScreen.route)
            ViewProductsScreen(mainViewModel,
                goToCart = {
                    navController.navigate(MyScreens.UserCartScreen.route)
                }) {
                navController.navigateUp()
            }
        }


        composable(route = MyScreens.UserCartScreen.route) {
            val mainViewModel: MainViewModel = it.getViewModel(route = MyScreens.RootScreen.route)
            CartScreen(mainViewModel,
                goToAddress = {
                    navController.navigate(MyScreens.UserProfileScreen.route)
                }) {
                navController.navigateUp()
            }
        }
    }


}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.getViewModel(route: String): T {
    val navController = LocalNavControllerProvider.current
    val parentEntry = remember(this) {
        navController.getBackStackEntry(route)
    }
    val viewModel = hiltViewModel<T>(parentEntry)
    return viewModel
}