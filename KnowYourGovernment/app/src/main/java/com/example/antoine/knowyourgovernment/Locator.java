package com.example.antoine.knowyourgovernment;

/**
 * Created by antoine on 4/01/17.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;


public class Locator{
    //Var MainActivity Declaration
    private MainActivity owner;

    //Var LocationManager Declaration
    private LocationManager locationManager;

    //Var LocationListener Declaration
    private LocationListener locationListener;

    //Var String Declaration & Initialization
    private String TAG = "Locator";

    public Locator(MainActivity activity){
        Log.d(TAG, ": LocatorStart, +" + activity);
        owner = activity;
        if (checkPermission()){
            setUpLocationManager();
        }
        Log.d(TAG, ": locatorEnd");
    }

    public void setUpLocationManager(){
        Log.d(TAG, ": setUpLocationManagerStart");
        if (locationManager != null)
            return;

        if (!checkPermission())
            return;

        // Get the system's Location Manager
        locationManager = (LocationManager) owner.getSystemService(LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                Toast.makeText(owner, "Update from " + location.getProvider(), Toast.LENGTH_SHORT).show();
                owner.setData(location.getLatitude(), location.getLongitude());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Nothing to do here
            }

            public void onProviderEnabled(String provider) {
                // Nothing to do here
            }

            public void onProviderDisabled(String provider) {
                // Nothing to do here
            }
        };

        // Register the listener with the Location Manager to receive GPS location updates
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        Log.d(TAG, ": setUpLocationManagerEnd");
    }

    public void shutdown(){
        Log.d(TAG, ": shutdownStart");

        if (locationManager != null){
            locationManager.removeUpdates(locationListener);
            locationManager = null;
        }
        Log.d(TAG, ": shutdownEnd");
    }

    public void determineLocation(){
        Log.d(TAG, ": determineLocationStart");

        if (!checkPermission())
            return;

        if (locationManager == null)
            setUpLocationManager();

        if (locationManager != null){
            Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (loc != null) {
                owner.setData(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(owner, "Using " + LocationManager.NETWORK_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (locationManager != null){
            Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (loc != null) {
                owner.setData(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(owner, "Using " + LocationManager.PASSIVE_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (locationManager != null){
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                owner.setData(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(owner, "Using " + LocationManager.GPS_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // If you get here, you got no location at all
        owner.noLocationAvailable();

        Log.d(TAG, ": determineLocationEnd");

        return;
    }


    // This method asks the user for Locations permissions (if it does not already have them)
    private boolean checkPermission(){
        Log.d(TAG, ": checkPermissionStart");

        if (ContextCompat.checkSelfPermission(owner, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(owner,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
            return false;
        }
        Log.d(TAG, ": checkPermissionEnd");
        return true;
    }
}
