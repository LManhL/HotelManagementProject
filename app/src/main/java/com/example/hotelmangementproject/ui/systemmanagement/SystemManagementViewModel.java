package com.example.hotelmangementproject.ui.systemmanagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelmangementproject.models.CalMoney;
import com.example.hotelmangementproject.models.Room;

public class SystemManagementViewModel extends ViewModel {

    private MutableLiveData<CalMoney> calMoney;
    private MutableLiveData<Room> room;

    public SystemManagementViewModel() {
        calMoney = new MutableLiveData<>();
        room = new MutableLiveData<>();
    }
    public void setCalMoney(CalMoney value){
        this.calMoney.setValue(value);
    }
    public MutableLiveData<CalMoney> getCalMoney(){
        return calMoney;
    }
    public void setRoom(Room value){
        this.room.setValue(value);
    }
    public LiveData<Room> getRoom(){
        return room;
    }

}