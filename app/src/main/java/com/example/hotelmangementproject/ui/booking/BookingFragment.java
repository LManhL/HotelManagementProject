package com.example.hotelmangementproject.ui.booking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.adapters.booking.BookingAdapter;
import com.example.hotelmangementproject.controllers.bookingcontrollers.BookingController;
import com.example.hotelmangementproject.databinding.FragBkMainBinding;
import com.example.hotelmangementproject.interfaces.IClickBookingListener;
import com.example.hotelmangementproject.models.Booking;
import com.example.hotelmangementproject.models.RoomTypeBooking;

import java.util.ArrayList;
import java.util.List;

public class BookingFragment extends Fragment {

    private FragBkMainBinding binding;
    private RecyclerView recyclerView;
    private List<Booking> bookingList;
    BookingAdapter bookingAdapter;
    BookingViewModel model;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(BookingViewModel.class);
        binding = FragBkMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recFragBkMain;
        bookingList = new ArrayList<>();
        bookingAdapter = new BookingAdapter(getActivity(), bookingList, new IClickBookingListener() {
            @Override
            public void onClick(Booking booking) {
                model.setBooking(booking);
                navToBookingDetail();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(bookingAdapter);

        BookingController.getBookingList(bookingList,bookingAdapter);

        binding.floatButtonFragBkMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<RoomTypeBooking> roomTypeBookingList =  new ArrayList<>();
                Booking booking =  new Booking("","","","","","",0.0,0.0,
                                                Booking.IS_NOT_CHECKED_IN_YET,roomTypeBookingList);
                model.setBooking(booking);
                navToBookingDetail();
            }
        });
        return root;
    }
    public void navToBookingDetail(){
        NavController navController = Navigation.findNavController(getView());
        navController.navigate(R.id.action_nav_booking_to_nav_booking_detail);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}