package com.example.hotelmangementproject.ui.systemmanagement;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelmangementproject.models.CalMoney;

public class SystemManagementViewModel extends ViewModel {

    private MutableLiveData<CalMoney> calMoney;

    public SystemManagementViewModel() {
        calMoney = new MutableLiveData<>();
    }
    public void setCalMoney(CalMoney value){
        this.calMoney.setValue(value);
    }
    public MutableLiveData<CalMoney> getCalMoney(){
        return calMoney;
    }

}