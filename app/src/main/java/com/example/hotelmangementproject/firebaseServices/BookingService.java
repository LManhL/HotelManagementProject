package com.example.hotelmangementproject.firebaseServices;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.MainActivity;
import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.Booking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class BookingService {
    public static void getBookingList(List<Booking> bookingList, RecyclerView.Adapter adapter){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("booking");
        Query query = mDatabase.orderByChild("checkin");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(bookingList != null){
                    bookingList.clear();
                }
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Booking booking = snapshot.getValue(Booking.class);
                    bookingList.add(booking);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public static void getBookingList(List<Booking> bookingList, CallBackGetBookingList callBackGetBookingList){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("booking");
        Query query = mDatabase.orderByChild("checkin");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(bookingList != null){
                    bookingList.clear();
                }
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Booking booking = snapshot.getValue(Booking.class);
                    bookingList.add(booking);
                }
                callBackGetBookingList.callBack();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public static void updateStateBookingIfBillFromBooking(Bill bill, int state){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("booking");
        Query query = mDatabase.orderByChild("state");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Booking booking = snapshot.getValue(Booking.class);
                    if(bill.getId().equals(booking.getId())){
                        updateStateBooking(booking.getId(),state);
                        break;
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
    public static void updateBooking(Booking booking){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID);
        mDatabase.child("booking").child(booking.getId()).setValue(booking);
    }
    public static void createBooking(Booking booking){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("booking").push();
        String key = mDatabase.getKey();
        booking.setId(key);
        mDatabase.setValue(booking);
    }
    public static void updateStateBooking(String id, int state){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("booking");
        mDatabase.child(id).child("state").setValue(state);
    }
    public interface CallBackGetBookingList{
        public void callBack();
    }
}
