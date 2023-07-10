package com.example.hotelmangementproject.controllers.roomservicescontroller;

import com.example.hotelmangementproject.adapters.roomservicesAdapter.RoomHouseKeepingAdapter;
import com.example.hotelmangementproject.firebaseServices.DirtyRoomService;
import com.example.hotelmangementproject.models.DirtyRoom;

import java.util.List;

public class HouseKeepingController {
    public static void getListDirtyRoom(List<DirtyRoom> listRoom, RoomHouseKeepingAdapter roomHouseKeepingAdapter) {
        DirtyRoomService.getListDirtyRoom(listRoom,roomHouseKeepingAdapter);
    }
}
