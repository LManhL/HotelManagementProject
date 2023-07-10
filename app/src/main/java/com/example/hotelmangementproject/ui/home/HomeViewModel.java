package com.example.hotelmangementproject.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Integer> available;
    private MutableLiveData<Integer> checkin;
    private MutableLiveData<Integer> todayCheckin;
    private MutableLiveData<Integer> booking;
    private MutableLiveData<Integer> housekeeping;

    public HomeViewModel() {
        available = new MutableLiveData<>();
        checkin = new MutableLiveData<>();
        todayCheckin = new MutableLiveData<>();
        booking = new MutableLiveData<>();
        housekeeping = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getHousekeeping(){
        return housekeeping;
    }
    public void setHousekeeping(Integer housekeeping){
        this.housekeeping.setValue(housekeeping);
    }
    public MutableLiveData<Integer> getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available.setValue(available);
    }

    public MutableLiveData<Integer> getCheckin() {
        return checkin;
    }

    public void setCheckin(Integer checkin) {
        this.checkin.setValue(checkin);
    }

    public MutableLiveData<Integer> getTodayCheckin() {
        return todayCheckin;
    }

    public void setTodayCheckin(Integer todayCheckin) {
        this.todayCheckin.setValue(todayCheckin);
    }

    public MutableLiveData<Integer> getBooking() {
        return booking;
    }

    public void setBooking(Integer booking) {
        this.booking.setValue(booking);
    }
}