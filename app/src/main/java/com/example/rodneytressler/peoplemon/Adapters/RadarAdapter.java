package com.example.rodneytressler.peoplemon.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rodneytressler.peoplemon.Models.Auth;
import com.example.rodneytressler.peoplemon.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rodneytressler on 11/11/16.
 */

public class RadarAdapter extends RecyclerView.Adapter<RadarAdapter.SuspenseHolder> {
    public ArrayList<Auth> caughtDudes;
    private Context context;

    public RadarAdapter(ArrayList<Auth> caughtDudes, Context context) {
        this.caughtDudes = caughtDudes;
        this.context = context;
    }


    @Override
    public RadarAdapter.SuspenseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedview = LayoutInflater.from(context).inflate(R.layout.nearby_item, parent, false);
        return new RadarAdapter.SuspenseHolder(inflatedview);
    }

    @Override
    public void onBindViewHolder(RadarAdapter.SuspenseHolder holder, int position) {
        Auth travis = caughtDudes.get(position);
        holder.bindExpense(travis);
    }

    @Override
    public int getItemCount() {
        return caughtDudes == null ? 0 : caughtDudes.size();
    }

    class SuspenseHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.list_radar_text_view)
        TextView textField;

        public SuspenseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView); // because we are in a view holder, do context and view.
        }


        public void bindExpense(final Auth expense){ // pass an expense in and do these things to it.
            textField.setText(expense.getUserName());
        }
    }
}
