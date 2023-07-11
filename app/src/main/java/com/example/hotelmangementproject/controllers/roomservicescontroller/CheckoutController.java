package com.example.hotelmangementproject.controllers.roomservicescontroller;

import android.content.Context;
import android.widget.Toast;

import com.example.hotelmangementproject.firebaseServices.BillService;
import com.example.hotelmangementproject.firebaseServices.BookingService;
import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.Booking;
import com.example.hotelmangementproject.models.DirtyRoom;
import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.models.RoomBill;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckoutController {
    public static void checkoutBillOnFireBase(Context context, Bill bill){
        BillService.checkoutBill(bill);
        Toast.makeText(context, "Checkout successfully", Toast.LENGTH_SHORT).show();
    }
    public static void updateToFirebase(Bill bill){
        BillService.updateBill(bill);
    }
}
