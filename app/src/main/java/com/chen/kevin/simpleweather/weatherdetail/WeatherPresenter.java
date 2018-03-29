package com.chen.kevin.simpleweather.weatherdetail;

import android.support.v4.app.Fragment;

/**
 * Created by Ms Chen on 2018/3/28.
 */

public class WeatherPresenter implements WeatherContract.Presenter {
    private Fragment mFragment;

    public WeatherPresenter(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void start() {
    }

    @Override
    public boolean updateWeather() {
        // TODO: 2018/3/28
        return true;
    }
}
