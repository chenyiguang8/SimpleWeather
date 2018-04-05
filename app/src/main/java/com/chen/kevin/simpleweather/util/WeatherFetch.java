package com.chen.kevin.simpleweather.util;

import com.chen.kevin.simpleweather.data.weather.CurrentWeather;
import com.chen.kevin.simpleweather.data.weather.Forecast;
import com.chen.kevin.simpleweather.data.weather.WeatherDaily;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.chen.kevin.simpleweather.data.WeatherDataSource.*;

/**
 * Created by Ms Chen on 2018/4/2.
 */

public class WeatherFetch {
    private static final String API_KEY = "eee47bf0bd11a0f2fb53e443ae80f04d";
    private static final String END_POINT = "https://api.darksky.net/forecast/" +
                                         API_KEY + "/";

    private double mLatitude;
    private double mLongitude;
    private String mUrl;

    public WeatherFetch(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
        mUrl = END_POINT + mLatitude + ", " + mLongitude;
    }

    /**
     * fetch
     * @param callBack
     */
    public void fetchForecast(final GetForecastCallBack callBack) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(mUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onDataNotAvailable();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    CurrentWeather currentWeather = parseCurrentWeather(jsonString);
                    List<WeatherDaily> weatherDailyList =  parseDailyForecast(jsonString);
                    Forecast forecast = new Forecast(currentWeather, weatherDailyList);
                    callBack.onForecastLoaded(forecast);
                } else {
                    callBack.onDataNotAvailable();
                }
            }
        });
    }


    private CurrentWeather parseCurrentWeather(String jsonString) {
        CurrentWeather currentWeather = null;

        try {
            JSONObject jsonBody = new JSONObject(jsonString);
            String timeZone = jsonBody.getString("timezone");

            JSONObject currentlyJsonObject =jsonBody.getJSONObject("currently");
            long time = currentlyJsonObject.getLong("time");
            String icon = currentlyJsonObject.getString("icon");
            int temperature = currentlyJsonObject.getInt("temperature");

            currentWeather = new CurrentWeather(time, icon, timeZone, temperature);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currentWeather;
    }

    private List<WeatherDaily> parseDailyForecast(String jsonString) {
        List<WeatherDaily> weatherDailyList = new ArrayList<>();

        try {
            JSONObject jsonBody = new JSONObject(jsonString);
            String timeZone = jsonBody.getString("timezone");

            JSONObject dailyJsonObject = jsonBody.getJSONObject("daily");
            JSONArray dataJsonArray = dailyJsonObject.getJSONArray("data");

            for (int i = 0; i < dataJsonArray.length(); i++) {
                JSONObject dataJsonObject = dataJsonArray.getJSONObject(i);
                long time = dataJsonObject.getLong("time");
                String icon = dataJsonObject.getString("icon");
                int low = dataJsonObject.getInt("temperatureLow");
                int high = dataJsonObject.getInt("temperatureHigh");

                WeatherDaily weatherDaily = new WeatherDaily(time, timeZone, icon, low, high);
                weatherDailyList.add(weatherDaily);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weatherDailyList;
    }
}
