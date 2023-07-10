package com.example.hotelmangementproject.models;

import java.util.List;

public class RoomTypeBooking {
    private int count;
    private String id;
    private String type;
    private List<Room> roomList;

    public RoomTypeBooking() {
    }

    public RoomTypeBooking(String id,int count, String type, List<Room> roomList) {
        this.count = count;
        this.id = id;
        this.type = type;
        this.roomList = roomList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }
}
