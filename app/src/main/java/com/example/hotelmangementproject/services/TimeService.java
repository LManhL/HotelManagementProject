package com.example.hotelmangementproject.services;

import android.util.Log;

import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.DirtyRoom;
import com.example.hotelmangementproject.models.RoomBill;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeService {
    public static final long HOUR_TO_MILISECONDS = 3600000;
    public static final long MINUTE_TO_MILISECONDS = 60000;

    public static long convertToLocalTime(long systemTime, long offSet){
        return systemTime + HOUR_TO_MILISECONDS * offSet;
    }
    public static String convertMilisecondsToDate(long miliseconds){
        Date res = new Date(miliseconds);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateFormat.format(res);
    }
    public static long getCurrentTime(){
        long currentTimeMilis = System.currentTimeMillis();
        return currentTimeMilis;
    }
    public static long convertToMilliseconds(String timeString){
        String format = "dd/MM/yyyy HH:mm:ss";
        long milliseconds = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(timeString);
            milliseconds = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return milliseconds;
    }
    public static String calStayTime(Bill bill){
        long diff = bill.getSubmitBillTime() - bill.getCreateAt();
        long days = (diff / (1000 * 60 * 60 * 24));
        long hours = (diff / (1000 * 60 * 60)) % 24;
        long minutes = (diff / (1000 * 60)) % 60;
        return String.format("%02d:%02d:%02d",days, hours, minutes);
    }
    public static String calStaytime(RoomBill roomBill){
        long currentTimeMillis = System.currentTimeMillis();
        long diff = currentTimeMillis - roomBill.getCheckinTime();
        long hours = (diff / (TimeService.HOUR_TO_MILISECONDS));
        long minutes = (diff / (TimeService.MINUTE_TO_MILISECONDS)) % 60;
        return String.format("%02d:%02d", hours, minutes);
    }
    public static String calWaitingTime(DirtyRoom room){
        long currentTimeMillis = 0;
        LocalDateTime localDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDateTime = LocalDateTime.now();
            currentTimeMillis = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
        Log.d("TAG",Long.toString(currentTimeMillis));
        long diff = currentTimeMillis - room.getCheckoutTime();
        long hours = (diff / (1000 * 60 * 60));
        long minutes = (diff / (1000 * 60)) % 60;
        return String.format("%02d:%02d", hours, minutes);
    }
}
