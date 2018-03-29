package com.chen.kevin.simpleweather.weatherdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.kevin.simpleweather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ms Chen on 2018/3/28.
 */

public class WeatherFragment extends Fragment implements WeatherContract.View{
    @BindView(R.id.refresh) SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.text_location) TextView mLocationView;
    @BindView(R.id.text_temperature) TextView mTemperatureView;
    @BindView(R.id.text_time) TextView mTimeView;
    @BindView(R.id.icon_weather_condition) ImageView mWeatherIconView;
    @BindView(R.id.recycler_view_daily) RecyclerView mRecyclerView;

    private WeatherContract.Presenter mPresenter;

    public static Fragment newInstance() {
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);

        // set up swipe refresh linstener
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPresenter.updateWeather()) {
                    mRefreshLayout.setRefreshing(false);
                }
            }
        });

        // set up presenter
        mPresenter = new WeatherPresenter(this);

        return view;
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
    public void showUpdateTime(String time) {
        String timeInfoFormat = getResources().getString(R.string.update_time_info);
        String timeInfo = String.format(timeInfoFormat, time);
        mTimeView.setText(timeInfo);
    }

    @Override
    public void showTemperature(int degree) {
        mTemperatureView.setText(degree);
    }

    @Override
    public void showWeatherCondition(String location, int iconResId, String time, int degree) {
        showLocation(location);
        showWeatherIcon(iconResId);
        showUpdateTime(time);
        showTemperature(degree);
        // TODO: 2018/3/28  

    }
}
