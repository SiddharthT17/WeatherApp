package com.peramal.weatherapplication.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.peramal.weatherapplication.screens.SearchScreen
import com.peramal.weatherapplication.screens.WeatherScreen
import com.peramal.weatherapplication.viewmodel.WeatherViewModel

@Composable
fun MainAppNavigation(viewModel: WeatherViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "search") {
        composable("search") { SearchScreen(navController, viewModel) }
        composable("weather/{city}") { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("city") ?: ""
            WeatherScreen(navController, viewModel, cityName)
        }
    }
}
