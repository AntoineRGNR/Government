package com.example.antoine.knowyourgovernment;

/**
 * Created by antoine on 4/01/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    //Var TextView Declaration
    public TextView officeName;
    public TextView mame;

    public RecyclerViewHolder(View view) {
        super(view);
        officeName = (TextView) view.findViewById(R.id.textViewRole);
        mame = (TextView) view.findViewById(R.id.textViewName);
    }

}
