package com.example.hotelmangementproject.ui.statistic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatisticViewModel extends ViewModel {
    private final MutableLiveData<String> mtext;


    public StatisticViewModel() {
        mtext = new MutableLiveData<>();
        mtext.setValue("This is statistic fragment");
    }
    public LiveData<String> getText(){
        return this.mtext;
    }
}
