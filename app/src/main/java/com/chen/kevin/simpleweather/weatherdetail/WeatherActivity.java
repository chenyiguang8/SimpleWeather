package com.chen.kevin.simpleweather.weatherdetail;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import com.chen.kevin.simpleweather.SingleFragmentActivity;
import butterknife.ButterKnife;

public class WeatherActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return WeatherFragment.newInstance();
    }

}
