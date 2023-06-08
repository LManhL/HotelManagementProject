package com.example.hotelmangementproject.firebaseServices;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hotelmangementproject.adapters.roomservicesAdapter.RoomAvailableAdapter;
import com.example.hotelmangementproject.models.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RoomService {
    public static void getListRoom(List<Room> listRoom, RoomAvailableAdapter roomAvailableAdapter){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("room");
        Query query = mDatabase.orderByChild("roomState").equalTo(Room.STATE_AVAILABLE);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listRoom != null){
                    listRoom.clear();
                }
                for (DataSnapshot roomSnapshot: dataSnapshot.getChildren()) {
                    Log.d("key",roomSnapshot.getKey());
                    Room room = roomSnapshot.getValue(Room.class);
                    listRoom.add(room);
                    roomAvailableAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public static void changeRoomState(Room room, int state){
        DatabaseReference mDatabase  = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("room").child(room.getId()).child("roomState").setValue(state);
    }
}
