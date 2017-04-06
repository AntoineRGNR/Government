package com.example.antoine.knowyourgovernment;

/**
 * Created by antoine on 4/6/17.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;


public class Locator {

    private static final String TAG = "Locator";
    private MainActivity owner;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public Locator(MainActivity activity) {
        owner = activity;

        setUpLocationManager();
    }

    public void setUpLocationManager() {

        if (locationManager != null)
            return;

        if (!checkPermission())
            return;

        locationManager = (LocationManager) owner.getSystemService(LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                Toast.makeText(owner, "Update from " + location.getProvider(), Toast.LENGTH_SHORT).show();
                owner.setData(location.getLatitude(), location.getLongitude());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

// Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.PASSIVE_PROVIDER, 1000, 0, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

    }

    public void shutDown() {
        locationManager.removeUpdates(locationListener);
        locationManager = null;
    }

    public void determineLocationGPS() {
        checkPermission();
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(owner, "GPS Location provider is not available!", Toast.LENGTH_LONG).show();
        }
        if (locationManager != null) {
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                owner.setData(loc.getLatitude(), loc.getLongitude());
            } else {
                Toast.makeText(owner, "GPS Location provider did not return a location!", Toast.LENGTH_SHORT).show();
            }
        }
        return;
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(owner, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(owner,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
            return false;
        }
        return true;
    }
}

