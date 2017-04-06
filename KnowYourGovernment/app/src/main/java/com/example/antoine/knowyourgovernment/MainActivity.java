package com.example.antoine.knowyourgovernment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Var ArrayList Declaration and Initialization
    private ArrayList<Politician> politicianList = new ArrayList<>();

    //Var RecyclerVIew Declaration
    private RecyclerView recyclerView;

    //Var SwipeRefreshLayout Declaration
    private SwipeRefreshLayout swiper;

    //Var StockAdapter Declaration
    private PoliticianAdapter politicianAdapter;

    private Locator locator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        politicianAdapter = new PoliticianAdapter(politicianList, this);

        recyclerView.setAdapter(politicianAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        locator = new Locator(this);
        locator.determineLocationGPS();

        // Make some data - not always needed - used to fill list
        for (int i = 0; i < 3; i++) {
            politicianList.add(new Politician("GOD", "Antoine", "", "", "", "", "", "", "", "", ""));
        }
        Log.d(this.getString(R.string.TAGMA), "onCreate: End");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Adding Menu to MainActivity
        Log.d(this.getString(R.string.TAGMA), "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        Log.d(this.getString(R.string.TAGMA), "onDestroy: ");
        locator.shutDown();
        super.onDestroy();
    }

    public void setData(double lat, double lon) {
        String address = doAddress(lat, lon);

        ((TextView) findViewById(R.id.textLocation)).setText(address);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        Log.d(this.getString(R.string.TAGMA), "onRequestPermissionsResult: CALL: " + permissions.length);
        Log.d(this.getString(R.string.TAGMA), "onRequestPermissionsResult: PERM RESULT RECEIVED");

        if (requestCode == 5) {
            Log.d(this.getString(R.string.TAGMA), "onRequestPermissionsResult: permissions.length: " + permissions.length);
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(this.getString(R.string.TAGMA), "onRequestPermissionsResult: HAS PERM");
                        locator.setUpLocationManager();
                    } else {
                        Log.d(this.getString(R.string.TAGMA), "onRequestPermissionsResult: NO PERM");
                    }
                }
            }
        }
        Log.d(this.getString(R.string.TAGMA), "onRequestPermissionsResult: Calling super onRequestPermissionsResult");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(this.getString(R.string.TAGMA), "onRequestPermissionsResult: Exiting onRequestPermissionsResult");
    }


    private String doAddress(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            StringBuilder sb = new StringBuilder();

            for (Address ad : addresses) {
                Log.d(this.getString(R.string.TAGMA), "doLocation: " + ad);

                sb.append(ad.getAddressLine(1));
            }
            return sb.toString();
        } catch (IOException e) {
            Toast.makeText(this, "Cannot acquire ZIP code from Lat/Lon.\n\nNetwork resources unavailable", Toast.LENGTH_LONG).show();
        }
        return null;

    }

}
