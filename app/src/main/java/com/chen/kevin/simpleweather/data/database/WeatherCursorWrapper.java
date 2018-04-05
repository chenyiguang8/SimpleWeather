package com.chen.kevin.simpleweather.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.chen.kevin.simpleweather.data.weather.WeatherDaily;

import java.util.UUID;

import static com.chen.kevin.simpleweather.data.database.WeatherDbSchema.*;

/**
 * Created by Ms Chen on 2018/4/1.
 */

public class WeatherCursorWrapper extends CursorWrapper {

    public WeatherCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public WeatherDaily getWeather() {
        long time = getInt(getColumnIndex(WeatherTable.Cols.TIME));
        String timeZone = getString(getColumnIndex(WeatherTable.Cols.TIMEZONE));

        String icon = getString(getColumnIndex(WeatherTable.Cols.WEATHER_CONDITION));
        int low = getInt(getColumnIndex(WeatherTable.Cols.TEMPERATURE_LOW));
        int high = getInt(getColumnIndex(WeatherTable.Cols.TEMPERATURE_HIGH));

        WeatherDaily weatherDaily = new WeatherDaily(time, timeZone, icon, low, high);
        return weatherDaily;
    }
}
