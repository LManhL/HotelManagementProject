package com.example.hotelmangementproject.models;

import java.util.List;
import java.util.Map;

public class Booking {

    public final static int IS_NOT_CHECKED_IN_YET = 0;
    public final static int CHECKED_IN = 1;
    public final static int CHECKED_OUT = 2;
    private String id;
    private String customerName;
    private String phoneNumber;
    private String checkin;
    private String checkout;
    private String note;
    private Double prepayment;
    private Double bookingPrice;
    private int state;
    private Map<String,Integer> roomTypeList;
    private List<Room> roomList;

    public Booking() {

    }

    public Booking(String id, String customerName, String phoneNumber, String checkin, String checkout, String note, Double prepayment, Double bookingPrice, int state, Map<String, Integer> roomTypeList, List<Room> roomList) {
        this.id = id;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.checkin = checkin;
        this.checkout = checkout;
        this.note = note;
        this.prepayment = prepayment;
        this.bookingPrice = bookingPrice;
        this.state = state;
        this.roomTypeList = roomTypeList;
        this.roomList = roomList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(Double prepayment) {
        this.prepayment = prepayment;
    }

    public Double getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(Double bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Map<String, Integer> getRoomTypeList() {
        return roomTypeList;
    }

    public void setRoomTypeList(Map<String, Integer> roomTypeList) {
        this.roomTypeList = roomTypeList;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }
    public String getStringState(){
        if(getState() == IS_NOT_CHECKED_IN_YET){
            return "Customer is not checked in yet";
        }
        else if(getState() == CHECKED_IN){
            return "Customer has checked in";
        }
        else if(getState() == CHECKED_OUT){
            return "Customer has checked out";
        }
        return "";
    }
}
