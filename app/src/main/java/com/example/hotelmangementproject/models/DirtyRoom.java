package com.example.hotelmangementproject.models;

import java.util.List;

public class DirtyRoom extends Room{
    private long checkoutTime;


    public DirtyRoom(){

    }

    public DirtyRoom(RoomBill room){
        super(room.getId(), room.getDescription(),room.getName(), room.getRoomTypes(), Room.STATE_HOUSEKEEPING,room.getCalMoney(),room.getListImage());
        this.checkoutTime = room.getCheckoutTime();
    }

    public DirtyRoom(String id, String description, String name, List<String> listImage, String roomTypes, int state, CalMoney calMoney, long checkoutTime) {
        super(id, description, name, roomTypes, state, calMoney,listImage);
        this.checkoutTime = checkoutTime;
    }

    public long getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(long checkoutTime) {
        this.checkoutTime = checkoutTime;
    }
}
