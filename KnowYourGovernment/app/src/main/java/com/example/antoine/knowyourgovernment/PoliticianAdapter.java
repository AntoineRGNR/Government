package com.example.antoine.knowyourgovernment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by antoine on 4/3/17.
 */

public class PoliticianAdapter extends RecyclerView.Adapter<PoliticianListHolder>
{
    //Var TAG Declaration for log
    private static final String TAG = "SummonerAdapter";

    //Var List Declaration
    private List<Politician> politicianList;

    //Var MainActivity Declaration for onClickListener and onLongClickListener
    private MainActivity mainAct;

    public PoliticianAdapter(List<Politician> empList, MainActivity ma)
    {
        //Initializing stockList as an empty list
        this.politicianList = empList;
        //Initializing MainActivity Var
        mainAct = ma;
    }

    @Override
    public PoliticianListHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW");
        //Initializing the view holder to hold stock_list_row
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.government_row, parent, false);
        itemView.setOnClickListener(mainAct);
        //itemView.setOnLongClickListener(mainAct);
        return new PoliticianListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PoliticianListHolder holder, int position)
    {
        Log.d("Politician Adapter", "onBindViewHolder: BINDING");
        Politician politician = politicianList.get(position);
        holder.politicianFunction.setText(politician.getOffice());
        Log.d("Politician Adapter", "onBindViewHolder: " + politician.getOffice());
        holder.politicianName.setText(politician.getName());
        Log.d("Politician Adapter", "onBindViewHolder: " + politician.getName());
    }

    @Override
    public int getItemCount()
    {
        return politicianList.size();
    }

}
