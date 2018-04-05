package com.chen.kevin.simpleweather.data.weather;

import java.util.List;

/**
 * Created by Ms Chen on 2018/4/4.
 */

public class Forecast {
    private CurrentWeather mCurrentWeather;
    private List<WeatherDaily> mDailyForecast;

    public Forecast(CurrentWeather currentWeather, List<WeatherDaily> dailyForecast) {
        mCurrentWeather = currentWeather;
        mDailyForecast = dailyForecast;
    }

    public CurrentWeather getCurrentWeather() {
        return mCurrentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        mCurrentWeather = currentWeather;
    }

    public List<WeatherDaily> getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(List<WeatherDaily> dailyForecast) {
        mDailyForecast = dailyForecast;
    }
}
