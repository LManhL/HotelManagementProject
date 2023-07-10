package com.example.hotelmangementproject.models;

import com.example.hotelmangementproject.services.TimeService;

import java.util.ArrayList;
import java.util.List;

public class RoomBill extends Room{
    public static final int TYPE_RENT_BY_DAY = 1;
    public static final int TYPE_RENT_BY_HOUR = 2;
    private long checkinTime;
    private long checkoutTime;
    private int extraAdult;
    private int extraChild;
    private double roomCost;
    private String note;
    private int type;
    private int roomState;
    private List<MenuBill> listMenuBill;
    private String idRoomBill;

    public RoomBill() {
    }

    public RoomBill(RoomBill roomBill){
        super(roomBill.getId(), roomBill.getDescription(), roomBill.getName(), roomBill.getRoomTypes(), roomBill.getRoomState(),roomBill.getCalMoney(),roomBill.getListImage());
        this.checkinTime = roomBill.getCheckinTime();
        this.checkoutTime = roomBill.getCheckoutTime();
        this.type = roomBill.getType();
        this.roomState = roomBill.getRoomState();
        // CHANGE
        this.note = roomBill.getNote();
        /*  */
        this.listMenuBill = roomBill.getListMenuBill();
        this.extraAdult = roomBill.getExtraAdult();
        this.extraChild = roomBill.getExtraChild();
        this.roomCost = roomBill.getRoomCost();
        this.idRoomBill = roomBill.getIdRoomBill();
    }

    public RoomBill(RoomBill roomBill, int extraChild, int extraAdult,String note,List<MenuBill> listMenuBill){
        super(roomBill.getId(), roomBill.getDescription(), roomBill.getName(), roomBill.getRoomTypes(), roomBill.getRoomState(),roomBill.getCalMoney(),roomBill.getListImage());
        this.checkinTime = roomBill.getCheckinTime();
        this.checkoutTime = roomBill.getCheckoutTime();
        this.type = roomBill.getType();
        this.roomState = roomBill.getRoomState();
        // CHANGE
        this.note = note;
        /*  */
        this.listMenuBill = listMenuBill;
        this.extraAdult = extraAdult;
        this.extraChild = extraChild;
        this.roomCost = roomBill.getRoomCost();
        this.idRoomBill = roomBill.getIdRoomBill();
    }

    public RoomBill(String id, String description, String name, String roomTypes,
                    int state, CalMoney calMoney, List<String> listImage, long checkinTime, long checkoutTime,
                    int extraAdult, int extraChild, double roomCost,
                    String note, int type,int roomState,List<MenuBill> listMenuBill,String idRoomBill) {
        super(id, description, name, roomTypes, state, calMoney,listImage);
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.extraAdult = extraAdult;
        this.extraChild = extraChild;
        this.roomCost = roomCost;
        this.note = note;
        this.type = type;
        this.roomState = roomState;
        this.listMenuBill = listMenuBill;
        this.idRoomBill = idRoomBill;
    }

    public String getIdRoomBill() {
        return idRoomBill;
    }

    public void setIdRoomBill(String idRoomBill) {
        this.idRoomBill = idRoomBill;
    }

    public List<MenuBill> getListMenuBill() {
        return listMenuBill;
    }

    public void setListMenuBill(List<MenuBill> listMenuBill) {
        this.listMenuBill = listMenuBill;
    }

    public long getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(long checkinTime) {
        this.checkinTime = checkinTime;
    }

    public long getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(long checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public int getExtraAdult() {
        return extraAdult;
    }

    public void setExtraAdult(int extraAdult) {
        this.extraAdult = extraAdult;
    }

    public int getExtraChild() {
        return extraChild;
    }

    public void setExtraChild(int extraChild) {
        this.extraChild = extraChild;
    }

    public double getRoomCost() {
        return roomCost;
    }

    public void setRoomCost(double roomCost) {
        this.roomCost = roomCost;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double caculateRoomCost(){
        roomCost = 0;
        if(type == TYPE_RENT_BY_DAY){
            long roundedHourPerDay = Long.parseLong(getCalMoney().getCheckinTime().substring(0,2)) + Long.parseLong(getCalMoney().getCheckoutTime().substring(0,2));
            long diff = checkoutTime -checkinTime;
            long days = diff / (3600000 * roundedHourPerDay);
            roomCost = days * getCalMoney().getPrice();
        }
        else if(type == TYPE_RENT_BY_HOUR){
            checkoutTime = TimeService.getCurrentTime();
            String staytime = TimeService.calStaytime(this);
            /* HH:mm */
            String[] stringArrayList = staytime.split(":");
            int hour = Integer.parseInt(stringArrayList[0]);
            int minutes = Integer.parseInt(stringArrayList[1]);

            if(minutes >= getCalMoney().getRoundedMinutesToOneHour()) hour++;
            long afterFirstBlock = hour - getCalMoney().getFirstBlock();

            roomCost = getCalMoney().getFirstBlockPrice();
            if(afterFirstBlock > 0){
                roomCost += getCalMoney().getAfterFirstBlockPrice() * afterFirstBlock;
            }

        }
        roomCost += caculateMenuBill();
        roomCost += extraAdult * getCalMoney().getExtraAdultPrice() + extraChild*getCalMoney().getExtraChildPrice();
        return roomCost;
    }
    public double caculateMenuBill(){
        if(getListMenuBill() == null) return 0;
        double res = 0;
        for(MenuBill item : getListMenuBill()){
            res += item.getPrice() * item.getCount();
        }
        return res;
    }
    @Override
    public int getRoomState() {
        return roomState;
    }

    @Override
    public void setRoomState(int roomState) {
        this.roomState = roomState;
    }

    @Override
    public String toString() {
        return "RoomBill{" +
                "checkinTime=" + checkinTime +
                ", checkoutTime=" + checkoutTime +
                ", extraAdult=" + extraAdult +
                ", extraChild=" + extraChild +
                ", roomCost=" + roomCost +
                ", note='" + note + '\'' +
                ", type=" + type +
                '}';
    }
}
