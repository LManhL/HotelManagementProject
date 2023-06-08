package com.example.hotelmangementproject.controllers.rentcheckoutcontrollers;

import com.example.hotelmangementproject.firebaseServices.BillService;
import com.example.hotelmangementproject.models.Bill;

public class EditCheckedinController {
    public static void updateDataToFireBase(Bill bill){
        BillService.updateDataToFireBase(bill);
    }
}
