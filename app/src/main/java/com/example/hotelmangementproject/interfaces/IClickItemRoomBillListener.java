package com.example.hotelmangementproject.interfaces;

import android.view.View;

import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.RoomBill;

public interface IClickItemRoomBillListener {

    void onClickItem(RoomBill roomBill, Bill bill);
    void onLongClickItem(RoomBill roomBill, View view);
}
