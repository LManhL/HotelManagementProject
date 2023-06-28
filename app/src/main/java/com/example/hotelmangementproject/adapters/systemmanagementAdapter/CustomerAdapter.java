package com.example.hotelmangementproject.adapters.systemmanagementAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.interfaces.IClickCustomer;
import com.example.hotelmangementproject.models.Customer;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    List<Customer> listCustomer;
    Context mContext;
    IClickCustomer iClickCustomer;

    public CustomerAdapter(Context context, List<Customer> listCustomer, IClickCustomer iClickCustomer){
        this.mContext = context;
        this.listCustomer = listCustomer;
        this.iClickCustomer = iClickCustomer;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_sm_item_customer,parent,false);
        return new CustomerAdapter.CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = listCustomer.get(position);
        if(customer == null) return;
        holder.txtId.setText(String.valueOf(position + 1));
        holder.txtName.setText(customer.getName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickCustomer.onClick(customer);
            }
        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                iClickCustomer.onLongClick(customer);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCustomer.size();
    }

    public  class CustomerViewHolder extends RecyclerView.ViewHolder {
        public TextView txtId;
        public TextView txtName;
        public LinearLayout linearLayout;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName_frag_sm_item_customer);
            txtId = itemView.findViewById(R.id.txtID_frag_sm_item_customer);
            linearLayout = itemView.findViewById(R.id.linearlayout_frag_sm_item_customer);
        }
    }
}
