package com.example.taskfin

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavApp(
    modifier: Modifier = Modifier
){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash_screen",
        modifier = Modifier
    ) {

        composable("splash_screen") {
            SplashScreen(
                modifier = Modifier,
                onNavigateToBoarding1 = {
                    navController.navigate("on_boarding1")
                }
            )
        }

        composable("on_boarding1") {
            onBoarding1(
                modifier = Modifier,
                onContinueClick = {
                    navController.navigate("on_boarding2")
                },
                onSkipClick = {
                    navController.navigate("login_screen")
                }
            )
        }

        composable("on_boarding2") {
            onBoarding2(
                modifier = Modifier,
                onContinueClick = {
                    navController.navigate("login_screen")
                },
                onSkipClick = {
                    navController.navigate("login_screen")
                }
            )
        }

        composable("login_screen") {
            LoginScreen(
                modifier = Modifier,
                onEnterClick = {
                    navController.navigate("dashboard_screen")
                },
                onRegisterClick = {
                    navController.navigate("register_screen") {
                    }
                }
            )
        }

        composable("register_screen") {
            RegisterScreen(
                modifier = Modifier,
                onRegisterNowClick = {
                    navController.navigate("input_data_diri")
                },
                onLoginClick = {
                    navController.navigate("login_screen")
                }
            )
        }

        composable("input_data_diri") {
            InputPersonalData(
                modifier = Modifier,
                onBackClick = {
                    navController.navigate("register_screen")
                }
            )
        }

        composable("dashboard_screen") {
            DashboardScreen(
                modifier = Modifier,
                navController = navController
            )
        }
    }
}