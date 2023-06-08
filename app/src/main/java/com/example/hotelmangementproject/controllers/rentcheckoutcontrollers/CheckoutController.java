package com.example.hotelmangementproject.controllers.rentcheckoutcontrollers;

import android.content.Context;
import android.widget.Toast;

import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.DirtyRoom;
import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.models.RoomBill;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckoutController {
    public static void checkoutBillOnFireBase(Context context, Bill bill){
        DatabaseReference mDatabase2  = FirebaseDatabase.getInstance().getReference();
        mDatabase2.child("bill").child(bill.getId()).child("state").setValue(Bill.STATE_CHECK_OUT);
        // Change every room in bill to available
        for(RoomBill roomBill : bill.getListRoomBill()){
            DirtyRoom dirtyRoom = new DirtyRoom(roomBill);
            mDatabase2.child("dirtyRoom").child(roomBill.getId()).setValue(dirtyRoom);
            mDatabase2.child("room").child(roomBill.getId()).child("roomState").setValue(Room.STATE_HOUSEKEEPING);
        }
        Toast.makeText(context, "Checkout successfully", Toast.LENGTH_SHORT).show();
    }
    public static void updateToFirebase(Bill bill){
        DatabaseReference mDatabase2  = FirebaseDatabase.getInstance().getReference();
        mDatabase2.child("bill").child(bill.getId()).setValue(bill);
    }
}
