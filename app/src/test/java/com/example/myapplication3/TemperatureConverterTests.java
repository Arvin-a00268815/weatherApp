package com.example.myapplication3;

import org.junit.Assert;
import org.junit.Test;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TemperatureConverterTests {
    @Test
    public void testCelsiusToFahrenheitConversion() {
        final double delta = 0.01;


        Assert.assertEquals(TemperatureConverter.celsiusToFahrenheit(-50),-58, delta);
        Assert.assertEquals(TemperatureConverter.celsiusToFahrenheit(0), 32, delta);
        Assert.assertEquals(TemperatureConverter.celsiusToFahrenheit(10), 50, delta);
        Assert.assertEquals(TemperatureConverter.celsiusToFahrenheit(21.11f), 70, delta);
        Assert.assertEquals(TemperatureConverter.celsiusToFahrenheit(37.78f), 100, delta);
        Assert.assertEquals(TemperatureConverter.celsiusToFahrenheit(100), 212, delta);
        Assert.assertEquals(TemperatureConverter.celsiusToFahrenheit(1000), 1832, delta);
    }
}
