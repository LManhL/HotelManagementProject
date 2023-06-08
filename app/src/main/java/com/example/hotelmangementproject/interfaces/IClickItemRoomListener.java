package com.example.hotelmangementproject.interfaces;

import android.view.View;

import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.models.RoomBill;

public interface IClickItemRoomListener {
    void onClickItem(Room room);
    void onLongClickItem(Room room, View view);
}
