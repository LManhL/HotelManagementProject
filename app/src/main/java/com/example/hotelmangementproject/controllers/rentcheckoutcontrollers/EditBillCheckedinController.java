package com.example.hotelmangementproject.controllers.rentcheckoutcontrollers;

import com.example.hotelmangementproject.models.Bill;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditBillCheckedinController {
    public static void updateToFirebase(Bill bill){
        DatabaseReference mDatabase2  = FirebaseDatabase.getInstance().getReference();
        mDatabase2.child("bill").child(bill.getId()).setValue(bill);
    }
}
