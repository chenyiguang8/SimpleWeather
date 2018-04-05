package com.chen.kevin.simpleweather.data.database;


/**
 * Created by Ms Chen on 2018/3/31.
 */

public class WeatherDbSchema {
    public static final class WeatherTable {
        public static final String TABLE_NAME = "WeatherDaily";
        public static final String CREATE_SQL = "create table " + TABLE_NAME + "(" +
                "_id"+ " integer primary key autoincrement, " +
                Cols.TIME + "," +
                Cols.TIMEZONE + "," +
                Cols.WEATHER_CONDITION + "," +
                Cols.TEMPERATURE_LOW + "," +
                Cols.TEMPERATURE_HIGH + ")";

        public static final class Cols {
            public static final String TIME = "time";
            public static final String TIMEZONE = "timeZone";
            public static final String WEATHER_CONDITION = "weatherCondition";
            public static final String TEMPERATURE_LOW = "temperatureLow";
            public static final String TEMPERATURE_HIGH = "temperatureHigh";
        }
    }
}
