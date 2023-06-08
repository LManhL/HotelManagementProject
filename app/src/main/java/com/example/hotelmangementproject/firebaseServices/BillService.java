package com.example.hotelmangementproject.firebaseServices;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hotelmangementproject.adapters.roomservicesAdapter.HistoryAdapter;
import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.RoomBill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class BillService {
    public static void updateDataToFireBase(Bill bill){
        DatabaseReference mDatabase  = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("bill").child(bill.getId()).setValue(bill);
    }
    public static void createBill(int type, int state, long createAt, long checkoutTime, List<RoomBill> roomBillList,
                                   String customerName,String phoneNumber,double otherPrice,double prepayment,double surcharge,double menuCost,
                                  double totalRoomcost,double totalPrice, String note){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("bill");
        DatabaseReference pushedPostRef = mDatabase.push();
        String id = pushedPostRef.getKey();
        Bill bill = new Bill(id,type,state, createAt,checkoutTime,roomBillList,
                             customerName,phoneNumber,otherPrice,prepayment,surcharge,menuCost,
                             totalRoomcost,totalPrice, note);
        pushedPostRef.setValue(bill);
    }
    public static void getHistory(List<Bill> listBill, HistoryAdapter historyAdapter) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("bill");
        Query query = mDatabase.orderByChild("state").equalTo(Bill.STATE_CHECK_OUT);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listBill != null){
                    listBill.clear();
                    historyAdapter.notifyDataSetChanged();
                }
                for (DataSnapshot billSnapshot: dataSnapshot.getChildren()) {
                    Bill bill = billSnapshot.getValue(Bill.class);
                    listBill.add(bill);
                    historyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public static void updateCost(Bill bill){
        for(RoomBill roomBill : bill.getListRoomBill()){
            roomBill.caculateRoomCost();
        }
        bill.caculateBill();
        DatabaseReference mDatabase2  = FirebaseDatabase.getInstance().getReference();
        mDatabase2.child("bill").child(bill.getId()).setValue(bill);
    }
}
