package com.chen.kevin.simpleweather.data.weather;

import com.chen.kevin.simpleweather.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Ms Chen on 2018/4/5.
 */

public class Weather {
    protected long mTime;
    protected String mIcon;
    protected String mTimeZone;

    public Weather(long time, String icon, String timeZone) {
        mTime = time;
        mIcon = icon;
        mTimeZone = timeZone;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timezone) {
        mTimeZone = timezone;
    }

    public String getDayOfTheWeek() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        Date dateTime = new Date(mTime * 1000);
        return formatter.format(dateTime);
    }

    public int getIconResId() {
        // clear-day, clear-night, rain, snow, sleet, wind, fog,
        // cloudy, partly-cloudy-day, or partly-cloudy-night.
        int iconResId = R.drawable.clear_day;

        if (mIcon.equals("clear-day")) {
            iconResId = R.drawable.clear_day;
        }
        else if (mIcon.equals("clear-night")) {
            iconResId = R.drawable.clear_night;
        }
        else if (mIcon.equals("rain")) {
            iconResId = R.drawable.rain;
        }
        else if (mIcon.equals("snow")) {
            iconResId = R.drawable.snow;
        }
        else if (mIcon.equals("sleet")) {
            iconResId = R.drawable.sleet;
        }
        else if (mIcon.equals("wind")) {
            iconResId = R.drawable.wind;
        }
        else if (mIcon.equals("fog")) {
            iconResId = R.drawable.fog;
        }
        else if (mIcon.equals("cloudy")) {
            iconResId = R.drawable.cloudy;
        }
        else if (mIcon.equals("partly-cloudy-day")) {
            iconResId = R.drawable.partly_cloudy;
        }
        else if (mIcon.equals("partly-cloudy-night")) {
            iconResId = R.drawable.cloudy_night;
        }

        return iconResId;
    }

   public String getPreciseTime() {
       SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
       formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));
       Date dateTime = new Date(mTime * 1000);
       return formatter.format(dateTime);
   }

   public String getCity() {
        String[] parts = mTimeZone.split("/");
        return parts[1];
   }
}
