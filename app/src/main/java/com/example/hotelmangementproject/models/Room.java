package com.example.hotelmangementproject.models;

import java.util.ArrayList;
import java.util.List;

public class Room {
    public static final int STATE_AVAILABLE = 1;
    public static final int STATE_CHECKEDIN = 2;
    public static final int STATE_HOUSEKEEPING = 3;
    private String id;
    private String description;
    private String name;
    private String roomTypes;
    private int roomState;
    private CalMoney calMoney;

    private List<String> listImage;


    public Room(){
        this.id = "0";
        this.description = "";
        this.name = "";
        this.roomTypes = "";
        this.roomState = 1;
        this.calMoney = new CalMoney();
        this.listImage = new ArrayList<>();
    }
    public Room(String id, String description, String name, String roomTypes, int roomState, CalMoney calMoney, List<String> listImage) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.roomTypes = roomTypes;
        this.roomState = roomState;
        this.calMoney = calMoney;
        this.listImage = listImage;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(String roomTypes) {
        this.roomTypes = roomTypes;
    }

    public int getRoomState() {
        return roomState;
    }

    public void setRoomState(int roomState) {
        this.roomState = roomState;
    }

    public CalMoney getCalMoney() {
        return calMoney;
    }

    public void setCalMoney(CalMoney calMoney) {
        this.calMoney = calMoney;
    }

    public List<String> getListImage() {
        return listImage;
    }

    public void setListImage(List<String> listImage) {
        this.listImage = listImage;
    }

    public String converStateToString(){
        if(roomState == 1){
            return "Available";
        }
        else if(roomState == 2){
            return "Checked in";
        }
        else if(roomState == 3){
            return "House keeping";
        }
        return "";
    }
}
