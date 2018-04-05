package com.chen.kevin.simpleweather.data;

import com.chen.kevin.simpleweather.data.weather.CurrentWeather;
import com.chen.kevin.simpleweather.data.weather.Forecast;
import com.chen.kevin.simpleweather.data.weather.WeatherDaily;

import java.util.List;

/**
 * Created by Ms Chen on 2018/4/1.
 */

public interface WeatherDataSource {
    interface GetForecastCallBack {
        void onForecastLoaded(Forecast forecast);
        void onDataNotAvailable();
    }

    void getForecast(GetForecastCallBack callBack);
    void refreshForecast(Forecast forecast);
}
