package com.base.software_for_mobile_devices_project;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private static final String TAG = "=== TransactionListAdapter ===";

    private List<Transaction> transactions;
    private List<Transaction> filteredTransactions = new ArrayList<>();

    TransactionListAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.description.setText(transactions.get(position).getDescription());
        viewHolder.date.setText(transactions.get(position).getDate("dd-MM-yyyy hh:mm:ss"));
        viewHolder.amount.setText((Double.toString(transactions.get(position).getAmount())));

        if (transactions.get(position).getAmount() >= 0) {
            viewHolder.amount.setTextColor(Color.rgb(0, 100, 0));
        } else {
            viewHolder.amount.setTextColor(Color.rgb(100, 0, 0));
        }
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // TODO: init variables

        TextView description;
        TextView date;
        TextView amount;
        Button button;

        ViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description_item_transaction_list);
            date = itemView.findViewById(R.id.date_item_transaction_list);
            amount = itemView.findViewById(R.id.amount_item_transaction_list);
            button = itemView.findViewById(R.id.button_item_transaction_list);
        }
    }

}
