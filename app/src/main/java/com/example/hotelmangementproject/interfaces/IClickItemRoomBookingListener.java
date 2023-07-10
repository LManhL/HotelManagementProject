package com.example.hotelmangementproject.interfaces;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.models.RoomTypeBooking;

public interface IClickItemRoomBookingListener {
    public void onClick(Room room);
    public void onClickDelete(RoomTypeBooking roomTypeBooking, Room room);
}
