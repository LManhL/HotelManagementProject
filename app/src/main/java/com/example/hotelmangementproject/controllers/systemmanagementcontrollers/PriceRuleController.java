package com.example.hotelmangementproject.controllers.systemmanagementcontrollers;

import android.util.Log;

import com.example.hotelmangementproject.adapters.systemmanagementAdapter.PriceRuleAdapter;
import com.example.hotelmangementproject.adapters.systemmanagementAdapter.RoomTypeAdapter;
import com.example.hotelmangementproject.firebaseServices.PriceRuleService;
import com.example.hotelmangementproject.firebaseServices.RoomTypeService;
import com.example.hotelmangementproject.models.CalMoney;

import java.util.ArrayList;
import java.util.List;

public class PriceRuleController {
    public static void getListPriceRule(List<CalMoney> listPriceRule, PriceRuleAdapter priceRuleAdapter){
        PriceRuleService.getListPriceRule(listPriceRule,priceRuleAdapter);
    }
    public static void updatePriceRule(CalMoney calMoney){
        PriceRuleService.updatePriceRule(calMoney);
    }
    public static void getListRoomTypeAvailable(List<String> listRoomType){
        List<CalMoney> listPriceRule = new ArrayList<>();
        RoomTypeService.getListRoomTypeAvailable(listRoomType,listPriceRule, new PriceRuleService.CallBack() {
            @Override
            public void callBackFunc() {
                for(CalMoney calMoney : listPriceRule){
                    for(String roomtype : listRoomType){
                        if(calMoney.getType().equals(roomtype)){
                            listRoomType.remove(roomtype);
                            break;
                        }
                    }
                }
            }
        });
    }
    public static void createPriceRule(CalMoney calMoney){
        PriceRuleService.createPriceRule(calMoney);
    }
    public static void deletePriceRule(CalMoney calMoney){
        PriceRuleService.deletePriceRule(calMoney);
    }
}
