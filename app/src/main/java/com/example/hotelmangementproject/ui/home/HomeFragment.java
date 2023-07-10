package com.example.hotelmangementproject.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hotelmangementproject.controllers.homecontrollers.HomeController;
import com.example.hotelmangementproject.databinding.FragHmMainBinding;
import com.example.hotelmangementproject.firebaseServices.BillService;
import com.example.hotelmangementproject.firebaseServices.BookingService;
import com.example.hotelmangementproject.firebaseServices.RoomService;
import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.Booking;
import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.services.TimeService;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

            private FragHmMainBinding binding;
            List<Room> roomList;
            List<Booking> bookingList;
            List<Bill> billList;

            public View onCreateView(@NonNull LayoutInflater inflater,
                                     ViewGroup container, Bundle savedInstanceState) {
                HomeViewModel homeViewModel =
                        new ViewModelProvider(getActivity()).get(HomeViewModel.class);
                binding = FragHmMainBinding.inflate(inflater, container, false);
                View root = binding.getRoot();
                roomList = new ArrayList<>();
                bookingList = new ArrayList<>();
                billList = new ArrayList<>();

                homeViewModel.getAvailable().observe(this, available ->{
                    binding.txtAvailableFragHmMain.setText(String.valueOf(available));
                });
                homeViewModel.getCheckin().observe(this, checkin ->{
                    binding.txtCheckinFragHmMain.setText(String.valueOf(checkin));
                });
                homeViewModel.getHousekeeping().observe(this,housekeeping ->{
                    binding.txtHousekeepingFragHmMain.setText(String.valueOf(housekeeping));
                });
                homeViewModel.getBooking().observe(this, booking ->{
                    binding.txtBookingFragHmMain.setText(String.valueOf(booking));
                });
                homeViewModel.getTodayCheckin().observe(this, todayCheckIn ->{
                    binding.txtTodayCheckinFragHmMain.setText(String.valueOf(todayCheckIn));
                });
                HomeController.getListRoom(roomList, new RoomService.CallBackGetListRoom() {
                    @Override
                    public void callBack() {
                        int available = HomeController.countAvailableRoom(roomList);
                        int checkin = HomeController.countCheckinRoom(roomList);
                        int housekeeping = HomeController.countHouseKeppingRoom(roomList);
                        homeViewModel.setAvailable(available);
                        homeViewModel.setCheckin(checkin);
                        homeViewModel.setHousekeeping(housekeeping);
                    }
                });
                HomeController.getListBooking(bookingList, new BookingService.CallBackGetBookingList() {
                    @Override
                    public void callBack() {
                        int booking = HomeController.countBookingRoom(bookingList);
                        homeViewModel.setBooking(booking);
                    }
                });
                HomeController.getBillList(billList, new BillService.CallBackBillList() {
                    @Override
                    public void callBack() {
                        int todayCheckIn = HomeController.countTodayCheckin(billList);
                        homeViewModel.setTodayCheckin(todayCheckIn);
                    }
                });
             return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}