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
 * Created by rodneytressler on 11/10/16.
 */

public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.ExpenseHolder> {

    public ArrayList<Auth> caughtDudes;
    private Context context;

    public PeopleListAdapter(ArrayList<Auth> caughtDudes, Context context) {
        this.caughtDudes = caughtDudes;
        this.context = context;
    }


    @Override
    public ExpenseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedview = LayoutInflater.from(context).inflate(R.layout.people_list_item, parent, false);
        return new ExpenseHolder(inflatedview);
    }

    @Override
    public void onBindViewHolder(ExpenseHolder holder, int position) {
        Auth travis = caughtDudes.get(position);
        holder.bindExpense(travis);
    }

    @Override
    public int getItemCount() {
        return caughtDudes == null ? 0 : caughtDudes.size();
    }

    class ExpenseHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.list_item_text_view)
        TextView textField;

        public ExpenseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView); // because we are in a view holder, do context and view.
        }


        public void bindExpense(final Auth expense){ // pass an expense in and do these things to it.
            textField.setText(expense.getUserName());
        }
    }
}
