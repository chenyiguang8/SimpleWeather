package com.chen.kevin.simpleweather.weatherdetail;

import com.chen.kevin.simpleweather.BasePresenter;
import com.chen.kevin.simpleweather.BaseView;
import com.chen.kevin.simpleweather.data.weather.Forecast;

/**
 * Created by Ms Chen on 2018/3/28.
 */

public interface WeatherContract {
    interface View extends BaseView<Presenter> {
        void showLocation(String location);
        void showWeatherIcon(int iconResId);
        void showUpdateTime(String time);
        void showTemperature(int degree);
        void showCurrentWeather(String location, int iconResId, String preciseTime, int degree);
        void setUpAdapter(Forecast forecast);
        void finishRefresh();
    }


    interface Presenter extends BasePresenter {
        void updateWeather();
    }
}
