package com.chen.kevin.simpleweather.weatherdetail;

import com.chen.kevin.simpleweather.BasePresenter;
import com.chen.kevin.simpleweather.BaseView;

/**
 * Created by Ms Chen on 2018/3/28.
 */

public interface WeatherContract {
    interface View extends BaseView<Presenter> {
        void showLocation(String location);
        void showWeatherIcon(int iconResId);
        void showUpdateTime(String time);
        void showTemperature(int degree);
        void showWeatherCondition(String location, int iconResId, String time, int degree);
    }

    interface Presenter extends BasePresenter {
        boolean updateWeather();
    }
}
