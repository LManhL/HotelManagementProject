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
import com.example.hotelmangementproject.interfaces.IClickItemRoomListener;
import com.example.hotelmangementproject.models.Room;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder>{
    List<Room> listRoom;
    Context mContext;
    IClickItemRoomListener iClickItemRoomListener;

    public RoomAdapter(Context context, List<Room> listRoom, IClickItemRoomListener iClickItemRoomListener){
        this.mContext = context;
        this.listRoom = listRoom;
        this.iClickItemRoomListener = iClickItemRoomListener;
    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_sm_item_room,parent,false);
        return new RoomAdapter.RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = listRoom.get(position);
        if(room == null) return;
        holder.txtId.setText(String.valueOf(position+1));
        holder.txtName.setText(room.getName().toUpperCase());
        holder.txtType.setText(room.getRoomTypes());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemRoomListener.onClickItem(room);
            }
        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                iClickItemRoomListener.onLongClickItem(room,v);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listRoom != null) return listRoom.size();
        return 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        TextView txtId;
        TextView txtName;
        TextView txtType;
        LinearLayout linearLayout;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtID_frag_sm_item_room);
            txtName = itemView.findViewById(R.id.txtName_frag_sm_item_room);
            txtType = itemView.findViewById(R.id.txtType_frag_sm_item_room);
            linearLayout = itemView.findViewById(R.id.linearlayout_frag_sm_item_room);
        }
    }
}
