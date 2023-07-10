package com.example.hotelmangementproject.ui.booking;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.adapters.booking.RoomTypeListAdapter;
import com.example.hotelmangementproject.controllers.bookingcontrollers.BookingController;
import com.example.hotelmangementproject.databinding.FragBkBookingDetailBinding;
import com.example.hotelmangementproject.interfaces.IClickItemRoomBookingListener;
import com.example.hotelmangementproject.interfaces.IClickRoomTypeListListener;
import com.example.hotelmangementproject.models.Booking;
import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.models.RoomTypeBooking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BookingDetailFragment extends Fragment {
    final Calendar myCalendar= Calendar.getInstance();
    FragBkBookingDetailBinding binding;
    BookingViewModel model;
    List<RoomTypeBooking> roomTypeBookingList;
    RoomTypeListAdapter roomTypeListAdapter;
    RecyclerView recyclerView;
    private ArrayAdapter<String> adapter;
    private List<String> roomTypeList;
    private EditText editTextDate;
    boolean isVisible = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(BookingViewModel.class);
        binding = FragBkBookingDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(model.getBooking().getValue().getState() != Booking.IS_NOT_CHECKED_IN_YET){
            binding.buttonCheckinFragBkBookingDetail.setVisibility(View.GONE);
            binding.floatButtonFragBkBookingDetail.setVisibility(View.GONE);
        }

        if(model.getBooking().getValue().getId().isEmpty()){
            binding.buttonCheckinFragBkBookingDetail.setVisibility(View.GONE);
        }

        roomTypeBookingList = new ArrayList<>();
        recyclerView = binding.recRoomtypeListFragBkBookingDetail;
        roomTypeList =  new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, roomTypeList);
        adapter.setNotifyOnChange(true);
        roomTypeListAdapter =  new RoomTypeListAdapter(getActivity(), roomTypeBookingList, new IClickRoomTypeListListener() {
            @Override
            public void onClickAdd(RoomTypeBooking roomTypeBooking) {
                roomTypeBooking.setCount(roomTypeBooking.getCount() + 1);
                roomTypeListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onClickMinus(RoomTypeBooking roomTypeBooking) {
                roomTypeBooking.setCount(Math.max(roomTypeBooking.getCount() - 1, 0));
                if(roomTypeBooking.getCount() == 0){
                    roomTypeBookingList.remove(roomTypeBooking);
                    model.getBooking().getValue().setRoomTypeList(roomTypeBookingList);
                }
                roomTypeListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onClickAddRoom(RoomTypeBooking roomTypeBooking) {
                model.setRoomTypeBooking(roomTypeBooking);
                navToChooseRoomFragment();
            }
        }, new IClickItemRoomBookingListener() {
            @Override
            public void onClick(Room room) {

            }

            @Override
            public void onClickDelete(RoomTypeBooking roomTypeBooking, Room room) {
                roomTypeBooking.getRoomList().remove(room);
                model.getBooking().getValue().setRoomTypeList(roomTypeBookingList);
                binding.edtBookingpriceFragBkBookingDetail.setText(String.valueOf(model.getBooking().getValue().calCulatePrice()));
                roomTypeListAdapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(roomTypeListAdapter);
        binding.autoCompleteFragBkBookingDetail.setAdapter(adapter);

        model.getBooking().observe(this, booking -> {
            binding.edtNameFragBkBookingDetail.setText(booking.getCustomerName());
            binding.edtPhonenumberFragBkBookingDetail.setText(booking.getPhoneNumber());
            binding.edtCheckinFragBkBookingDetail.setText(booking.getCheckin());
            binding.edtCheckoutFragBkBookingDetail.setText(booking.getCheckout());
            binding.edtPrepaymentFragBkBookingDetail.setText(booking.getPrepayment()+ "");
            binding.edtBookingpriceFragBkBookingDetail.setText(booking.getBookingPrice()+ "");
            binding.edtNoteFragBkBookingDetail.setText(booking.getNote());
            if(roomTypeBookingList != null) {
                roomTypeBookingList.clear();
            }
            roomTypeBookingList.addAll(booking.getRoomTypeList());
            roomTypeListAdapter.notifyDataSetChanged();
        });

        binding.floatButtonFragBkBookingDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidate()){
                    if(!model.getBooking().getValue().getId().isEmpty()){
                        saveChange();
                        Toast.makeText(getActivity(), "Update successfully", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        createBooking();
                        Toast.makeText(getActivity(), "Create successfully", Toast.LENGTH_SHORT).show();
                    }
                    navToBookingFragment();
                }
            }
        });
        binding.linearLayoutCoverAddRoomtypeFragBkBookingDetail.setVisibility(View.GONE);
        binding.btnAddRoomtypeFragBkBookingDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVisible == false){
                    binding.linearLayoutCoverAddRoomtypeFragBkBookingDetail.setVisibility(View.VISIBLE);
                    isVisible = true;
                }
                else{
                    binding.linearLayoutCoverAddRoomtypeFragBkBookingDetail.setVisibility(View.GONE);
                    isVisible = false;
                }
            }
        });
        binding.autoCompleteFragBkBookingDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.autoCompleteFragBkBookingDetail.showDropDown();
            }
        });
        binding.btnConfirmAddRoomtypeFragBkItemRoomtypeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.autoCompleteFragBkBookingDetail.getText().toString().isEmpty()){
                    if(checkExist()){
                        List<Room> roomList = new ArrayList<>();
                        RoomTypeBooking roomTypeBooking = new RoomTypeBooking(String.valueOf(roomTypeBookingList.size()), 1,
                                binding.autoCompleteFragBkBookingDetail.getText().toString(), roomList);
                        roomTypeBookingList.add(roomTypeBooking);
                        model.getBooking().getValue().setRoomTypeList(roomTypeBookingList);
                        roomTypeListAdapter.notifyDataSetChanged();
                        binding.linearLayoutCoverAddRoomtypeFragBkBookingDetail.setVisibility(View.GONE);
                        isVisible = false;
                    }
                    else{
                        Toast.makeText(getActivity(), "Already exist", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Choose a type", Toast.LENGTH_SHORT).show();
                }
            }
        });
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        binding.edtCheckinFragBkBookingDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                editTextDate = binding.edtCheckinFragBkBookingDetail;
            }
        });
        binding.edtCheckoutFragBkBookingDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                editTextDate = binding.edtCheckoutFragBkBookingDetail;
            }
        });
        binding.buttonCheckinFragBkBookingDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCompatible()){
                    checkin();
                    Toast.makeText(getActivity(), "Check in successfully", Toast.LENGTH_SHORT).show();
                    navToBookingFragment();
                }
                else Toast.makeText(getActivity(), "Please choose enough rooms for any roomtype", Toast.LENGTH_SHORT).show();
            }
        });
        BookingController.getRoomTypeList(roomTypeList,adapter);
        return root;
    }
    public boolean checkCompatible(){
        if(checkValidate()){
            if(roomTypeBookingList != null){
                for(RoomTypeBooking item : roomTypeBookingList){
                    if(item.getRoomList() == null) return false;
                    if(item.getCount() != item.getRoomList().size()){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        editTextDate.setText(dateFormat.format(myCalendar.getTime()));
    }
    public boolean checkExist(){
        if(roomTypeBookingList == null) return true;
        for(RoomTypeBooking roomTypeBooking : roomTypeBookingList){
            if(binding.autoCompleteFragBkBookingDetail.getText().toString().equals(roomTypeBooking.getType())){
                return false;
            }
        }
        return true;
    }
    public void navToBookingFragment(){
        NavController navController = Navigation.findNavController(getView());
        navController.popBackStack();
    }
    public void navToChooseRoomFragment(){
        NavController navController = Navigation.findNavController(getView());
        navController.navigate(R.id.action_nav_booking_detail_to_nav_choose_room);
    }
    public boolean checkValidate(){
        if(binding.edtNameFragBkBookingDetail.getText().toString().isEmpty()){
            binding.edtNameFragBkBookingDetail.setError("Required");
            return false;
        }
        if(binding.edtPhonenumberFragBkBookingDetail.getText().toString().isEmpty()){
            binding.edtPhonenumberFragBkBookingDetail.setError("Required");
            return false;
        }
        if(binding.edtCheckinFragBkBookingDetail.getText().toString().isEmpty()){
            binding.edtCheckinFragBkBookingDetail.setError("Required");
            return false;
        }
        if(binding.edtCheckoutFragBkBookingDetail.getText().toString().isEmpty()){
            binding.edtCheckoutFragBkBookingDetail.setError("Required");
            return false;
        }
        if(binding.edtPrepaymentFragBkBookingDetail.getText().toString().isEmpty()){
            binding.edtPrepaymentFragBkBookingDetail.setError("Required");
            return false;
        }
        if(binding.edtBookingpriceFragBkBookingDetail.getText().toString().isEmpty()){
            binding.edtBookingpriceFragBkBookingDetail.setError("Required");
            return false;
        }
        return true;
    }
    public void saveChange(){
        Booking booking = model.getBooking().getValue();
        booking.setCustomerName(binding.edtNameFragBkBookingDetail.getText().toString());
        booking.setPhoneNumber(binding.edtPhonenumberFragBkBookingDetail.getText().toString());
        booking.setCheckin(binding.edtCheckinFragBkBookingDetail.getText().toString());
        booking.setCheckout(binding.edtCheckoutFragBkBookingDetail.getText().toString());
        booking.setPrepayment(Double.parseDouble(binding.edtPrepaymentFragBkBookingDetail.getText().toString()));
        booking.setBookingPrice(Double.parseDouble(binding.edtBookingpriceFragBkBookingDetail.getText().toString()));
        booking.calCulatePrice();
        BookingController.updateBooking(booking);
    }
    public void createBooking(){
        Booking booking = model.getBooking().getValue();
        booking.setCustomerName(binding.edtNameFragBkBookingDetail.getText().toString());
        booking.setPhoneNumber(binding.edtPhonenumberFragBkBookingDetail.getText().toString());
        booking.setCheckin(binding.edtCheckinFragBkBookingDetail.getText().toString());
        booking.setCheckout(binding.edtCheckoutFragBkBookingDetail.getText().toString());
        booking.setPrepayment(Double.parseDouble(binding.edtPrepaymentFragBkBookingDetail.getText().toString()));
        booking.setBookingPrice(Double.parseDouble(binding.edtBookingpriceFragBkBookingDetail.getText().toString()));
        booking.calCulatePrice();
        BookingController.createBooking(booking);
    }
    public void checkin(){
        saveChange();
        BookingController.checkin(model.getBooking().getValue());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onResume() {
        super.onResume();
        adapter.clear();
        adapter.addAll(roomTypeList);
        binding.autoCompleteFragBkBookingDetail.setAdapter(adapter);
    }
}
