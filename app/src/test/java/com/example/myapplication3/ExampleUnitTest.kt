package com.example.myapplication3

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkStandardDeviation(){

        val values = ArrayList<Float>()
        values.add(16.83F)
        values.add(11.15F)
        values.add(14.2F)
        values.add(9.88F)
        values.add(19.19F)





        assertEquals(StandardDeviationCalculator.calculate(values), 3.865, 0.01)



    }
}
