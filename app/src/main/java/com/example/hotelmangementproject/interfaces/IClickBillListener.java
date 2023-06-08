package com.example.hotelmangementproject.interfaces;

import android.view.View;

import com.example.hotelmangementproject.models.Bill;

public interface IClickBillListener {
    public void onClickItem(Bill bill);
    public void onLongClickItem(Bill bill, View v);
}
