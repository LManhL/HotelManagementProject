package com.example.hotelmangementproject.models;

public class CalMoney {
    private String id;
    private String checkinTime;
    private String checkoutTime;
    private Double extraAdultPrice;
    private Double extraChildPrice;
    private Double price;
    private Double overtimeSurcharge;
    private Integer roundedMinutesToOneHour;
    private Double afterFirstBlockPrice;
    private Integer firstBlock;
    private Double firstBlockPrice;
    private String type;

    public CalMoney(){
        this.id = "";
        this.checkinTime = "";
        this.checkoutTime = "";
        this.extraAdultPrice = Double.valueOf(0);
        this.extraChildPrice = Double.valueOf(0);
        this.price = Double.valueOf(0);
        this.overtimeSurcharge = Double.valueOf(0);
        this.roundedMinutesToOneHour = 0;
        this.afterFirstBlockPrice = Double.valueOf(0);
        this.firstBlock = 0;
        this.firstBlockPrice = Double.valueOf(0);
        this.type = "";
    }

    public CalMoney(String id, String checkinTime, String checkoutTime, Double extraAdultPrice,
                    Double extraChildPrice, Double price, Double overtimeSurcharge, Integer roundedMinutesToOneHour,
                    Double afterFirstBlockPrice, Integer firstBlock, Double firstBlockPrice, String type) {
        this.id = id;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.extraAdultPrice = extraAdultPrice;
        this.extraChildPrice = extraChildPrice;
        this.price = price;
        this.overtimeSurcharge = overtimeSurcharge;
        this.roundedMinutesToOneHour = roundedMinutesToOneHour;
        this.afterFirstBlockPrice = afterFirstBlockPrice;
        this.firstBlock = firstBlock;
        this.firstBlockPrice = firstBlockPrice;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(String checkinTime) {
        this.checkinTime = checkinTime;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public Double getExtraAdultPrice() {
        return extraAdultPrice;
    }

    public void setExtraAdultPrice(Double extraAdultPrice) {
        this.extraAdultPrice = extraAdultPrice;
    }

    public Double getExtraChildPrice() {
        return extraChildPrice;
    }

    public void setExtraChildPrice(Double extraChildPrice) {
        this.extraChildPrice = extraChildPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOvertimeSurcharge() {
        return overtimeSurcharge;
    }

    public void setOvertimeSurcharge(Double overtimeSurcharge) {
        this.overtimeSurcharge = overtimeSurcharge;
    }

    public Integer getRoundedMinutesToOneHour() {
        return roundedMinutesToOneHour;
    }

    public void setRoundedMinutesToOneHour(Integer roundedMinutesToOneHour) {
        this.roundedMinutesToOneHour = roundedMinutesToOneHour;
    }

    public Double getAfterFirstBlockPrice() {
        return afterFirstBlockPrice;
    }

    public void setAfterFirstBlockPrice(Double afterFirstBlockPrice) {
        this.afterFirstBlockPrice = afterFirstBlockPrice;
    }

    public Integer getFirstBlock() {
        return firstBlock;
    }

    public void setFirstBlock(Integer firstBlock) {
        this.firstBlock = firstBlock;
    }

    public Double getFirstBlockPrice() {
        return firstBlockPrice;
    }

    public void setFirstBlockPrice(Double firstBlockPrice) {
        this.firstBlockPrice = firstBlockPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
