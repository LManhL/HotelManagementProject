package com.example.hotelmangementproject.controllers.systemmanagementcontrollers;

import com.example.hotelmangementproject.adapters.systemmanagementAdapter.RoomTypeAdapter;
import com.example.hotelmangementproject.firebaseServices.RoomTypeService;

import java.util.List;

public class RoomTypeController {
    public static void getListRoomType(List<String> listRoomType, RoomTypeAdapter roomTypeAdapter){
        RoomTypeService.getListRoomType(listRoomType,roomTypeAdapter);
    }
    public static void createRoomType(String roomtype){
        RoomTypeService.createRoomType(roomtype);
    }
    public static void deleteRoomType(String roomtype){
        RoomTypeService.deleteRoomType(roomtype);
    }
}
