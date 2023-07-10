package com.example.hotelmangementproject.ui.booking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.adapters.booking.ChooseRoomAdapter;
import com.example.hotelmangementproject.controllers.bookingcontrollers.BookingController;
import com.example.hotelmangementproject.databinding.FragBkChooseRoomBinding;
import com.example.hotelmangementproject.interfaces.IClickChooseRoomListener;
import com.example.hotelmangementproject.models.Room;

import java.util.ArrayList;
import java.util.List;


public class ChooseRoomFragment extends Fragment {
    private FragBkChooseRoomBinding binding;
    BookingViewModel model;
    ChooseRoomAdapter chooseRoomAdapter;
    RecyclerView recyclerView;
    List<Room> roomList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(BookingViewModel.class);
        binding = FragBkChooseRoomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        roomList =  new ArrayList<>();
        recyclerView = binding.recFragBkChooseRoom;
        chooseRoomAdapter = new ChooseRoomAdapter(getActivity(), roomList, new IClickChooseRoomListener() {
            @Override
            public void clickCheckBox(CompoundButton buttonView, Room room, boolean isChecked) {
                if(isChecked == true){
                    if(model.getRoomTypeBooking().getValue().getRoomList() == null){
                        model.getRoomTypeBooking().getValue().setRoomList(new ArrayList<>());
                        model.getRoomTypeBooking().getValue().getRoomList().add(room);
                        model.getBooking().getValue().calCulatePrice();
                    }
                    else if(model.getRoomTypeBooking().getValue().getCount() == model.getRoomTypeBooking().getValue().getRoomList().size()){
                        buttonView.setChecked(false);
                        Toast.makeText(getActivity(), "Can't have more room", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        model.getRoomTypeBooking().getValue().getRoomList().add(room);
                        model.getBooking().getValue().calCulatePrice();
                    }
                    displayCount();
                }
                else{
                    model.getRoomTypeBooking().getValue().getRoomList().remove(room);
                    model.getBooking().getValue().calCulatePrice();
                    displayCount();
                }
            }

            @Override
            public void onClickRoom(Room room) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(chooseRoomAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        model.getRoomTypeBooking().observe(this,roomTypeBooking -> {
            binding.txtRoomtypeFragBkChooseRoom.setText(roomTypeBooking.getType());
            if(roomTypeBooking.getRoomList() != null){
                binding.txtRemainCountFragBkChooseRoom.setText("Remain: " + (roomTypeBooking.getCount()-roomTypeBooking.getRoomList().size()));
            }
            else{
                binding.txtRemainCountFragBkChooseRoom.setText("Remain: " + roomTypeBooking.getCount());
            }
        });

        BookingController.getRoomListOfType(roomList,chooseRoomAdapter,model.getRoomTypeBooking().getValue());

        return root;
    }
    public void navToBookingDetail(){
        NavController navController = Navigation.findNavController(getView());
        navController.navigate(R.id.action_nav_booking_to_nav_booking_detail);
    }
    public void displayCount(){
        binding.txtRemainCountFragBkChooseRoom.setText("Remain: " + (model.getRoomTypeBooking().getValue().getCount()-model.getRoomTypeBooking().getValue().getRoomList().size()));
    }
    @Override
    public void onResume() {
        super.onResume();
        chooseRoomAdapter.notifyDataSetChanged();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
