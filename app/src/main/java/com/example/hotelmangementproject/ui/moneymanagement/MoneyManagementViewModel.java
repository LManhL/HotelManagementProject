package com.example.hotelmangementproject.ui.moneymanagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MoneyManagementViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MoneyManagementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is money management fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}