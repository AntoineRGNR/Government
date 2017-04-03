package com.example.antoine.knowyourgovernment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Var ArrayList Declaration and Initialization
    private ArrayList<Politician> politicianList = new ArrayList<>();

    //Var RecyclerVIew Declaration
    private RecyclerView recyclerView;

    //Var SwipeRefreshLayout Declaration
    private SwipeRefreshLayout swiper;

    //Var StockAdapter Declaration
    private PoliticianAdapter politicianAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        politicianAdapter = new PoliticianAdapter(politicianList, this);

        recyclerView.setAdapter(politicianAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Make some data - not always needed - used to fill list
        for (int i = 0; i < 20; i++) {
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

}
