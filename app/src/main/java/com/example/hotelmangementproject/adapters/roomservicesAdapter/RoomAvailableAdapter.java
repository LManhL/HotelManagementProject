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
import com.example.hotelmangementproject.interfaces.IClickItemRoomListener;
import com.example.hotelmangementproject.models.Room;

import java.util.List;

public class RoomAvailableAdapter extends RecyclerView.Adapter<RoomAvailableAdapter.RoomAvailableViewHolder> {
    private Context mContext;
    private List<Room> listRoom;
    private IClickItemRoomListener iClickItemRoomListener;

    public RoomAvailableAdapter(Context mContext, List<Room> listRoom, IClickItemRoomListener iClickItemRoomListener){
        this.listRoom = listRoom;
        this.iClickItemRoomListener = iClickItemRoomListener;
        this.mContext = mContext;
    }
    public void setData (List<Room> list){
        this.listRoom = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomAvailableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_rs_item_available,parent,false);
        return new RoomAvailableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAvailableViewHolder holder, int position) {
        Room room = listRoom.get(position);
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

        holder.txtType.setText(room.getRoomTypes());
        holder.txtState.setText(room.converStateToString());
        holder.txtName.setText(room.getName().toUpperCase());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemRoomListener.onClickItem(room);
            }
        });
        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                iClickItemRoomListener.onLongClickItem(room,v);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listRoom != null){
            return listRoom.size();
        }
        return 0;
    }

    public class RoomAvailableViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgHotel;
        private TextView txtName;
        private TextView txtState;
        private TextView txtType;

        private RelativeLayout relativeLayout;
        public RoomAvailableViewHolder(@NonNull View itemView) {
            super(itemView);

            imgHotel = itemView.findViewById(R.id.img_item_available);
            txtName = itemView.findViewById(R.id.txt_name_item_avaiable);
            txtState = itemView.findViewById(R.id.txt_state_item_available);
            txtType = itemView.findViewById(R.id.txt_type_item_available);
            relativeLayout = itemView.findViewById(R.id.relativelayout_cover_item_available);
        }
    }
}
