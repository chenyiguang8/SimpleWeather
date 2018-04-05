package com.chen.kevin.simpleweather.util;

import android.content.Context;
import android.preference.PreferenceManager;

import com.chen.kevin.simpleweather.data.weather.CurrentWeather;
import com.google.gson.Gson;

/**
 * Created by Ms Chen on 2018/4/5.
 */

public class WeatherPreferences {
    public static final String PRE_CURRENT_WEATHER = "currentWeather";

    public static CurrentWeather getStoredWeather(Context context) {
        CurrentWeather currentWeather = null;
        String json = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PRE_CURRENT_WEATHER, null);
        if (json != null) {
            Gson gson = new Gson();
            currentWeather = gson.fromJson(json, CurrentWeather.class);
        }

        return currentWeather;
    }

    public static void setStoredWeather(Context context, CurrentWeather currentWeather) {
        Gson gson = new Gson();
        String json = gson.toJson(currentWeather);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PRE_CURRENT_WEATHER, json)
                .apply();
    }
}
