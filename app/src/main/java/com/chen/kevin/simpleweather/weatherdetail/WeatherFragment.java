package com.chen.kevin.simpleweather.weatherdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    @BindView(R.id.icon_current_weather) ImageView mWeatherIconView;
    @BindView(R.id.recycler_view_daily) RecyclerView mRecyclerView;

    private WeatherContract.Presenter mPresenter;

    public static Fragment newInstance() {
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        // set up recycler view adapter
        mRecyclerView.setAdapter(new WeatherAdapter(new DailyPresenter()));
        return view;
    }


    class WeatherHolder extends RecyclerView.ViewHolder implements WeatherDailyContract.View{
        @BindView(R.id.text_day) TextView mDayView;
        @BindView(R.id.icon_weather_condition) ImageView mWeatherView;
        @BindView(R.id.text_temperature_range) TextView mDegreeRangeView;

        public WeatherHolder(LayoutInflater layoutInflater, ViewGroup parent) {
            super(layoutInflater.inflate(R.layout.list_item_forecast_daily, parent, false));
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void setPresenter(WeatherDailyContract.Presenter presenter) {
            // do nothing
        }

        @Override
        public void showDay(int dayResId) {
            mDayView.setText(getResources().getString(dayResId));
        }

        @Override
        public void showWeatherIcon(int weatherIconResId) {
            mWeatherView.setImageDrawable(getResources().getDrawable(weatherIconResId));
        }

        @Override
        public void showTemperatureRange(int low, int high) {
            String rangeFormat = getResources().getString(R.string.temperature_range);
            String temperatureRange = String.format(rangeFormat, low, high);
            mDegreeRangeView.setText(temperatureRange);
        }
    }


    private class WeatherAdapter extends RecyclerView.Adapter<WeatherHolder> {
        private WeatherDailyContract.Presenter mDailyPresenter;

        public WeatherAdapter(WeatherDailyContract.Presenter presenter) {
            mDailyPresenter = presenter;
        }

        @Override
        public WeatherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            WeatherHolder holder = new WeatherHolder(getLayoutInflater(), parent);
            return holder;
        }

        @Override
        public void onBindViewHolder(WeatherHolder holder, int position) {
            mDailyPresenter.bindData(holder, position);
        }

        @Override
        public int getItemCount() {
            return mDailyPresenter.getItemCount();
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
