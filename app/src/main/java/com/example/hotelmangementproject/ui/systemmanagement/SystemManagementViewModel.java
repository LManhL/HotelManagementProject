package com.example.hotelmangementproject.ui.systemmanagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SystemManagementViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public SystemManagementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is system management fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}