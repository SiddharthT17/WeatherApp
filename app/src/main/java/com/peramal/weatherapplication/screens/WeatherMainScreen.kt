package com.peramal.weatherapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.peramal.weatherapplication.network.WeatherResponse
import com.peramal.weatherapplication.viewmodel.WeatherState
import com.peramal.weatherapplication.viewmodel.WeatherViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.peramal.weatherapplication.common.ApiConfig.API_KEY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(navController: NavHostController, viewModel: WeatherViewModel, cityName: String) {
    val weatherState = viewModel.weatherState.value

    LaunchedEffect(Unit) {
        viewModel.fetchWeather(cityName, API_KEY)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("$cityName weather") },
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()}
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (weatherState) {
                is WeatherState.Loading -> CircularProgressIndicator()
                is WeatherState.Success -> WeatherDetails(weather = weatherState.data)
                is WeatherState.Error -> Text("Error: ${weatherState.message}")
                WeatherState.Empty -> Text("Enter a city to view weather")
            }
        }

    }

}

@Composable
fun WeatherDetails(weather: WeatherResponse) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Weather in ${weather.name}", style = MaterialTheme.typography.headlineMedium)
        Text(text = "${weather.main.temp}°C")
        Text(text = "Humidity: ${weather.main.humidity}%")
        Text(text = "Pressure: ${weather.main.pressure} hPa")
        if (weather.weather.isNotEmpty()) {
            Text(text = "Condition: ${weather.weather[0].description}")
            val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png"
            // Load image from URL using Coil
            val painter = rememberAsyncImagePainter(imageUrl)
            //val painter = painterResource(id = R.drawable.ic_sample)
            Icon(
                painter = painter,
                contentDescription = "Weather Icon",
                modifier = Modifier.size(64.dp)
            )
        }
    }
}
