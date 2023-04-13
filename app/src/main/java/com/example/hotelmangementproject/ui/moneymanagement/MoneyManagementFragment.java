package com.example.hotelmangementproject.ui.moneymanagement;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.databinding.FragmentHomeBinding;
import com.example.hotelmangementproject.databinding.FragmentMoneyManagementBinding;
import com.example.hotelmangementproject.ui.home.HomeViewModel;

public class MoneyManagementFragment extends Fragment {

    private FragmentMoneyManagementBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MoneyManagementViewModel moneyManagementViewModel =
                new ViewModelProvider(this).get(MoneyManagementViewModel.class);

        binding = FragmentMoneyManagementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMoneyManagement;
        moneyManagementViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}