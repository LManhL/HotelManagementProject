package com.example.hotelmangementproject.interfaces;

import android.view.View;

import com.example.hotelmangementproject.models.CalMoney;

public interface IClickPriceRule {
    public void onClick(CalMoney calMoney);
    public void onLongClick(View v, CalMoney calMoney);
}
