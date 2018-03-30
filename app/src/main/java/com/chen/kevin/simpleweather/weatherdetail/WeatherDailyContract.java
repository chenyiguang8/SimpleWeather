package com.chen.kevin.simpleweather.weatherdetail;

import com.chen.kevin.simpleweather.BasePresenter;
import com.chen.kevin.simpleweather.BaseView;

/**
 * Created by Ms Chen on 2018/3/29.
 */

public interface WeatherDailyContract {
    interface View extends BaseView<Presenter> {
        void showDay(int dayResId);
        void showWeatherIcon(int weatherIconResId);
        void showTemperatureRange(int low, int high);
    }

    interface Presenter extends BasePresenter {
        void bindData(View holder, int position);
        int getItemCount();
    }
}
