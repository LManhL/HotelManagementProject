package com.example.hotelmangementproject.interfaces;

import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.models.RoomTypeBooking;

public interface IClickRoomTypeListListener {
    public void onClickAdd(RoomTypeBooking RoomTypeBooking);
    public void onClickMinus(RoomTypeBooking RoomTypeBooking);
    public void onClickAddRoom(RoomTypeBooking RoomTypeBooking);
}
