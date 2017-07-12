package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by adriano on 12-7-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherForecasterAdapterTest {

    @InjectMocks
    WeatherForecasterAdapter weatherForecaster;
    @Mock
    Forecaster forecaster;
    @Mock
    Forecast forecast;

    @Before
    public void setUp() throws Exception {
        when(forecaster.forecastFor(Region.EDINBURGH, Day.MONDAY)).thenReturn(forecast);
    }

    @Test
    public void testRetrieveForecast() throws Exception {
        Forecast weatherForecastFor = weatherForecaster.weatherForecastFor(Region.EDINBURGH, Day.MONDAY);
        assertEquals(forecast, weatherForecastFor);
    }
}
