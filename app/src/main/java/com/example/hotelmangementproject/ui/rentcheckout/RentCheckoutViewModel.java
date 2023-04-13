package com.example.hotelmangementproject.ui.rentcheckout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RentCheckoutViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RentCheckoutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is rent and checkout fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}