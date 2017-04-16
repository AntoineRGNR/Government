package com.example.antoine.knowyourgovernment;

/**
 * Created by antoine on 4/01/17.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        Log.d(this.getString(R.string.TAGAA), ": onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}