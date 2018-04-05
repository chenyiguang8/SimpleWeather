package com.chen.kevin.simpleweather.data;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.chen.kevin.simpleweather.data.WeatherDataSource.GetForecastCallBack;
import com.chen.kevin.simpleweather.data.weather.Forecast;
import com.chen.kevin.simpleweather.util.LocationUtils;


import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by Ms Chen on 2018/4/2.
 */

public class WeatherRepository {
    private static volatile WeatherRepository instance = null;

    private WeatherDataSource mWeatherLocalDataSource;
    private WeatherDataSource mWeatherRemoteDataSource;
    private Forecast mCachedForecast = null;
    private Context mContext;


    private WeatherRepository(Context context) {
        mContext = context;
        mWeatherLocalDataSource = WeatherLocalDataSource.getInstance(context);
        Location location = LocationUtils.getLocation(context);
        if (location == null) {
            // set deafult location in remote database
            mWeatherRemoteDataSource = new WeatherRemoteDataSource(22.5485, 114.0661);
        } else {
            mWeatherRemoteDataSource = new WeatherRemoteDataSource(location);
        }
    }

    public static WeatherRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (WeatherRepository.class) {
                if (instance == null) {
                    instance = new WeatherRepository(context);
                }
            }
        }
        return instance;
    }

    /**
     * get forecast from cached forecast, remote data source first, if not available,
     * get from the local database
     * @param callBack
     */
    public void getForecast(final GetForecastCallBack callBack) {
        if (isNetworkAvailableAndConnected()) {
            mWeatherRemoteDataSource.getForecast(new GetForecastCallBack() {
                @Override
                public void onForecastLoaded(Forecast forecast) {
                    // update the database to the lastest
                    mWeatherLocalDataSource.refreshForecast(forecast);
                    refreshCache(forecast);
                    callBack.onForecastLoaded(forecast);
                }

                @Override
                public void onDataNotAvailable() {
                    getForecastFromLocal(callBack);
                }
            });
        } else {
            Toast.makeText(mContext, "no internet connection", Toast.LENGTH_SHORT).show();
            getForecastFromLocal(callBack);
        }
    }

    private void refreshCache(Forecast forecast) {
        mCachedForecast = forecast;
    }

    public void refreshForecast() {

    }

    public void setLocation(Location location) {
        mWeatherRemoteDataSource = new WeatherRemoteDataSource(location);
    }


    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }

    private void getForecastFromLocal(final GetForecastCallBack callBack) {
        // get from cached data to avoid accessing the database repeatedly
        if (mCachedForecast != null) {
            callBack.onForecastLoaded(mCachedForecast);
            return;
        }
        // get data from the local database if cannot fetch from remote
        mWeatherLocalDataSource.getForecast(new GetForecastCallBack() {
            @Override
            public void onForecastLoaded(Forecast forecast) {
                refreshCache(forecast);
                callBack.onForecastLoaded(forecast);
            }

            @Override
            public void onDataNotAvailable() {
                callBack.onDataNotAvailable();
            }
        });
    }
}
