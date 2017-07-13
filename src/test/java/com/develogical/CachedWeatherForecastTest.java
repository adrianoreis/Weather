package com.develogical;

import com.sun.org.apache.regexp.internal.RE;
import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.TemporalUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by adriano on 13-7-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CachedWeatherForecastTest {
    @InjectMocks
    private CachedWeatherForecaster cachedWeatherForecaster;
    @Mock
    private WeatherForecaster weatherForecaster;
    @Mock
    private Forecast forecast;
    @Mock
    private Clock clock;

    @Before
    public void setUp() throws Exception {
        when(weatherForecaster.weatherForecastFor(Region.LONDON, Day.FRIDAY)).thenReturn(forecast);
    }

    @Test
    public void testRetrieveForecast() throws Exception {
        when(clock.instant()).thenReturn(Instant.now());
        Forecast forecastFor = cachedWeatherForecaster.weatherForecastFor(Region.LONDON, Day.FRIDAY);
        assertEquals(forecast, forecastFor);
    }

    @Test
    public void testRetrieveForecastFromCache() throws Exception {
        when(clock.instant()).thenReturn(Instant.now(), Instant.now().plusSeconds(60));
        cachedWeatherForecaster.weatherForecastFor(Region.LONDON, Day.FRIDAY);
        cachedWeatherForecaster.weatherForecastFor(Region.LONDON, Day.FRIDAY);
        verify(weatherForecaster).weatherForecastFor(Region.LONDON, Day.FRIDAY);
    }

    @Test
    public void testCacheIsResetAfterThreeEntries() throws Exception {
        when(clock.instant()).thenReturn(Instant.now());
        cachedWeatherForecaster.weatherForecastFor(Region.LONDON, Day.FRIDAY);
        cachedWeatherForecaster.weatherForecastFor(Region.LONDON, Day.SATURDAY);
        cachedWeatherForecaster.weatherForecastFor(Region.LONDON, Day.SUNDAY);
        cachedWeatherForecaster.weatherForecastFor(Region.LONDON, Day.MONDAY);
        cachedWeatherForecaster.weatherForecastFor(Region.LONDON, Day.FRIDAY);
        verify(weatherForecaster, times(2)).weatherForecastFor(Region.LONDON, Day.FRIDAY);
    }

    @Test
    public void testCachedValueExpiresAfterOneHour() throws Exception {

        Instant now = Instant.now();
        when(clock.instant()).thenReturn(now,now.plusSeconds(1800),now.plusSeconds(3601));
        cachedWeatherForecaster.weatherForecastFor(Region.LONDON, Day.FRIDAY);
        cachedWeatherForecaster.weatherForecastFor(Region.LONDON, Day.FRIDAY);
        cachedWeatherForecaster.weatherForecastFor(Region.LONDON, Day.FRIDAY);
        verify(weatherForecaster, times(2)).weatherForecastFor(Region.LONDON, Day.FRIDAY);
    }
}
