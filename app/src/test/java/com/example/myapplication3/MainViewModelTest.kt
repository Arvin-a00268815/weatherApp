package com.example.myapplication3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.example.myapplication3.network.retrofit.Repository
import com.example.myapplication3.ui.main.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
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



    @Before
    fun init(){
        viewModel = MainViewModel(savedStateHandle, repository)
    }

    @Test
    fun `test fetchWeatherDetails Method`(){



        Mockito.`when`(weather.temp).thenReturn(36.0F)
        Mockito.`when`(weather.humidity).thenReturn(40)
        Mockito.`when`(weather.pressure).thenReturn(50)
        Mockito.`when`(weatherHelper.weather).thenReturn(weather)

        Mockito.`when`(coordinates.lat).thenReturn("23")
        Mockito.`when`(coordinates.long).thenReturn("33")
        Mockito.`when`(weatherHelper.coordinates).thenReturn(coordinates)

        Mockito.`when`(clouds.cloudiness).thenReturn(63)
        Mockito.`when`(weatherHelper.clouds).thenReturn(clouds)
        Mockito.`when`(weatherHelper.name).thenReturn("City1")


        Mockito.`when`(weatherLiveData.value).thenReturn(weatherHelper)

        Mockito.`when`(repository.getCurrentWeather()).thenReturn(weatherLiveData)

        viewModel.fetchWeatherDetails().observeForever(observer)


        Assert.assertNotNull(viewModel.fetchWeatherDetails().value)

        Assert.assertEquals("City1", viewModel.getWeatherDetails().value?.name)

        Assert.assertEquals(36.0F, viewModel.getWeatherDetails().value?.weather!!.temp)
        Assert.assertEquals(40, viewModel.getWeatherDetails().value?.weather!!.humidity)
        Assert.assertEquals(50, viewModel.getWeatherDetails().value?.weather!!.pressure)

        Assert.assertEquals("23", viewModel.getWeatherDetails().value?.coordinates!!.lat)
        Assert.assertEquals("33", viewModel.getWeatherDetails().value?.coordinates!!.long)

        Assert.assertEquals(63, viewModel.getWeatherDetails().value?.clouds!!.cloudiness)


    }

    @Test
    fun `test getStandardDev Method`(){


        var sdMutableLiveData = mock(MutableLiveData::class.java) as MutableLiveData<Double>

        Mockito.`when`(sdMutableLiveData.value).thenReturn(3.85)
        Mockito.`when`(repository.getSD()).thenReturn(sdMutableLiveData)


        var observer = mock(Observer::class.java) as Observer<Double>

        viewModel.fetchStandardDev().observeForever(observer)

        Assert.assertNotNull(viewModel.getStandardDev().value)
        val precision = 0.01
        Assert.assertEquals(3.85, viewModel.getStandardDev().value as Double, precision)

    }


}
