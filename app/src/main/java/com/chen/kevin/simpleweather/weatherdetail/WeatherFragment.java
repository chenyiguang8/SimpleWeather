package com.chen.kevin.simpleweather.weatherdetail;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.kevin.simpleweather.R;
import com.chen.kevin.simpleweather.data.weather.Forecast;
import com.chen.kevin.simpleweather.data.weather.WeatherDaily;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ms Chen on 2018/3/28.
 */

public class WeatherFragment extends Fragment implements WeatherContract.View{
    private static final int  LOCATION_PERMISSION_REQUEST = 1;
    private static final String[] LOCATION_PERMISSION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @BindView(R.id.refresh) SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.text_location) TextView mLocationView;
    @BindView(R.id.text_temperature) TextView mTemperatureView;
    @BindView(R.id.text_time) TextView mTimeView;
    @BindView(R.id.icon_current_weather) ImageView mWeatherIconView;
    @BindView(R.id.recycler_view_daily) RecyclerView mRecyclerView;

    private WeatherContract.Presenter mPresenter;
    private WeatherAdapter mWeatherAdapter;

    public static Fragment newInstance() {
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);


        // set up presenter
        mPresenter = new WeatherPresenter(getContext(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        checkLocationPermission();

        // set up swipe refresh linstener
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.updateWeather();
            }
        });

        return view;
    }


    class WeatherHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.text_day) TextView mDayView;
        @BindView(R.id.icon_weather_condition) ImageView mWeatherView;
        @BindView(R.id.text_temperature_range) TextView mDegreeRangeView;

        public WeatherHolder(LayoutInflater layoutInflater, ViewGroup parent) {
            super(layoutInflater.inflate(R.layout.list_item_forecast_daily, parent, false));
            ButterKnife.bind(this, itemView);
        }

        public void bind(WeatherDaily weatherDaily) {
            mDayView.setText(weatherDaily.getDayOfTheWeek());
            mWeatherView.setImageDrawable(getResources().getDrawable(weatherDaily.getIconResId()));

            // set temperature range
            String rangeFormat = getResources().getString(R.string.temperature_range);
            String temperatureRange = String.format(rangeFormat, weatherDaily.getLow(),
                                                  weatherDaily.getHigh());
            mDegreeRangeView.setText(temperatureRange);
        }
    }


    private class WeatherAdapter extends RecyclerView.Adapter<WeatherHolder> {
        List<WeatherDaily> weatherDailyList;

        public WeatherAdapter(Forecast forecast) {
            weatherDailyList = forecast.getDailyForecast();
        }

        @Override
        public WeatherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            WeatherHolder holder = new WeatherHolder(getLayoutInflater(), parent);
            return holder;
        }

        @Override
        public void onBindViewHolder(WeatherHolder holder, int position) {
            holder.bind(weatherDailyList.get(position));
        }

        @Override
        public int getItemCount() {
            return weatherDailyList.size();
        }
    }

    @Override
    public void setPresenter(WeatherContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLocation(String location) {
        mLocationView.setText(location);
    }

    @Override
    public void showWeatherIcon(int iconResId) {
        mWeatherIconView.setImageDrawable(getResources().getDrawable(iconResId));
    }

    @Override
    public void showUpdateTime(String preciseTime) {
        String timeInfoFormat = getResources().getString(R.string.update_time_info);
        String timeInfo = String.format(timeInfoFormat, preciseTime);
        mTimeView.setText(timeInfo);
    }

    @Override
    public void showTemperature(int degree) {
        mTemperatureView.setText(degree + "");
    }

    @Override
    public void showCurrentWeather(String location, int iconResId, String preciseTime, int degree) {
        showLocation(location);
        showWeatherIcon(iconResId);
        showUpdateTime(preciseTime);
        showTemperature(degree);
    }

    @Override
    public void setUpAdapter(Forecast forecast) {
        mWeatherAdapter = new WeatherAdapter(forecast);
        mRecyclerView.setAdapter(mWeatherAdapter);
    }

    @Override
    public void finishRefresh() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST:
                if (!hasLocationPermission()) {
                    // show a message if the user deny permission
                    Toast.makeText(getActivity(), "location permission denied", Toast.LENGTH_SHORT)
                            .show();
                }

                mPresenter.updateWeather();
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * check for location permission, if no permission, request it
     */
    private void checkLocationPermission() {
        if (!hasLocationPermission()) {
            // check if the user has been asked about this permission and denied it
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // explain to the user why permission is needed
            }

            requestPermissions(LOCATION_PERMISSION,
                    LOCATION_PERMISSION_REQUEST);

        } else {
            mPresenter.updateWeather();
        }
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }
}
