package com.example.hotelmangementproject.models;

import java.util.List;

public class Bill {
    public static final int STATE_CHECKED_IN = 1;
    public static final int STATE_CHECK_OUT = 2;
    private String id;
    private int type;
    private int state;
    private long createAt;
    private long submitBillTime;
    private List<RoomBill> listRoomBill;
    private String customerName;
    private String phoneNumber;
    private double otherPrice;
    private double prepayment;
    private double surcharge;
    private double menuCost;
    private double totalRoomCost;
    private double totalPrice;
    private String note;

    public Bill(){

    }

    public Bill(String id, int type, int state, long createAt,
                long submitBillTime, List<RoomBill> listRoomBill, String customerName,
                String phoneNumber, double otherPrice, double prepayment,
                double surcharge, double menuCost, double totalRoomCost,
                double totalPrice, String note) {
        this.id = id;
        this.type = type;
        this.state = state;
        this.createAt = createAt;
        this.submitBillTime = submitBillTime;
        this.listRoomBill = listRoomBill;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.otherPrice = otherPrice;
        this.prepayment = prepayment;
        this.surcharge = surcharge;
        this.menuCost = menuCost;
        this.totalRoomCost = totalRoomCost;
        this.totalPrice = totalPrice;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public long getSubmitBillTime() {
        return submitBillTime;
    }

    public void setSubmitBillTime(long submitBillTime) {
        this.submitBillTime = submitBillTime;
    }

    public List<RoomBill> getListRoomBill() {
        return listRoomBill;
    }

    public void setListRoomBill(List<RoomBill> listRoomBill) {
        this.listRoomBill = listRoomBill;
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

    public double getOtherPrice() {
        return otherPrice;
    }

    public void setOtherPrice(double otherPrice) {
        this.otherPrice = otherPrice;
    }

    public double getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(double prepayment) {
        this.prepayment = prepayment;
    }

    public double getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(double surcharge) {
        this.surcharge = surcharge;
    }

    public double getMenuCost() {
        return menuCost;
    }

    public void setMenuCost(double menuCost) {
        this.menuCost = menuCost;
    }

    public double getTotalRoomCost() {
        return totalRoomCost;
    }

    public void setTotalRoomCost(double totalRoomCost) {
        this.totalRoomCost = totalRoomCost;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double caculateBill(){
        menuCost = 0;
        totalRoomCost = 0;
        totalPrice = 0;

        // Caculate menu cost

        for(RoomBill roomBill : listRoomBill){
            menuCost += roomBill.caculateMenuBill();
        }

        // Caculate total roomcost

        for(RoomBill roomBill : listRoomBill){
            totalRoomCost += roomBill.caculateRoomCost();
        }

        // Caculate total price

        totalPrice = totalRoomCost + otherPrice + surcharge - prepayment;

        return totalPrice;
    }
}
