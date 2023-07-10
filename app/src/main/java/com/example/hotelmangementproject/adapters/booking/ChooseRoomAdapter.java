package com.example.hotelmangementproject.adapters.booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.interfaces.IClickChooseRoomListener;
import com.example.hotelmangementproject.models.Room;

import java.util.List;

public class ChooseRoomAdapter extends RecyclerView.Adapter<ChooseRoomAdapter.RoomAvailableViewHolder> {

    private Context mContext;
    private List<Room> listRoom;
    private IClickChooseRoomListener iClickChooseRoomListener;

    public ChooseRoomAdapter(Context mContext, List<Room> listRoom, IClickChooseRoomListener iClickChooseRoomListener){
        this.listRoom = listRoom;
        this.iClickChooseRoomListener = iClickChooseRoomListener;
        this.mContext = mContext;
    }
    public void setData (List<Room> list){
        this.listRoom = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChooseRoomAdapter.RoomAvailableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_bk_item_choose_room,parent,false);
        return new ChooseRoomAdapter.RoomAvailableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseRoomAdapter.RoomAvailableViewHolder holder, int position) {
        Room room = listRoom.get(position);
        if(room == null){
            return;
        }
        holder.imgHotel.setImageResource(R.drawable.room);
        holder.txtType.setText(room.getRoomTypes());
        holder.txtState.setText(room.converStateToString());
        holder.txtName.setText(room.getName().toUpperCase());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickChooseRoomListener.onClickRoom(room);
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                iClickChooseRoomListener.clickCheckBox(buttonView,room,isChecked);
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
        private CheckBox checkBox;

        private RelativeLayout relativeLayout;
        public RoomAvailableViewHolder(@NonNull View itemView) {
            super(itemView);

            imgHotel = itemView.findViewById(R.id.img_item_available);
            txtName = itemView.findViewById(R.id.txt_name_item_avaiable);
            txtState = itemView.findViewById(R.id.txt_state_item_available);
            txtType = itemView.findViewById(R.id.txt_type_item_available);
            relativeLayout = itemView.findViewById(R.id.relativelayout_cover_item_available);
            checkBox = itemView.findViewById(R.id.checkbox_item_available);
        }
    }
}
