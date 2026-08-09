package com.example.taskfin

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavApp(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass
) {

    val profileViewModel: ProfileViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash_screen",
        modifier = modifier
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
                    navController.navigate("dashboard_screen") {
                        popUpTo("splash_screen") { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register_screen")
                }
            )
        }

        composable("register_screen") {
            RegisterScreen(
                modifier = Modifier,
                viewModel = profileViewModel,
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
                viewModel = profileViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveClick = {
                    navController.navigate("profile_screen") {
                        popUpTo("splash_screen") { inclusive = true }
                    }
                }
            )
        }

        composable("dashboard_screen") {
            DashboardScreen(
                modifier = Modifier,
                navController = navController,
                onSettingsClick = {
                    navController.navigate("settings_screen")
                }
            )
        }

        composable("settings_screen") {
            SettingsScreen(
                modifier = Modifier,
                viewModel = profileViewModel,
                onBackClick = {
                    navController.navigate("dashboard_screen")
                },
                onEditProfileClick = {
                    navController.navigate("profile_screen")
                }
            )
        }

        composable("profile_screen") {
            ProfileScreen(
                modifier = Modifier,
                navController = navController,
                viewModel = profileViewModel,
                onSettingsClick = {
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("edit_profile") {
            EditProfileScreen(
                viewModel = profileViewModel,
                onSaveSuccess = { navController.popBackStack() }
            )
        }
    }
}