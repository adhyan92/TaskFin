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
                onNavigateToLogin = {
                    navController.navigate("login_screen")
                }
            )
        }

        composable("login_screen") {
            LoginScreen(
                modifier = Modifier,
                onEnterClick = {
                    navController.navigate("dashboard_screen") {
                    }
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