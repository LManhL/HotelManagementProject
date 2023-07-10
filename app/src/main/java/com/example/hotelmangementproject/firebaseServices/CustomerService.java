package com.example.hotelmangementproject.firebaseServices;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.MainActivity;
import com.example.hotelmangementproject.models.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CustomerService {
    public static void getListCustomer(List<Customer> listCustomer, RecyclerView.Adapter adapter){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("customer");
        Query query = mDatabase.orderByChild("name");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listCustomer != null){
                    listCustomer.clear();
                }
                for (DataSnapshot customerSnapshot: dataSnapshot.getChildren()) {
                    Customer customer = customerSnapshot.getValue(Customer.class);
                    listCustomer.add(customer);
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
    public static void createCustomer(Customer customer){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("customer").push();
        String key = mDatabase.getKey();
        customer.setId(key);
        mDatabase.setValue(customer);
    }
    public static void updateCustomer(Customer customer){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("customer");
        mDatabase.child(customer.getId()).setValue(customer);
    }

    public static void deleteCustomer(Customer customer){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("customer");
        mDatabase.child(customer.getId()).removeValue();
    }
}
