package com.chen.kevin.simpleweather.data.weather;

import com.chen.kevin.simpleweather.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Ms Chen on 2018/3/31.
 */

public class WeatherDaily extends Weather{
    private int mLow;
    private int mHigh;

    public WeatherDaily(long time, String timezone, String icon, int low, int high) {
        super(time, icon, timezone);
        mLow = low;
        mHigh = high;
    }

    public int getLow() {
        return mLow;
    }

    public void setLow(int low) {
        mLow = low;
    }

    public int getHigh() {
        return mHigh;
    }

    public void setHigh(int high) {
        mHigh = high;
    }

}
