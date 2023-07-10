package com.example.hotelmangementproject.controllers.roomservicescontroller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hotelmangementproject.adapters.roomservicesAdapter.ListDataCheckedinAdapter;
import com.example.hotelmangementproject.firebaseServices.BillService;
import com.example.hotelmangementproject.firebaseServices.BookingService;
import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.Booking;
import com.example.hotelmangementproject.models.RoomBill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CheckedinController {
    public static void getListRoom(Boolean rentByDay, List<Bill> listBill, ListDataCheckedinAdapter listDataCheckedinAdapter){
        int type = 1;
        if(rentByDay != true) type = 2;
        BillService.getBillList(listBill,type,listDataCheckedinAdapter);
    }
    public static void deleteBillOnFireBase(Context context,Bill bill){
        BillService.deleteBill(bill);
    }
}
