package com.chen.kevin.simpleweather.weatherdetail;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.chen.kevin.simpleweather.R;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                // TODO: 2018/3/30
        }
        return super.onOptionsItemSelected(item);
    }
}
