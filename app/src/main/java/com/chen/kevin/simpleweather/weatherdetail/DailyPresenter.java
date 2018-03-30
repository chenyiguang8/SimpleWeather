package com.chen.kevin.simpleweather.weatherdetail;

import com.chen.kevin.simpleweather.R;

/**
 * Created by Ms Chen on 2018/3/30.
 */

class DailyPresenter implements WeatherDailyContract.Presenter {
    WeatherDailyContract.View mDailyViewHolder;

    public DailyPresenter() {
    }

    @Override
    public void start() {
    }

    @Override
    public void bindData(WeatherDailyContract.View holder, int position) {
        mDailyViewHolder = holder;

        // TODO: 2018/3/30 fake data
        mDailyViewHolder.showDay(R.string.monday);
        mDailyViewHolder.showWeatherIcon(R.drawable.cloudy_night);
        mDailyViewHolder.showTemperatureRange(18, 25);
    }

    @Override
    public int getItemCount() {
        // TODO: 2018/3/30 fake data
        return 10;
    }
}
