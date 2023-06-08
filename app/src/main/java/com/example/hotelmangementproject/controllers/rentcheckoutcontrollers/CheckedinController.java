package com.example.hotelmangementproject.controllers.rentcheckoutcontrollers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hotelmangementproject.adapters.roomservicesAdapter.ListDataCheckedinAdapter;
import com.example.hotelmangementproject.models.Bill;
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
        Query query;

        query = FirebaseDatabase.getInstance().getReference("bill").orderByChild("state").equalTo(1);

        int finalType = type;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listBill != null){
                    listBill.clear();
                    listDataCheckedinAdapter.notifyDataSetChanged();
                }
                for (DataSnapshot roomSnapshot: dataSnapshot.getChildren()) {
                    Bill bill = roomSnapshot.getValue(Bill.class);
                    if(bill.getType() == finalType) {
                        listBill.add(bill);
                        listDataCheckedinAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public static void deleteBillOnFireBase(Context context,Bill bill){
        DatabaseReference mDatabase2  = FirebaseDatabase.getInstance().getReference();
        mDatabase2.child("bill").child(bill.getId()).removeValue();
        // Change every room in bill to available
        for(RoomBill roomBill : bill.getListRoomBill()){
            mDatabase2.child("room").child(roomBill.getId()).child("roomState").setValue(Bill.STATE_CHECKED_IN);
        }
        Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
    }
}
