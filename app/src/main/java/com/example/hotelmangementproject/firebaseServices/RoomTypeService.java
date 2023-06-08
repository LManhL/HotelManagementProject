package com.example.hotelmangementproject.firebaseServices;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hotelmangementproject.adapters.systemmanagementAdapter.RoomTypeAdapter;
import com.example.hotelmangementproject.models.CalMoney;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RoomTypeService {
    public static void getListRoomType(List<String> listRoomType, RoomTypeAdapter roomTypeAdapter){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("roomType");
        Query query = mDatabase.orderByChild("type");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listRoomType != null){
                    listRoomType.clear();
                }
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String roomType = snapshot.getValue(String.class);
                    listRoomType.add(roomType);
                    roomTypeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public static void createRoomType(String roomtype){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("roomType");
        DatabaseReference pushedPostRef = mDatabase.push();
        String id = pushedPostRef.getKey();
        mDatabase.child(id).setValue(roomtype);
    }
    public static void deleteRoomType(String roomtype){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("roomType");
        Query query = mDatabase.orderByValue().equalTo(roomtype);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public static void getListRoomTypeAvailable(List<String> listRoomType, List<CalMoney> listPriceRule, PriceRuleService.CallBack callBack){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("roomType");
        Query query = mDatabase.orderByChild("type");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listRoomType != null){
                    listRoomType.clear();
                }
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String roomType = snapshot.getValue(String.class);
                    listRoomType.add(roomType);
                }
                PriceRuleService.getListPriceRule(listPriceRule,callBack);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
