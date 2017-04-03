package com.example.antoine.knowyourgovernment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    //Var RecyclerVIew Declaration
    private RecyclerView recyclerView;

    //Var SwipeRefreshLayout Declaration
    private SwipeRefreshLayout swiper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recyclerView = (RecyclerView) findViewById(R.id.recycler);
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
