package com.chen.kevin.simpleweather.data;

import android.content.Context;
import android.location.Location;

import com.chen.kevin.simpleweather.data.weather.CurrentWeather;
import com.chen.kevin.simpleweather.data.weather.Forecast;
import com.chen.kevin.simpleweather.data.weather.WeatherDaily;
import com.chen.kevin.simpleweather.util.WeatherFetch;

import java.util.List;

/**
 * Created by Ms Chen on 2018/4/2.
 */

public class WeatherRemoteDataSource implements WeatherDataSource {
    private WeatherFetch mWeatherFetch;
    private Context mContext;
    private double mLatitude;
    private double mLongitude;

    public WeatherRemoteDataSource(Location location) {
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        mWeatherFetch = new WeatherFetch(mLatitude, mLongitude);
    }

    public WeatherRemoteDataSource(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
        mWeatherFetch = new WeatherFetch(latitude, longitude);
    }
    @Override
    public void getForecast(GetForecastCallBack callBack) {
        mWeatherFetch.fetchForecast(callBack);
    }

    @Override
    public void refreshForecast(Forecast forecast) {
        // do nothing
    }
}
