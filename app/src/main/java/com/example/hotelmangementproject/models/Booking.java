package com.example.hotelmangementproject.models;

import com.example.hotelmangementproject.services.TimeService;

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
    private List<RoomTypeBooking> roomTypeList;

    public Booking() {

    }

    public Booking(String id, String customerName, String phoneNumber, String checkin, String checkout, String note, Double prepayment, Double bookingPrice, int state, List<RoomTypeBooking> roomTypeList) {
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
    }

    public void setRoomTypeList(List<RoomTypeBooking> roomTypeList) {
        this.roomTypeList = roomTypeList;
    }

    public List<RoomTypeBooking> getRoomTypeList() {
        return roomTypeList;
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
    public double calCulatePrice(){
        bookingPrice = 0.0;
        if(roomTypeList != null){
            for(RoomTypeBooking roomTypeBooking : roomTypeList){
                if(roomTypeBooking.getRoomList() != null){
                    for(Room room : roomTypeBooking.getRoomList()){
                        String checkin = room.getCalMoney().getCheckinTime();
                        String chekout = room.getCalMoney().getCheckoutTime();
                        String[] arrayCheckin = checkin.split(":");
                        String[] arracyCheckout = chekout.split(":");
                        long roundedHourPerDay = Long.parseLong(arrayCheckin[0]) + Long.parseLong(arracyCheckout[0]);

                        String timeIn = getCheckin() + " "+room.getCalMoney().getCheckinTime()+":00";
                        long checkinTime = TimeService.convertToMilliseconds(timeIn);
                        String timeOut = getCheckout() + " "+room.getCalMoney().getCheckoutTime()+":00";
                        long checkoutTime = TimeService.convertToMilliseconds(timeOut);

                        long diff = checkoutTime - checkinTime;
                        long days = diff / (3600000 * roundedHourPerDay);
                        bookingPrice += days * room.getCalMoney().getPrice();
                    }
                }
            }
        }
        return bookingPrice;
    }
}
