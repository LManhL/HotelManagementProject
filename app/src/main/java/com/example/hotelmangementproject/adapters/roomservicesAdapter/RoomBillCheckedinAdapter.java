package com.example.hotelmangementproject.adapters.roomservicesAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotelmangementproject.services.TimeService;
import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.interfaces.IClickItemRoomBillListener;
import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.RoomBill;

import java.util.List;

public class RoomBillCheckedinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_RENT_BY_DAY = 1;
    public static final int TYPE_RENT_BY_HOUR = 2;

    private IClickItemRoomBillListener iClickItemRoomBillListener;


    private Context mContext;
    private List<RoomBill> listRoomCheckedin;
    private Bill bill;

    public RoomBillCheckedinAdapter(Context mContext, IClickItemRoomBillListener iClickItemRoomBillListener){
        this.mContext = mContext;
        this.iClickItemRoomBillListener = iClickItemRoomBillListener;
    }

    public void setData (List<RoomBill> list,Bill bill){
        this.listRoomCheckedin = list;
        this.bill = bill;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return listRoomCheckedin.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(TYPE_RENT_BY_DAY == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_rs_item_bill_rentbyday,parent,false);
            return new RoomCheckedinRentByDayViewHolder(view);
        }
        else if(TYPE_RENT_BY_HOUR == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_rs_item_bill_rentbyhour,parent,false);
            return new RoomCheckedinRentByHourViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RoomBill roomBill = listRoomCheckedin.get(position);
        if(roomBill==null) return;
        if(TYPE_RENT_BY_DAY == holder.getItemViewType()){
            RoomCheckedinRentByDayViewHolder roomCheckedinRentByDayViewHolder = (RoomCheckedinRentByDayViewHolder) holder;
            if(roomBill.getListImage() != null){
                String url = roomBill.getListImage().get(0);
                if(url != null){
                    Glide.with(mContext).load(url).into(((RoomCheckedinRentByDayViewHolder) holder).imgHotel);
                }
            }
            else{
                ((RoomCheckedinRentByDayViewHolder) holder).imgHotel.setImageResource(R.drawable.room);
            }
            roomCheckedinRentByDayViewHolder.txtStartTime.setText("From: " + TimeService.convertMilisecondsToDate(roomBill.getCheckinTime()));
            roomCheckedinRentByDayViewHolder.txtEndTime.setText("To: " + TimeService.convertMilisecondsToDate(roomBill.getCheckoutTime()));
            roomCheckedinRentByDayViewHolder.txtPrice.setText(Double.toString(roomBill.getRoomCost()));
            roomCheckedinRentByDayViewHolder.txtRoomName.setText(roomBill.getName().toUpperCase());

            roomCheckedinRentByDayViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bill == null) bill = new Bill();
                    iClickItemRoomBillListener.onClickItem(roomBill,bill);
                }
            });
            roomCheckedinRentByDayViewHolder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    iClickItemRoomBillListener.onLongClickItem(roomBill,v);
                    return false;
                }
            });
        }
        else if(TYPE_RENT_BY_HOUR == holder.getItemViewType()){
            RoomCheckedinRentByHourViewHolder roomCheckedinRentByHourViewHolder = (RoomCheckedinRentByHourViewHolder) holder;
            if(roomBill.getListImage() != null){
                String url = roomBill.getListImage().get(0);
                if(url != null){
                    Glide.with(mContext).load(url).into(((RoomCheckedinRentByHourViewHolder) holder).imgHotel);
                }
            }
            else{
                ((RoomCheckedinRentByHourViewHolder) holder).imgHotel.setImageResource(R.drawable.room);
            }
            roomCheckedinRentByHourViewHolder.txtCheckinTime.setText("From: "+ TimeService.convertMilisecondsToDate(roomBill.getCheckinTime()));
            roomCheckedinRentByHourViewHolder.curPrice.setText(Double.toString(roomBill.getRoomCost()));
            roomCheckedinRentByHourViewHolder.txtRoomName.setText(roomBill.getName().toUpperCase());
            roomCheckedinRentByHourViewHolder.txtStaytime.setText("Stay time: "+TimeService.calStaytime(roomBill));

            roomCheckedinRentByHourViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bill == null) bill = new Bill();
                    iClickItemRoomBillListener.onClickItem(roomBill,bill);
                }
            });
            roomCheckedinRentByHourViewHolder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    iClickItemRoomBillListener.onLongClickItem(roomBill,v);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(listRoomCheckedin != null)
            return listRoomCheckedin.size();
        return 0;
    }

    public class RoomCheckedinRentByDayViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgHotel;
        private TextView txtStartTime;
        private TextView txtEndTime;
        private TextView txtPrice;
        private TextView txtRoomName;
        private RelativeLayout relativeLayout;

        public RoomCheckedinRentByDayViewHolder(@NonNull View itemView)
        {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relativelayout_cover_item_rentbyday);
            imgHotel = itemView.findViewById(R.id.img_item_checkedin_rentbyday);
            txtStartTime = itemView.findViewById(R.id.txt_starttime_item_checkedin_rentbyday);
            txtEndTime = itemView.findViewById(R.id.txt_endtime_item_rentbyday);
            txtPrice = itemView.findViewById(R.id.txt_price_item_checkedin_rentbyday);
            txtRoomName = itemView.findViewById(R.id.txt_room_name_item_checkedin_rentbyday);
        }
    }

    public class RoomCheckedinRentByHourViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgHotel;
        private TextView txtCheckinTime;
        private TextView curPrice;
        private TextView txtRoomName;
        private TextView txtStaytime;
        private RelativeLayout relativeLayout;
        public RoomCheckedinRentByHourViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relativelayout_cover_item_rentbyhour);
            imgHotel = itemView.findViewById(R.id.img_item_checkedin_rentbyhour);
            txtCheckinTime = itemView.findViewById(R.id.txt_starttime_item_checkedin_rentbyhour);
            curPrice = itemView.findViewById((R.id.txt_currentprice_item_rentbyhour));
            txtRoomName = itemView.findViewById(R.id.txt_room_name_item_checkedin_rentbyhour);
            txtStaytime = itemView.findViewById(R.id.txt_staytime_item_checkedin_rentbyhour);
        }
    }

}
