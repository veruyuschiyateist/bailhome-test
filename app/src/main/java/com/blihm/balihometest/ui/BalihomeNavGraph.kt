package com.blihm.balihometest.ui

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.blihm.balihometest.di.ViewModelFactoryProvider
import com.blihm.balihometest.ui.home.HomeScreen
import com.blihm.balihometest.ui.home.HomeViewModel
import com.blihm.balihometest.ui.repositories.UserRepositoriesViewModel
import dagger.hilt.android.EntryPointAccessors

@Composable
fun BalihomeNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = BalihomeNavigation.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = BalihomeNavigation.HOME_ROUTE
        ) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = homeViewModel
            ) { login ->
                navController.navigate(BalihomeNavigation.REPOSITORIES_ROUTE + "/$login")
            }
        }
        composable(
            route = BalihomeNavigation.REPOSITORIES_ROUTE + "/{userLogin}",
            arguments = listOf(
                navArgument(name = "userLogin") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val login = entry.arguments?.getString("userLogin", "") ?: ""
            val repositoriesViewModel = userRepositoriesViewModel(login = login)

            repositoriesViewModel.logLogin()
        }
    }
}

@Composable
private fun userRepositoriesViewModel(login: String): UserRepositoriesViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).repositoriesViewModelFactory()

    return viewModel(factory = UserRepositoriesViewModel.provideFactory(factory, login))
}