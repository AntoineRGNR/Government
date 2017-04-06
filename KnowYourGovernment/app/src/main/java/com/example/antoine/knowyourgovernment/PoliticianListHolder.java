package com.example.antoine.knowyourgovernment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by antoine on 4/3/17.
 */

public class PoliticianListHolder extends RecyclerView.ViewHolder{

    //Var TextView Declaration
    public TextView politicianName;
    public TextView politicianFunction;

    public PoliticianListHolder(View view) {
        super(view);
        //Var TextView Initialization
        politicianName = (TextView) view.findViewById(R.id.politicianName);
        politicianFunction = (TextView) view.findViewById(R.id.politicianFunction);
    }

}
