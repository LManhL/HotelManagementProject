package com.example.hotelmangementproject.ui.systemmanagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelmangementproject.models.CalMoney;
import com.example.hotelmangementproject.models.Customer;
import com.example.hotelmangementproject.models.Menu;
import com.example.hotelmangementproject.models.Room;

public class SystemManagementViewModel extends ViewModel {

    private MutableLiveData<CalMoney> calMoney;
    private MutableLiveData<Room> room;
    private MutableLiveData<Menu> menu;
    private MutableLiveData<Customer> customer;

    public SystemManagementViewModel() {
        calMoney = new MutableLiveData<>();
        room = new MutableLiveData<>();
        menu = new MutableLiveData<>();
        customer = new MutableLiveData<>();
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
    public void setMenu(Menu value){
        this.menu.setValue(value);
    }
    public MutableLiveData<Menu> getMenu(){
        return this.menu;
    }
    public MutableLiveData<Customer> getCustomer(){
        return this.customer;
    }
    public void setCustomer(Customer value){
        this.customer.setValue(value);
    }

}