package com.example.myapplication3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.example.myapplication3.network.retrofit.Repository
import com.example.myapplication3.ui.main.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @InjectMocks
    lateinit var viewModel: MainViewModel

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var savedStateHandle: SavedStateHandle

    @Mock
    lateinit var observer: Observer<WeatherHelper>

    @Mock
    lateinit var weatherLiveData : MutableLiveData<WeatherHelper>

    @Mock
    lateinit var weatherHelper: WeatherHelper

    @Mock
    lateinit var weather: Weather

    @Mock
    lateinit var coordinates : Coordinates

    @Mock
    lateinit var clouds : Clouds

//
//    @Mock
//    lateinit var repository: Repository
//
//    @Mock
//    lateinit var savedStateHandle: SavedStateHandle

    @Test
    fun `test name in GetWeatherDetails Method`(){

        viewModel = MainViewModel(savedStateHandle, repository)

        Mockito.`when`(weatherHelper.name).thenReturn("Chennai")
        Mockito.`when`(weatherLiveData.value).thenReturn(weatherHelper)
        Mockito.`when`(repository.getCurrentWeather()).thenReturn(weatherLiveData)

        viewModel.getWeatherDetails().observeForever(observer)

        Assert.assertNotNull(viewModel.getWeatherDetails().value)
        Assert.assertEquals("Chennai", viewModel.getWeatherDetails().value?.name)


    }

    @Test
    fun `test weather in GetWeatherDetails Method`(){

        viewModel = MainViewModel(savedStateHandle, repository)


        Mockito.`when`(weather.temp).thenReturn(36.0F)

        Mockito.`when`(weather.humidity).thenReturn(40)

        Mockito.`when`(weather.pressure).thenReturn(50)
        Mockito.`when`(weatherHelper.weather).thenReturn(weather)
        Mockito.`when`(weatherLiveData.value).thenReturn(weatherHelper)
        Mockito.`when`(repository.getCurrentWeather()).thenReturn(weatherLiveData)

        viewModel.getWeatherDetails().observeForever(observer)

        Assert.assertNotNull(viewModel.getWeatherDetails().value)
        Assert.assertEquals(36.0F, viewModel.getWeatherDetails().value?.weather!!.temp)

        Assert.assertEquals(40, viewModel.getWeatherDetails().value?.weather!!.humidity)
        Assert.assertEquals(50, viewModel.getWeatherDetails().value?.weather!!.pressure)

    }


    @Test
    fun `test coordinates in GetWeatherDetails Method`(){

        viewModel = MainViewModel(savedStateHandle, repository)


        Mockito.`when`(coordinates.lat).thenReturn("23")

        Mockito.`when`(coordinates.long).thenReturn("33")

        Mockito.`when`(weatherHelper.coordinates).thenReturn(coordinates)
        Mockito.`when`(weatherLiveData.value).thenReturn(weatherHelper)
        Mockito.`when`(repository.getCurrentWeather()).thenReturn(weatherLiveData)

        viewModel.getWeatherDetails().observeForever(observer)

        Assert.assertNotNull(viewModel.getWeatherDetails().value)

        Assert.assertEquals("23", viewModel.getWeatherDetails().value?.coordinates!!.lat)
        Assert.assertEquals("33", viewModel.getWeatherDetails().value?.coordinates!!.long)

    }


    @Test
    fun `test clouds in GetWeatherDetails Method`(){

        viewModel = MainViewModel(savedStateHandle, repository)


        Mockito.`when`(clouds.cloudiness).thenReturn(63)


        Mockito.`when`(weatherHelper.clouds).thenReturn(clouds)
        Mockito.`when`(weatherLiveData.value).thenReturn(weatherHelper)
        Mockito.`when`(repository.getCurrentWeather()).thenReturn(weatherLiveData)

        viewModel.getWeatherDetails().observeForever(observer)

        Assert.assertNotNull(viewModel.getWeatherDetails().value)

        Assert.assertEquals(63, viewModel.getWeatherDetails().value?.clouds!!.cloudiness)

    }
}