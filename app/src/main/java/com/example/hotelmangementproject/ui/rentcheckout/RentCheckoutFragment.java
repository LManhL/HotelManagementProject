package com.example.hotelmangementproject.ui.rentcheckout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hotelmangementproject.databinding.FragmentRentcheckoutBinding;

public class RentCheckoutFragment extends Fragment {

    private FragmentRentcheckoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RentCheckoutViewModel rentCheckoutViewModel =
                new ViewModelProvider(this).get(RentCheckoutViewModel.class);

        binding = FragmentRentcheckoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRentCheckout;
        rentCheckoutViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}