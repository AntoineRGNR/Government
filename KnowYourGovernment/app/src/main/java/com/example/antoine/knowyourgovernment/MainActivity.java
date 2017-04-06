package com.example.antoine.knowyourgovernment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Var ArrayList Declaration and Initialization
    private ArrayList<Politician> politicianList = new ArrayList<>();

    //Var RecyclerVIew Declaration
    private RecyclerView recyclerView;

    //Var StockAdapter Declaration
    private PoliticianAdapter politicianAdapter;

    //Var Locator Declaration
    private Locator locator;

    //Var int Declaration
    private static final int ADD_REQ = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RecyclerView Initialization
        recyclerView = (RecyclerView)findViewById(R.id.recycler);

        //PoliticianAdapter Initialization
        politicianAdapter = new PoliticianAdapter(politicianList, this);

        //Set RecyclerView to PoliticianAdapter
        recyclerView.setAdapter(politicianAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Locator Initilization
        locator = new Locator(this);
        //Ask For Locatopn
        locator.determineLocationGPS();

        // Make some data - not always needed - used to fill list
        politicianList.add(new Politician("GOD", "Antoine", "Democratic", "20 allee du jardin anglais\n93340\nLe Raincy", "06", "antoine", "pute.com", "OK", "OK", "", ""));
        politicianList.add(new Politician("JAVIER", "Antoine", "LOL", "20 allee du jardin anglais\n93340\nLe Raincy", "06", "antoine", "pute.com", "", "OK", "OK", ""));
        politicianList.add(new Politician("PASTORE", "Antoine", "IZI", "20 allee du jardin anglais\n93340\nLe Raincy", "06", "antoine", "pute.com", "", "OK", "", "OK"));
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.d(this.getString(R.string.TAGMA), "onOptionsItemSelected");
        //Doing Action if Menu Icon is selected
        switch (item.getItemId()) {
            case R.id.menuSearch:
                searchDialog();
                return true;
            case R.id.menuInfo:
                Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        //Intent Declaration and Initialization
        Intent intentOfficialActivity = new Intent(MainActivity.this, OfficialActivity.class);
        int pos = recyclerView.getChildLayoutPosition(v);
        //Get Position of selected Official to put in Intent
        intentOfficialActivity.putExtra("POLITICIAN", politicianList.get(pos));
        //Start Intent with selected Official
        startActivityForResult(intentOfficialActivity, ADD_REQ);
    }

    @Override
    protected void onDestroy() {
        Log.d(this.getString(R.string.TAGMA), "onDestroy: ");
        locator.shutDown();
        super.onDestroy();
    }

    public void setData(double lat, double lon) {
        String address = doAddress(lat, lon);

        ((TextView)findViewById(R.id.textLocation)).setText(address);
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

    public boolean networkCheck()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void searchDialog()
    {
        Log.d(this.getString(R.string.TAGMA), "searchDialog");
        //AlertDialog Declaration and Initialization
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //EditText Declaration and Initialization
        final EditText et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.setGravity(Gravity.CENTER_HORIZONTAL);
        et.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        builder.setView(et);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // Set Data
                ((TextView) findViewById(R.id.textLocation)).setText(et.getText().toString());
                startAsyncTask(et.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.c, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {

            }
        });

        builder.setMessage(R.string.sD);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void startAsyncTask(String string)
    {
        new AsyncTaskLoadOfficial(this).execute(string);
    }

}
