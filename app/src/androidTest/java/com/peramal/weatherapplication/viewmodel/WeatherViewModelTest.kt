package com.peramal.weatherapplication.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.peramal.weatherapplication.network.Main
import com.peramal.weatherapplication.network.WeatherResponse
import com.peramal.weatherapplication.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


@ExperimentalCoroutinesApi
class WeatherViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var viewModel: WeatherViewModel

    @Mock
    private lateinit var repository: WeatherRepository

    // Test dispatcher to run coroutines in the test environment
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Set up the ViewModel with a mocked repository
        viewModel = WeatherViewModel(repository)

        // Set the main dispatcher for testing
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test_fetchWeather_success`() = runTest {
        // Arrange
        val mockResponse = WeatherResponse(Main(1.0f, 5,3), listOf(),"test")
        val city = "Austin"
        val apiKey = "weatherApiKey"

        // Mock the repository's behavior
        Mockito.`when`(repository.getWeather(city, apiKey)).thenReturn(Result.success(mockResponse))

        // Act
        viewModel.fetchWeather(city, apiKey)
        testDispatcher.scheduler.advanceUntilIdle() // Ensure that coroutines are executed

        // Assert
        val state = viewModel.weatherState // Get the first emitted state
        assertEquals(WeatherState.Success(mockResponse), state)
    }

    @Test
    fun `test_getWeather_error_state`() = runTest {
        // Arrange
        val city = "Austin"
        val apiKey = "weatherApiKey"
        val errorMessage = "Network error"

        // Mock the repository's behavior to return an error
        Mockito.`when`(repository.getWeather(city, apiKey)).thenReturn(Result.failure(Exception(errorMessage)))

        // Act
        viewModel.fetchWeather(city, apiKey)
        testDispatcher.scheduler.advanceUntilIdle() // Ensure that coroutines are executed

        // Assert
        val state = viewModel.weatherState // Get the first emitted state
        assertEquals(WeatherState.Error(errorMessage), state)
    }
}

