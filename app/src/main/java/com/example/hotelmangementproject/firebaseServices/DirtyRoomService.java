package com.example.hotelmangementproject.firebaseServices;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hotelmangementproject.adapters.roomservicesAdapter.RoomHouseKeepingAdapter;
import com.example.hotelmangementproject.models.DirtyRoom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DirtyRoomService {
    public static void getListDirtyRoom(List<DirtyRoom> listRoom, RoomHouseKeepingAdapter roomHouseKeepingAdapter) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("dirtyRoom");
        Query query = mDatabase.orderByChild("roomTypes");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listRoom != null){
                    listRoom.clear();
                    roomHouseKeepingAdapter.notifyDataSetChanged();
                }
                for (DataSnapshot roomSnapshot: dataSnapshot.getChildren()) {
                    DirtyRoom room = roomSnapshot.getValue(DirtyRoom.class);
                    listRoom.add(room);
                    roomHouseKeepingAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
