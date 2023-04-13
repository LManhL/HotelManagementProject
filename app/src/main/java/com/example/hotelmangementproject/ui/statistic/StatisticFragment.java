package com.example.hotelmangementproject.ui.statistic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.databinding.FragmentStatisticBinding;


public class StatisticFragment extends Fragment {

    private FragmentStatisticBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StatisticViewModel statisticViewModel = new ViewModelProvider(this).get(StatisticViewModel.class);
        binding = FragmentStatisticBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        final TextView textView = binding.textStatistic;
        statisticViewModel.getText().observe(getViewLifecycleOwner(), textView ::setText);
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}