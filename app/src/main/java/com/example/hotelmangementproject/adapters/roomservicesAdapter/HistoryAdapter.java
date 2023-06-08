package com.example.hotelmangementproject.adapters.roomservicesAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.services.TimeService;
import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.interfaces.IClickBillListener;
import com.example.hotelmangementproject.models.Bill;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryAdapterViewHolder> {


    private Context mContext;
    private List<Bill> listBill;
    private IClickBillListener iClickBillListener;

    public HistoryAdapter(Context mContext, IClickBillListener iClickBillListener){
        this.mContext = mContext;
        this.iClickBillListener = iClickBillListener;
    }

    public void setData (Context context,List<Bill> list){
        this.mContext = context;
        this.listBill = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HistoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_rs_item_history,parent,false);
        return new HistoryAdapter.HistoryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapterViewHolder holder, int position) {
        Bill bill = listBill.get(position);
        if(bill == null) return;
        holder.txtCheckoutTime.setText("Checkout Time: "+ TimeService.convertMilisecondsToDate(bill.getSubmitBillTime()));
        holder.txtCustomerName.setText(bill.getCustomerName());
        holder.txtTotalPrice.setText(Double.toString(bill.getTotalPrice()));
        holder.txtStayTime.setText("Stay time: "+TimeService.calStayTime(bill));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickBillListener.onClickItem(bill);
            }
        });
        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                iClickBillListener.onLongClickItem(bill,v);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listBill != null) return listBill.size();
        return 0;
    }

    public class HistoryAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView txtCustomerName;
        TextView txtCheckoutTime;
        TextView txtStayTime;
        TextView txtTotalPrice;

        RelativeLayout relativeLayout;
        public HistoryAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCustomerName = itemView.findViewById(R.id.txt_customername_item_history);
            txtCheckoutTime = itemView.findViewById(R.id.txt_checkout_time_item_history);
            txtStayTime = itemView.findViewById(R.id.txt_staytime_item_history);
            txtTotalPrice = itemView.findViewById(R.id.txt_totalPrice_item_history);
            relativeLayout = itemView.findViewById(R.id.relativelayout_cover_item_history);
        }
    }
}
