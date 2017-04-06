package com.example.antoine.knowyourgovernment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by antoine on 4/6/17.
 */

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log
        Log.d(this.getString(R.string.TAGIA), "onCreate Creation");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

}
