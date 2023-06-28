package com.example.hotelmangementproject.ui.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.adapters.booking.BookingAdapter;
import com.example.hotelmangementproject.controllers.bookingcontrollers.BookingController;
import com.example.hotelmangementproject.databinding.FragBkMainBinding;
import com.example.hotelmangementproject.interfaces.IClickBookingListener;
import com.example.hotelmangementproject.models.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingFragment extends Fragment {

    private FragBkMainBinding binding;
    private RecyclerView recyclerView;
    private List<Booking> bookingList;
    BookingAdapter bookingAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BookingViewModel bookingViewModel =
                new ViewModelProvider(this).get(BookingViewModel.class);
        binding = FragBkMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recFragBkMain;
        bookingList = new ArrayList<>();
        bookingAdapter = new BookingAdapter(getActivity(), bookingList, new IClickBookingListener() {
            @Override
            public void onClick(Booking booking) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(bookingAdapter);

        BookingController.getBookingList(bookingList,bookingAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}