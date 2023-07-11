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
import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.interfaces.IClickDirtyRoomListener;
import com.example.hotelmangementproject.models.DirtyRoom;
import com.example.hotelmangementproject.services.TimeService;

import java.util.List;

public class RoomHouseKeepingAdapter extends RecyclerView.Adapter<RoomHouseKeepingAdapter.RoomHouseKeepingViewHolder> {

    List<DirtyRoom> dirtyRoomList;
    Context mContext;
    IClickDirtyRoomListener iClickDirtyRoomListener;

    public RoomHouseKeepingAdapter(Context mContext, IClickDirtyRoomListener iClickDirtyRoomListener){
        this.mContext = mContext;
        this.iClickDirtyRoomListener = iClickDirtyRoomListener;
    }
    public void setData(Context context,List<DirtyRoom> list){
        this.dirtyRoomList = list;
        this.mContext = context;
    }
    @NonNull
    @Override
    public RoomHouseKeepingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_rs_item_house_keeping,parent,false);
        return new RoomHouseKeepingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomHouseKeepingAdapter.RoomHouseKeepingViewHolder holder, int position) {
        DirtyRoom room = dirtyRoomList.get(position);
        if(room == null){
            return;
        }
        if(room.getListImage() != null){
            String url = room.getListImage().get(0);
            if(url != null){
                Glide.with(mContext).load(url).into(holder.imgHotel);
            }
        }
        else{
            holder.imgHotel.setImageResource(R.drawable.room);
        }
        holder.txtName.setText(room.getName().toUpperCase());
        holder.txtCheckoutTime.setText("Left-time: "+ TimeService.convertMilisecondsToDate(room.getCheckoutTime()));
        holder.txtWaitingTime.setText("Waiting time: "+TimeService.calWaitingTime(room));
        holder.txtState.setText("Need cleaning");

        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                iClickDirtyRoomListener.onLongClick(room);
                return false;
            }
        });
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickDirtyRoomListener.onClick(room);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dirtyRoomList != null) return dirtyRoomList.size();
        return 0;
    }

    public class RoomHouseKeepingViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHotel;
        TextView txtName;
        TextView txtCheckoutTime;
        TextView txtWaitingTime;
        TextView txtState;
        RelativeLayout relativeLayout;

        public RoomHouseKeepingViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHotel = itemView.findViewById(R.id.img_item_housekeeping);
            txtName = itemView.findViewById(R.id.txt_room_name_item_housekeeping);
            txtCheckoutTime = itemView.findViewById(R.id.txt_checkout_time_item_housekeeping);
            txtWaitingTime = itemView.findViewById(R.id.txt_watingtime_item_housekeeping);
            txtState = itemView.findViewById(R.id.txt_state_item_housekeeping);
            relativeLayout = itemView.findViewById(R.id.relativelayout_cover_item_housekeeping);
        }
    }
}
