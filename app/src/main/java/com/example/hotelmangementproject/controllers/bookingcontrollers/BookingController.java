package com.example.hotelmangementproject.controllers.bookingcontrollers;

import android.icu.util.Measure;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.firebaseServices.BillService;
import com.example.hotelmangementproject.firebaseServices.BookingService;
import com.example.hotelmangementproject.firebaseServices.RoomService;
import com.example.hotelmangementproject.firebaseServices.RoomTypeService;
import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.Booking;
import com.example.hotelmangementproject.models.Menu;
import com.example.hotelmangementproject.models.MenuBill;
import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.models.RoomBill;
import com.example.hotelmangementproject.models.RoomTypeBooking;
import com.example.hotelmangementproject.services.TimeService;

import java.util.ArrayList;
import java.util.List;

public class BookingController {
    public static void getBookingList(List<Booking> bookingList, RecyclerView.Adapter adapter){
        BookingService.getBookingList(bookingList,adapter);
    }
    public static void updateBooking(Booking booking){
        BookingService.updateBooking(booking);
    }
    public static void getRoomTypeList(List<String> roomTypeList, BaseAdapter adapter){
        RoomTypeService.getListRoomType(roomTypeList,adapter);
    }
    public static void getRoomListOfType(List<Room> roomList, RecyclerView.Adapter adapter, RoomTypeBooking roomTypeBooking){
        RoomService.getRoomListOfType(roomList,adapter,roomTypeBooking);
    }
    public static void createBooking(Booking booking){
        BookingService.createBooking(booking);
    }
    public static void checkin(Booking booking){
        long createAt = System.currentTimeMillis();
        List<RoomBill> roomBillList = new ArrayList<>();
        for(RoomTypeBooking roomTypeBooking : booking.getRoomTypeList()){
            for(Room room : roomTypeBooking.getRoomList()){
                // format dd/MM/yyyy HH:mm:ss
                String timeIn = booking.getCheckin() + " "+room.getCalMoney().getCheckinTime()+":00";
                long checkinTime = TimeService.convertToMilliseconds(timeIn);
                String timeOut = booking.getCheckout() + " "+room.getCalMoney().getCheckoutTime()+":00";
                long checkoutTime = TimeService.convertToMilliseconds(timeOut);

                long roundedHourPerDay = Long.parseLong(room.getCalMoney().getCheckinTime().substring(0,2)) + Long.parseLong(room.getCalMoney().getCheckoutTime().substring(0,2));
                long diff = checkoutTime -checkinTime;
                long days = diff / (3600000 * roundedHourPerDay);
                double roomCost = days * room.getCalMoney().getPrice();
                double totalRoomcost = roomCost;
                double totalPrice = roomCost;
                List<MenuBill> menuList =  new ArrayList<>();


                RoomBill roomBill = new RoomBill(room.getId(), room.getDescription(),room.getName(),
                        room.getRoomTypes(), room.getRoomState(),room.getCalMoney(),
                        room.getListImage(),checkinTime,checkoutTime,0,0, roomCost, "",RoomBill.TYPE_RENT_BY_DAY,
                        Room.STATE_CHECKEDIN,menuList,Integer.toString(roomBillList.size()));
                roomBillList.add(roomBill);
            }
        }
        Bill bill = new Bill(booking.getId(), RoomBill.TYPE_RENT_BY_DAY,Bill.STATE_CHECKED_IN,
                            createAt,0,roomBillList,booking.getCustomerName(),booking.getPhoneNumber(),
                            0,booking.getPrepayment(),0,0,booking.getBookingPrice(),booking.getBookingPrice(),booking.getNote());
        BillService.createBillFromBooking(bill);
        booking.setState(1);
        BookingService.updateBooking(booking);
        for(RoomTypeBooking roomTypeBooking : booking.getRoomTypeList()){
            for(Room room : roomTypeBooking.getRoomList()){
                RoomService.changeRoomState(room, Room.STATE_CHECKEDIN);
            }
        }
    }
}
