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
import com.example.hotelmangementproject.adapters.roomservicesAdapter.HistoryAdapter;
import com.example.hotelmangementproject.interfaces.IClickRoomTypeListener;

import java.util.List;

public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapter.RoomTypeViewHoder> {

    private Context mContext;
    private List<String> listRoomTypes;
    private IClickRoomTypeListener iClickRoomTypeListener;

    public RoomTypeAdapter(Context mContext, List<String> listRoomTypes, IClickRoomTypeListener iClickRoomTypeListener){
        this.mContext = mContext;
        this.listRoomTypes = listRoomTypes;
        this.iClickRoomTypeListener = iClickRoomTypeListener;
    }

    @NonNull
    @Override
    public RoomTypeViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_sm_item_roomtype,parent,false);
        return new RoomTypeAdapter.RoomTypeViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomTypeViewHoder holder, int position) {
        String roomType = listRoomTypes.get(position);
        if(roomType == null) return;

        holder.txtId.setText(Integer.toString(position + 1));
        holder.txtType.setText(roomType);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickRoomTypeListener.onClick(roomType);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listRoomTypes != null) return listRoomTypes.size();
        return 0;
    }

    public class RoomTypeViewHoder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        TextView txtId;
        TextView txtType;

        public RoomTypeViewHoder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearlayout_frag_sm_item_roomtype);
            txtId = itemView.findViewById(R.id.txtID_frag_sm_item_roomtype);
            txtType = itemView.findViewById(R.id.txtType_frag_sm_item_roomtype);
        }
    }
}
