package com.chen.kevin.simpleweather.weatherdetail;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.chen.kevin.simpleweather.data.WeatherDataSource;
import com.chen.kevin.simpleweather.data.WeatherRepository;
import com.chen.kevin.simpleweather.data.weather.CurrentWeather;
import com.chen.kevin.simpleweather.data.weather.Forecast;

/**
 * Created by Ms Chen on 2018/3/28.
 */

public class WeatherPresenter implements WeatherContract.Presenter {
    private WeatherContract.View mFragment;
    private Context mContext;

    public WeatherPresenter(Context context, WeatherContract.View fragment) {
        mContext = context.getApplicationContext();
        mFragment = fragment;
    }

    @Override
    public void start() {
    }

    @Override
    public void updateWeather() {
        WeatherRepository weatherRepository = WeatherRepository.getInstance(mContext);
        weatherRepository.getForecast(new WeatherDataSource.GetForecastCallBack() {
            @Override
            public void onForecastLoaded(final Forecast forecast) {
                Handler handler = new Handler(mContext.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mFragment.setUpAdapter(forecast);

                        CurrentWeather currentWeather = forecast.getCurrentWeather();
                        String location = currentWeather.getCity();
                        int iconResId = currentWeather.getIconResId();
                        String preciseTime = currentWeather.getPreciseTime();
                        int temperature = currentWeather.getTemperature();

                        mFragment.showCurrentWeather(location, iconResId, preciseTime, temperature);
                        mFragment.finishRefresh();
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {
                Toast.makeText(mContext, "no data available", Toast.LENGTH_SHORT).show();
                mFragment.finishRefresh();
            }
        });
    }

    private Location getLocation() {
        Location location = null;

        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            location = locationManager.getLastKnownLocation(provider);
        }

        return location;
    }
}
