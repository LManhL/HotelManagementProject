package com.example.hotelmangementproject.ui.booking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelmangementproject.models.Booking;
import com.example.hotelmangementproject.models.RoomTypeBooking;

public class BookingViewModel extends ViewModel {
    private MutableLiveData<Booking> booking;
    private MutableLiveData<RoomTypeBooking> roomTypeBooking;
    public BookingViewModel() {
        booking = new MutableLiveData<>();
        roomTypeBooking = new MutableLiveData<>();
    }
    public MutableLiveData<Booking> getBooking(){
        return this.booking;
    }
    public void setBooking(Booking value) {
        this.booking.setValue(value);
    }
    public MutableLiveData<RoomTypeBooking> getRoomTypeBooking(){
        return this.roomTypeBooking;
    }
    public void setRoomTypeBooking(RoomTypeBooking value){
        this.roomTypeBooking.setValue(value);
    }
}