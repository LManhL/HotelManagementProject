package com.example.hotelmangementproject.controllers.rentcheckoutcontrollers;

import com.example.hotelmangementproject.adapters.roomservicesAdapter.HistoryAdapter;
import com.example.hotelmangementproject.firebaseServices.BillService;
import com.example.hotelmangementproject.models.Bill;

import java.util.List;

public class HistoryController {
    public static void getHistory(List<Bill> listBill, HistoryAdapter historyAdapter) {
        BillService.getHistory(listBill,historyAdapter);
    }
}
