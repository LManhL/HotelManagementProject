package com.example.hotelmangementproject.controllers.bookingcontrollers;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.firebaseServices.BookingService;
import com.example.hotelmangementproject.models.Booking;

import java.util.List;

public class BookingController {
    public static void getBookingList(List<Booking> bookingList, RecyclerView.Adapter adapter){
        BookingService.getBookingList(bookingList,adapter);
    }
}
