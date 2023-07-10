package com.example.hotelmangementproject.controllers.roomservicescontroller;

import android.content.Context;
import android.widget.Toast;
import com.example.hotelmangementproject.services.TimeService;
import com.example.hotelmangementproject.adapters.roomservicesAdapter.RoomAvailableAdapter;
import com.example.hotelmangementproject.firebaseServices.BillService;
import com.example.hotelmangementproject.firebaseServices.RoomService;
import com.example.hotelmangementproject.models.MenuBill;
import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.models.RoomBill;

import java.util.ArrayList;
import java.util.List;

public class AvailableController {
    public static final int TYPE_RENT_BY_DAY = 1;
    public static final int TYPE_RENT_BY_HOUR = 2;

    public static void checkinWithoutBooking(Context context, Room room, int type, String customerName, String phoneNumber, String timeIn, String timeOut){

        // CODE BELOW ->  CREATE DATA FOR UPLOAD TO FIREBASE
        int state = 1;
        long createAt = System.currentTimeMillis();
        long checkinTime = 0;
        long checkoutTime = 0;
        double roomCost = 0;
        String note = "";
        double totalRoomcost = 0;
        double totalPrice = 0;

        if(type == TYPE_RENT_BY_DAY){
            String timeZone = "Asia/Saigon";
            // format dd/MM/yyyy HH:mm:ss
            String checkinTime1 = room.getCalMoney().getCheckinTime();
            timeIn+=" "+checkinTime1+":00";
            checkinTime = TimeService.convertToMilliseconds(timeIn);

            String checkoutTime1 = room.getCalMoney().getCheckoutTime();
            timeOut+=" "+checkoutTime1+":00";
            checkoutTime = TimeService.convertToMilliseconds(timeOut);

            // caculate roomcost
            long roundedHourPerDay = Long.parseLong(room.getCalMoney().getCheckinTime().substring(0,2)) + Long.parseLong(room.getCalMoney().getCheckoutTime().substring(0,2));
            long diff = checkoutTime -checkinTime;
            long days = diff / (3600000 * roundedHourPerDay);
            roomCost = days * room.getCalMoney().getPrice();
            totalRoomcost = roomCost;
            totalPrice = roomCost;
        }

        else if(type == TYPE_RENT_BY_HOUR){
            checkinTime = System.currentTimeMillis();
            checkoutTime = 0;
            roomCost = room.getCalMoney().getFirstBlockPrice();
            totalPrice = roomCost;
            totalRoomcost = roomCost;
        }

        List<MenuBill> menuList = new ArrayList<MenuBill>();

        // create roombill

        int index = 0;
        RoomBill roomBill = new RoomBill(room.getId(), room.getDescription(),room.getName(),
                room.getRoomTypes(), room.getRoomState(),room.getCalMoney(),
                room.getListImage(),checkinTime,checkoutTime,0,0, roomCost, note,type,Room.STATE_CHECKEDIN,menuList,Integer.toString(index));
        List<RoomBill> roomBillList = new ArrayList<RoomBill>();
        roomBillList.add(roomBill);


        // create bill
        BillService.createBill(type,state, createAt,checkoutTime,roomBillList,
                customerName,phoneNumber,0,0,0,0,
                totalRoomcost,totalPrice, note);


        // change room state

        RoomService.changeRoomState(room, Room.STATE_CHECKEDIN);

        //toast
        Toast.makeText(context, "Booking successfully", Toast.LENGTH_SHORT).show();
    }

    public static void getListRoomFromFireBase(List<Room> listRoom, RoomAvailableAdapter roomAvailableAdapter){
        RoomService.getListRoom(listRoom,roomAvailableAdapter);
    }
}
