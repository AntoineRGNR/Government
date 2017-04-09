package com.example.antoine.knowyourgovernment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by antoine on 4/9/17.
 */

public class DetailActivity extends AppCompatActivity {

    //Var Politician Declaration
    Politician politician;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log
        Log.d(this.getString(R.string.TAGIA), "onCreate Creation");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Getting Note intent
        politician = (Politician)getIntent().getSerializableExtra("DETAIL");

        //Text View Initialization
        ((TextView)findViewById(R.id.politicianFunctionDetail)).setText(politician.getOffice());
        ((TextView)findViewById(R.id.politicianNameDetail)).setText(politician.getName());
    }

}
