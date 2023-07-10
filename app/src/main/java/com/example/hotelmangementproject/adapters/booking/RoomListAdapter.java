package com.example.hotelmangementproject.adapters.booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.interfaces.IClickItemRoomBookingListener;
import com.example.hotelmangementproject.interfaces.IClickItemRoomListener;
import com.example.hotelmangementproject.interfaces.IClickRoomTypeListListener;
import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.models.RoomTypeBooking;

import java.util.List;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.RoomListViewHolder> {

    List<Room> roomList;
    Context mContext;
    IClickItemRoomBookingListener iClickItemRoomBookingListener;

    public RoomListAdapter(Context mContext, List<Room> roomList, IClickItemRoomBookingListener iClickItemRoomBookingListener){
        this.mContext = mContext;
        this.roomList = roomList;
        this.iClickItemRoomBookingListener = iClickItemRoomBookingListener;
    }

    @NonNull
    @Override
    public RoomListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_bk_item_room,parent,false);
        return new RoomListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomListViewHolder holder, int position) {
        Room room = roomList.get(position);
        if(room == null) return;
        holder.txtName.setText(room.getName());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemRoomBookingListener.onClickDelete(new RoomTypeBooking(), room);
            }
        });
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemRoomBookingListener.onClick(room);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(roomList != null){
            return roomList.size();
        }
        return 0;
    }

    public class RoomListViewHolder extends RecyclerView.ViewHolder{
        ImageView btnDelete;
        TextView txtName;
        RelativeLayout relativeLayout;

        public RoomListViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.btn_delete_room_frag_bk_item_room);
            txtName = itemView.findViewById(R.id.txt_name_frag_bk_item_room);
            relativeLayout = itemView.findViewById(R.id.relativelayout_cover_item_frag_bk_item_room);
        }
    }
}
