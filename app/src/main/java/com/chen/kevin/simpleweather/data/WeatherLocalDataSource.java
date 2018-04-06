package com.chen.kevin.simpleweather.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chen.kevin.simpleweather.data.database.WeatherCursorWrapper;
import com.chen.kevin.simpleweather.data.database.WeatherDbHelper;
import com.chen.kevin.simpleweather.data.database.WeatherDbSchema.WeatherTable;
import com.chen.kevin.simpleweather.data.weather.CurrentWeather;
import com.chen.kevin.simpleweather.data.weather.Forecast;
import com.chen.kevin.simpleweather.data.weather.WeatherDaily;
import com.chen.kevin.simpleweather.util.WeatherPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ms Chen on 2018/3/31.
 */

public class WeatherLocalDataSource implements WeatherDataSource {
    private static volatile WeatherLocalDataSource instance = null;

    private SQLiteDatabase mDatabase;
    private CurrentWeather mCurrentWeather;
    private Context mContext;

    private WeatherLocalDataSource(Context context) {
        mContext = context;
        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
        mDatabase = dbHelper.getWritableDatabase();
        mCurrentWeather = WeatherPreferences.getStoredWeather(context);
    }

    // double-checked locks
    public static WeatherLocalDataSource getInstance(Context context) {
        if (instance == null) {
            synchronized (WeatherLocalDataSource.class) {
                if (instance == null) {
                    instance = new WeatherLocalDataSource(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void getForecast(GetForecastCallBack callBack) {
        List<WeatherDaily> weatherDailyList = getDailyForecast();
        CurrentWeather currentWeather = getCurrentWeather();
        if (weatherDailyList.isEmpty() || currentWeather == null) {
            callBack.onDataNotAvailable();
        } else {
            Forecast forecast = new Forecast(currentWeather, weatherDailyList);
            callBack.onForecastLoaded(forecast);
        }
    }

    private List<WeatherDaily> getDailyForecast() {
        List<WeatherDaily> weatherDailyList = new ArrayList<>();
        WeatherCursorWrapper cursor = queryWeather(null, null
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            weatherDailyList.add(cursor.getWeather());
            cursor.moveToNext();
        }

        // Do not forget to close the cursor
        cursor.close();
        return weatherDailyList;
    }

    private CurrentWeather getCurrentWeather() {
        return mCurrentWeather;
    }

    @Override
    public void refreshForecast(Forecast forecast) {
        refreshCurrentWeather(forecast.getCurrentWeather());
        refreshDailyForecast(forecast.getDailyForecast());
    }

    private void refreshDailyForecast(List<WeatherDaily> weatherDailyList) {
        deleteDailyForecast();
        insertDailyForecast(weatherDailyList);
    }

    private void refreshCurrentWeather(CurrentWeather weather) {
        mCurrentWeather = weather;
        WeatherPreferences.setStoredWeather(mContext, weather);
    }

    /**
     * insert daily forecast into the database
     * @param weatherDailyList
     */
    private void insertDailyForecast(List<WeatherDaily> weatherDailyList) {
        for (WeatherDaily weather : weatherDailyList) {
            mDatabase.insert(WeatherTable.TABLE_NAME,
                    null,
                    getContentValus(weather));
        }
    }

    /**
     * delete all all data in the database
     */
    private void deleteDailyForecast() {
            mDatabase.delete(WeatherTable.TABLE_NAME,
                    null,
                    null);
    }

    /**
     * query the data from the weather database
     *
     * @param whereClause select clause
     * @param whereArgs   select arguments
     * @return @eatherCursorWrapper
     */
    private WeatherCursorWrapper queryWeather(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(WeatherTable.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new WeatherCursorWrapper(cursor);
    }

    private ContentValues getContentValus(WeatherDaily weatherDaily) {
        ContentValues values = new ContentValues();
        values.put(WeatherTable.Cols.TIME, weatherDaily.getTime());
        values.put(WeatherTable.Cols.TIMEZONE, weatherDaily.getTimeZone());
        values.put(WeatherTable.Cols.WEATHER_CONDITION, weatherDaily.getIcon());
        values.put(WeatherTable.Cols.TEMPERATURE_LOW, weatherDaily.getLow());
        values.put(WeatherTable.Cols.TEMPERATURE_HIGH, weatherDaily.getHigh());
        return values;
    }
}
