package com.example.hotelmangementproject.controllers.roomservicescontroller;

import com.example.hotelmangementproject.firebaseServices.BillService;
import com.example.hotelmangementproject.models.Bill;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditBillCheckedinController {
    public static void updateToFirebase(Bill bill){
        BillService.updateBill(bill);
    }
}
