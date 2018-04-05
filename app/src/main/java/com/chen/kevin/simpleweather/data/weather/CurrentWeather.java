package com.chen.kevin.simpleweather.data.weather;

import com.chen.kevin.simpleweather.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Ms Chen on 2018/4/2.
 */

public class CurrentWeather extends Weather{
    private int mTemperature;

    public CurrentWeather(long time, String icon, String timeZone, int temperature) {
        super(time, icon, timeZone);
        mTemperature = temperature;
    }

    public int getTemperature() {
        return mTemperature;
    }

    public void setTemperature(int temperature) {
        mTemperature = temperature;
    }

}
