package com.example.hotelmangementproject.interfaces;

import android.widget.CompoundButton;

import com.example.hotelmangementproject.models.Room;

public interface IClickChooseRoomListener {
    public void clickCheckBox(CompoundButton buttonView, Room room, boolean isChecked);
    public void onClickRoom(Room room);
}
