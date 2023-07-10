package com.example.hotelmangementproject.adapters.booking;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.interfaces.IClickChooseRoomListener;
import com.example.hotelmangementproject.interfaces.IClickItemRoomBookingListener;
import com.example.hotelmangementproject.interfaces.IClickRoomTypeListListener;
import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.models.RoomTypeBooking;

import java.util.List;

public class RoomTypeListAdapter extends RecyclerView.Adapter<RoomTypeListAdapter.RoomTypeListViewHolder> {
    List<RoomTypeBooking> roomtypeList;
    Context mContext;
    IClickRoomTypeListListener iClickRoomTypeListListener;
    IClickItemRoomBookingListener iClickItemRoomBookingListener;
    public RoomTypeListAdapter(Context context , List<RoomTypeBooking> roomtypeList,IClickRoomTypeListListener iClickRoomTypeListListener,IClickItemRoomBookingListener iClickItemRoomBookingListener){
        this.mContext = context;
        this.roomtypeList = roomtypeList;
        this.iClickRoomTypeListListener = iClickRoomTypeListListener;
        this.iClickItemRoomBookingListener = iClickItemRoomBookingListener;
    }
    @NonNull
    @Override
    public RoomTypeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_bk_item_roomtype_list,parent,false);
        return new RoomTypeListAdapter.RoomTypeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomTypeListViewHolder holder, int position) {
        RoomTypeBooking roomtype = roomtypeList.get(holder.getAdapterPosition());
        if(roomtype == null) return;
        holder.edtCount.setText(roomtype.getCount()+ "");
        holder.edtNameRoomType.setText(roomtype.getType());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
        RoomListAdapter adapter = new RoomListAdapter(mContext, roomtype.getRoomList(), new IClickItemRoomBookingListener() {
            @Override
            public void onClick(Room room) {
                iClickItemRoomBookingListener.onClick(room);
            }

            @Override
            public void onClickDelete(RoomTypeBooking roomTypeBooking, Room room) {
                iClickItemRoomBookingListener.onClickDelete(roomtype, room);
            }
        });

        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setLayoutManager(gridLayoutManager);

        holder.btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickRoomTypeListListener.onClickAddRoom(roomtype);
            }
        });


        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickRoomTypeListListener.onClickAdd(roomtype);
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickRoomTypeListListener.onClickMinus(roomtype);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(roomtypeList != null){
            return roomtypeList.size();
        }
        return 0;
    }

    public class RoomTypeListViewHolder extends RecyclerView.ViewHolder{
        EditText edtCount;
        ImageView btnAdd;
        ImageView btnMinus;
        EditText edtNameRoomType;

        RecyclerView recyclerView;
        Button btnAddRoom;

        public RoomTypeListViewHolder(@NonNull View itemView) {
            super(itemView);
            edtNameRoomType = itemView.findViewById(R.id.edt_name_frag_bk_item_roomtype_list);
            btnAdd = itemView.findViewById(R.id.btn_add_frag_bk_item_roomtype_list);
            btnMinus = itemView.findViewById(R.id.btn_minus_frag_bk_item_roomtype_list);
            edtCount = itemView.findViewById(R.id.edt_count_frag_bk_item_roomtype_list);
            recyclerView = itemView.findViewById(R.id.rec_roomlist_of_type_frag_bk_item_roomtype_list);
            btnAddRoom = itemView.findViewById(R.id.btn_add_extra_room_frag_bk_item_roomtype_list);
        }
    }
}
