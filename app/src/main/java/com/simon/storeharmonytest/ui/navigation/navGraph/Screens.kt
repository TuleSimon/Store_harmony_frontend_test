package com.simon.storeharmonytest.ui.navigation.navGraph

sealed class MyScreens(val route: String) {


    //--HOME---
    object RootScreen : MyScreens("root_screen")
    object WelcomeScreen : MyScreens("welcome_screen")
    object UserProfileScreen : MyScreens("my_profile")
    object ViewProductScreen : MyScreens("view_product")
    object UserHomeScreen : MyScreens("home")
    object UserCartScreen : MyScreens("cart")

}

