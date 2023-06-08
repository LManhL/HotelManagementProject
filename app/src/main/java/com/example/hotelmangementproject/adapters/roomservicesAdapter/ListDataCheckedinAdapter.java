package com.example.hotelmangementproject.adapters.roomservicesAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.firebaseServices.BillService;
import com.example.hotelmangementproject.interfaces.IClickBillListener;
import com.example.hotelmangementproject.interfaces.IClickItemRoomBillListener;
import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.RoomBill;
import com.example.hotelmangementproject.ui.roomservices.CheckedinFragment;

import java.util.List;

public class ListDataCheckedinAdapter extends RecyclerView.Adapter<ListDataCheckedinAdapter.ListDataViewHolder> {

    public static final int TYPE_RENT_BY_DAY = 1;
    public static final int TYPE_RENT_BY_HOUR = 2;

    private Context mContext;
    private List<Bill> listRoomCheckedin;
    private IClickItemRoomBillListener iClickItemRoomBillListener;
    private IClickBillListener iClickBillListener;

    public ListDataCheckedinAdapter(Context mcontext, IClickItemRoomBillListener iClickItemRoomBillListener,IClickBillListener iClickBillListener){
        this.mContext = mcontext;
        this.iClickItemRoomBillListener = iClickItemRoomBillListener;
        this.iClickBillListener = iClickBillListener;
    }

    public void setData (List<Bill> list){
        this.listRoomCheckedin = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return listRoomCheckedin.get(position).getType();
    }

    @NonNull
    @Override
    public ListDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_rs_item_checkin,parent,false);
        return new ListDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListDataViewHolder holder, int position) {
        Bill bill = listRoomCheckedin.get(position);
        if(bill == null) return;
        List<RoomBill> listdata = bill.getListRoomBill();

        for(RoomBill roomBill : listdata){
            roomBill.caculateRoomCost();
        }
        if(TYPE_RENT_BY_DAY == bill.getType()){
            holder.txtCustomerName.setText(bill.getCustomerName());

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
            holder.recyclerViewItem.setLayoutManager(linearLayoutManager);
            holder.recyclerViewItem.setFocusable(false);

            RoomBillCheckedinAdapter roomBillCheckedinAdapter = new RoomBillCheckedinAdapter(mContext, new IClickItemRoomBillListener() {
                @Override
                public void onClickItem(RoomBill roomBill,Bill billValue) {
                    iClickItemRoomBillListener.onClickItem(roomBill,bill);
                }

                @Override
                public void onLongClickItem(RoomBill roomBill, View view) {
                    iClickItemRoomBillListener.onLongClickItem(roomBill,view);
                }
            });

            roomBillCheckedinAdapter.setData(listdata,bill);

            holder.recyclerViewItem.setAdapter(roomBillCheckedinAdapter);

            holder.txtCustomerName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickBillListener.onClickItem(bill);
                }
            });
            holder.txtCustomerName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    iClickBillListener.onLongClickItem(bill,v);
                    return false;
                }
            });
        }
        else if(TYPE_RENT_BY_HOUR == bill.getType()){
            holder.txtCustomerName.setText(bill.getCustomerName());

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
            holder.recyclerViewItem.setLayoutManager(linearLayoutManager);
            holder.recyclerViewItem.setFocusable(false);

            RoomBillCheckedinAdapter roomBillCheckedinAdapter = new RoomBillCheckedinAdapter(mContext, new IClickItemRoomBillListener() {
                @Override
                public void onClickItem(RoomBill roomBill,Bill billValue) {
                    iClickItemRoomBillListener.onClickItem(roomBill,bill);
                }

                @Override
                public void onLongClickItem(RoomBill roomBill, View view) {
                    iClickItemRoomBillListener.onLongClickItem(roomBill,view);
                }
            });
            roomBillCheckedinAdapter.setData(listdata,bill);

            holder.recyclerViewItem.setAdapter(roomBillCheckedinAdapter);

            holder.txtCustomerName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickBillListener.onClickItem(bill);
                }
            });
            holder.txtCustomerName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    iClickBillListener.onLongClickItem(bill,v);
                    return false;
                }
            });
            if(CheckedinFragment.isUpdate == true){
                Log.d("goi khong updatefirebase","???");
                BillService.updateCost(bill);
                CheckedinFragment.isUpdate = false;
            }
        }
    }
    @Override
    public int getItemCount() {
        if(listRoomCheckedin !=null) return listRoomCheckedin.size();
        return 0;
    }

    public class ListDataViewHolder extends RecyclerView.ViewHolder{

        RecyclerView recyclerViewItem;
        TextView txtCustomerName;

        public ListDataViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewItem = itemView.findViewById(R.id.rcv_checkedin_listdata);
            txtCustomerName = itemView.findViewById(R.id.txt_item_customername_checkedin);
        }
    }

}
