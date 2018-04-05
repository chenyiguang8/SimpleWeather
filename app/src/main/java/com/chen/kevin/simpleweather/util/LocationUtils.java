package com.chen.kevin.simpleweather.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by Ms Chen on 2018/4/4.
 */

public class LocationUtils {

    public static Location getLocation(final Context context) {
        Location location = null;

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // FIXME: 2018/4/6
            location = locationManager.getLastKnownLocation(provider);
        }

        return location;
    }
}
