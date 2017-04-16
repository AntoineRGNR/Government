package com.example.antoine.knowyourgovernment;

/**
 * Created by antoine on 4/16/17.
 */

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    //Var String Declaration
    private static final String DEFAULT_LOCATION = "No Data For Location";
    private static final String TAG = "MainActivity";
    private String address = null;
    private String lastLocation;

    //Var List Declaration
    private List<Office> officesList = new ArrayList<>();

    //Var RecyclerView Declaration
    private RecyclerView recyclerView;

    //Var OfficeAdapter Declaration
    private OfficeAdapter mAdapter;

    //Var Locator Declaration
    private Locator locator;

    //Var MainActivity Declaration
    private MainActivity mainActivity;

    //Var TextView Declaration
    private TextView textViewLocation;

    //Var ConstraintLayout Declaration
    private ConstraintLayout noNetworkLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        Log.d(this.getString(R.string.TAGMA), ": onCreateStart");

        super.onCreate(savedInstanceState);
        mainActivity = this;
        setContentView(R.layout.activity_main);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
        textViewLocation.setText(DEFAULT_LOCATION);
        locator = new Locator(this);
        mAdapter = new OfficeAdapter(officesList, this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noNetworkLayout = (ConstraintLayout) findViewById(R.id.constraintNoNetwork);
        if (isNetworkOn()) {
            noNetworkLayout.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            locator.determineLocation();
        } else {
            noNetworkLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        if (address != null) {
            new OfficialsParserAsyncTask(mainActivity, address).execute();
        }

        Log.d(this.getString(R.string.TAGMA), ": onCreateEnd");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Log.d(this.getString(R.string.TAGMA), ": onCreateOptionsMenu");

        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Log.d(this.getString(R.string.TAGMA), ": onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.menuInfo:
                Log.d(this.getString(R.string.TAGMA), ": onOptionsItemSelected: menuInfo");
                Intent intentAboutActivity = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intentAboutActivity);
                return true;
            case R.id.menuSearch:
                Log.d(this.getString(R.string.TAGMA), ": onOptionsItemSelected, menuSearch");
                askNewLocationDialog();
                return true;
            default:
                Log.d(this.getString(R.string.TAGMA), ": onOptionsItemSelected, default");
                return super.onOptionsItemSelected(item);
        }
    }


    public void setData(double lat, double lon){
        Log.d(this.getString(R.string.TAGMA), "setData: Lat: " + lat + ", Lon: " + lon);
        String address = doAddress(lat, lon);
        ((TextView) findViewById(R.id.textViewLocation)).setText(address);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d(this.getString(R.string.TAGMA), ": onRequestPermissionsResult");

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d(this.getString(R.string.TAGMA), "onRequestPermissionsResult: CALL: " + permissions.length);
        Log.d(this.getString(R.string.TAGMA), "onRequestPermissionsResult: PERM RESULT RECEIVED");

        if (requestCode == 5) {
            Log.d(this.getString(R.string.TAGMA), "onRequestPermissionsResult: permissions.length: " + permissions.length);
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(this.getString(R.string.TAGMA), "onRequestPermissionsResult: HAS PERM");
                        locator.setUpLocationManager();
                        locator.determineLocation();
                    } else {
                        Toast.makeText(this, "Location permission was denied - cannot determine address", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onRequestPermissionsResult: NO PERM");
                    }
                }
            }
        }
        Log.d(this.getString(R.string.TAGMA), "onRequestPermissionsResult: Exiting onRequestPermissionsResult");
    }


    private String doAddress(double latitude, double longitude) {

        Log.d(this.getString(R.string.TAGMA), "doAddress: Lat: " + latitude + ", Lon: " + longitude);

        List<Address> addresses = null;
        for (int times = 0; times < 3; times++) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                Log.d(this.getString(R.string.TAGMA), "doAddress: Getting address now");


                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                StringBuilder sb = new StringBuilder();

                for (Address ad : addresses) {
                    Log.d(this.getString(R.string.TAGMA), "doLocation: " + ad);

                    sb.append(ad.getAddressLine(1));
                }
                if (locator != null) {
                    locator.shutdown();
                }
                this.address = sb.toString();
                return sb.toString();
            } catch (IOException e) {
                Log.d(TAG, "doAddress: " + e.getMessage());

            }
            Toast.makeText(this, "GeoCoder service is slow - please wait", Toast.LENGTH_SHORT).show();
        }
        if (locator != null) {
            locator.shutdown();
        }
        Toast.makeText(this, "GeoCoder service timed out - please try again", Toast.LENGTH_LONG).show();
        return null;
    }

    public void noLocationAvailable(){
        Log.d(this.getString(R.string.TAGMA), ": noLocationAvailable");
        Toast.makeText(this, "No location providers were available", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy(){
        Log.d(this.getString(R.string.TAGMA), ": onDestroy");
        if (locator != null) {
            locator.shutdown();
        }
        super.onDestroy();
    }

    public void setOfficesList(List<Office> officesList){
        Log.d(this.getString(R.string.TAGMA), ": setOfficesList");
        this.officesList.clear();
        for (Office office : officesList) {
            this.officesList.add(office);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setLastLocation(String lastLocation){
        Log.d(this.getString(R.string.TAGMA), ": setLastLocation");
        this.lastLocation = lastLocation;
        ((TextView) findViewById(R.id.textViewLocation)).setText(this.lastLocation);
    }

    @Override
    public void onClick(View v){
        Log.d(this.getString(R.string.TAGMA), ": onClick");
        int pos = recyclerView.getChildLayoutPosition(v);
        Intent intent = new Intent(MainActivity.this, OfficialActivity.class);
        intent.putExtra("office", this.officesList.get(pos));
        intent.putExtra("address", this.address);
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v){
        Log.d(this.getString(R.string.TAGMA), ": onLongClick");
        onClick(v);
        return true;
    }

    private void askNewLocationDialog(){
        Log.d(this.getString(R.string.TAGMA), ": askNewLocationDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter a City, State or a Zip Code:");
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText editTestSymbol = (EditText) view.findViewById(R.id.textTypedAsk);
                new OfficialsParserAsyncTask(mainActivity, editTestSymbol.getText().toString()).execute();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private boolean isNetworkOn(){
        Log.d(this.getString(R.string.TAGMA), ": isNetworkOn");
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public String getAddress(){
        Log.d(this.getString(R.string.TAGMA), ": getAddress");
        return address;
    }

    public void setAddress(String address){
        Log.d(this.getString(R.string.TAGMA), ": setAddress");
        this.address = address;
    }
}
