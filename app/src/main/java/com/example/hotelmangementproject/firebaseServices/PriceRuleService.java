package com.example.hotelmangementproject.firebaseServices;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hotelmangementproject.adapters.systemmanagementAdapter.PriceRuleAdapter;
import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.CalMoney;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

public class PriceRuleService {
    public static void getListPriceRule(List<CalMoney> listPriceRule, PriceRuleAdapter priceRuleAdapter){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("calMoney");
        Query query = mDatabase;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listPriceRule != null){
                    listPriceRule.clear();
                    priceRuleAdapter.notifyDataSetChanged();
                }
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    CalMoney calMoney = snapshot.getValue(CalMoney.class);
                    if(calMoney != null) listPriceRule.add(calMoney);
                    priceRuleAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public static void getListPriceRule(List<CalMoney> listPriceRule, CallBack callBack){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("calMoney");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listPriceRule != null){
                    listPriceRule.clear();
                }
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    CalMoney calMoney = snapshot.getValue(CalMoney.class);
                    if(calMoney != null) listPriceRule.add(calMoney);
                }
                callBack.callBackFunc();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public static void updatePriceRule(CalMoney calMoney){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("calMoney").child(calMoney.getId());
        mDatabase.setValue(calMoney);
    }
    public static void createPriceRule(CalMoney calMoney){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("calMoney").push();
        String key = mDatabase.getKey();
        calMoney.setId(key);
        mDatabase.setValue(calMoney);
    }
    public static void findPriceRule(String type,CallBackCalMoney callBack){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("calMoney");
        Query query = mDatabase.orderByChild("type").equalTo(type);
        List<CalMoney> list = new ArrayList<>();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    CalMoney calMoney = snapshot.getValue(CalMoney.class);
                    list.add(calMoney);
                }
                callBack.callBackFunc(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void deletePriceRule(CalMoney calMoney){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("calMoney");
        mDatabase.child(calMoney.getId()).removeValue();
    }
    public interface CallBack{
        public void callBackFunc();
    }
    public interface CallBackCalMoney{
        public void callBackFunc(List<CalMoney> list);
    }
}
