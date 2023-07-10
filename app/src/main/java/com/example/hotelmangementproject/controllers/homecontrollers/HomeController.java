package com.example.hotelmangementproject.controllers.homecontrollers;

import com.example.hotelmangementproject.firebaseServices.BillService;
import com.example.hotelmangementproject.firebaseServices.BookingService;
import com.example.hotelmangementproject.firebaseServices.RoomService;
import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.Booking;
import com.example.hotelmangementproject.models.Room;

import java.util.List;

public class HomeController {
    public static void getListRoom(List<Room> roomList, RoomService.CallBackGetListRoom callBackGetListRoom){
        RoomService.getListRoom(roomList,callBackGetListRoom);
    }
    public static void getListBooking(List<Booking> bookingList, BookingService.CallBackGetBookingList callBackGetBookingList){
        BookingService.getBookingList(bookingList,callBackGetBookingList);
    }
    public static void getBillList(List<Bill> billList, BillService.CallBackBillList callBackBillList){
        BillService.getBillList(billList,callBackBillList);
    }
    public static int countTodayCheckin(List<Bill> billList){
        if(billList != null){
            int count = 0;
            for(Bill bill : billList){
                if(bill.getListRoomBill() != null){
                    count += bill.getListRoomBill().size();
                }
            }
            return count;
        }
        return 0;
    }
    public static int countAvailableRoom(List<Room> roomList){
        if(roomList != null){
            int count = 0;
            for(Room room : roomList){
                if(room.getRoomState() == Room.STATE_AVAILABLE) count++;
            }
            return count;
        }
        return 0;
    }
    public static int countCheckinRoom(List<Room> roomList){
        if(roomList != null){
            int count = 0;
            for(Room room : roomList){
                if(room.getRoomState() == Room.STATE_CHECKEDIN) count++;
            }
            return count;
        }
        return 0;
    }
    public static int countHouseKeppingRoom(List<Room> roomList){
        if(roomList != null){
            int count = 0;
            for(Room room : roomList){
                if(room.getRoomState() == Room.STATE_HOUSEKEEPING) count++;
            }
            return count;
        }
        return 0;
    }
    public static int countBookingRoom(List<Booking> bookingList){
        if(bookingList != null){
            int count = 0;
            for(Booking booking : bookingList){
                if(booking.getState() == Booking.IS_NOT_CHECKED_IN_YET) count++;
            }
            return count;
        }
        return 0;
    }
}
