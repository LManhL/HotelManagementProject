package com.example.hotelmangementproject.controllers.rentcheckoutcontrollers;

import com.example.hotelmangementproject.adapters.roomservicesAdapter.ListMenuAdapter;
import com.example.hotelmangementproject.firebaseServices.MenuService;
import com.example.hotelmangementproject.models.MenuBill;

import java.util.List;

public class EditMenuDialogController {
    public static void getListRoomFromFireBase(List<MenuBill> listMenuBill, List<MenuBill> prevList, ListMenuAdapter listMenuAdapter){
        MenuService.getListMenuBill(listMenuBill,prevList,listMenuAdapter);
    }
}
