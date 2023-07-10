package com.example.hotelmangementproject.ui.roomservices;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.controllers.roomservicescontroller.AvailableController;
import com.example.hotelmangementproject.databinding.FragRsAvailableBinding;
import com.example.hotelmangementproject.interfaces.IClickItemRoomListener;
import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.adapters.roomservicesAdapter.RoomAvailableAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AvailableFragment extends Fragment {
    public final int TYPE_RENT_BY_DAY = 1;
    public final int TYPE_RENT_BY_HOUR = 2;
    private FragRsAvailableBinding binding;
    private RecyclerView recyclerView;
    private RoomAvailableAdapter roomAvailableAdapter;

    private List<Room> listRoom;
    private RoomServiceViewModel model;
    private EditText edtCheckinTime;
    private EditText edtCheckouTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getParentFragmentManager().getBackStackEntryCount() > 0){
            OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
                @Override
                public void handleOnBackPressed() {
                    if(getParentFragmentManager().getBackStackEntryCount() == 1){
                        Navigation.findNavController(getView()).popBackStack();
                    }
                    else{
                        getParentFragmentManager().popBackStack();
                    }
                }
            };
            requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragRsAvailableBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recFragmentAvailable;
        model = new ViewModelProvider(requireActivity()).get(RoomServiceViewModel.class);
        listRoom = new ArrayList<>();

        roomAvailableAdapter = new RoomAvailableAdapter(getActivity(), listRoom, new IClickItemRoomListener() {
            @Override
            public void onClickItem(Room room) {
                model.selectedRoom(room);
                handleSelectItem();
            }
            @Override
            public void onLongClickItem(Room room, View view){
                showPopupMenu(room,view);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        roomAvailableAdapter.setData(listRoom);
        recyclerView.setAdapter(roomAvailableAdapter);
        AvailableController.getListRoomFromFireBase(listRoom, roomAvailableAdapter);

        return root;
    }
    public void handleSelectItem(){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutRentCheckout, new SelectedItemFragment());
        fragmentTransaction.addToBackStack("itemRoom");
        fragmentTransaction.commit();
    }
    public void showPopupMenu(Room room, View view){
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.menu_available); // R.menu.menu là tệp menu.xml bạn đã tạo

        setForceShowIcon(popupMenu);


        // Đăng ký lắng nghe sự kiện cho các menu item
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Xử lý sự kiện khi người dùng chọn một menu item
                switch (menuItem.getItemId()) {
                    case R.id.menu_item_checkinbyhour_without_booking:
                        showDialog(room, TYPE_RENT_BY_HOUR);
                        return true;
                    case R.id.menu_item_checkinbyday_without_booking:
                        showDialog(room, TYPE_RENT_BY_DAY);
                        return true;
                    case R.id.menu_item_checkin_with_booking:
                        navToBookingFragment();
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }
    public void navToBookingFragment(){
        NavController navController = Navigation.findNavController(getView());
        navController.navigate(R.id.nav_booking);
    }

    protected void showDialog(Room room, int type){

        Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);

        View view  = getActivity().getLayoutInflater().inflate(R.layout.frag_rs_custom_dialog_input, null);
        dialog.setContentView(view);

        EditText customerName = view.findViewById(R.id.edt_customername_available);
        EditText phonenumber = view.findViewById(R.id.edt_phonenumber_available);
        Button btnConfirm = view.findViewById(R.id.btn_confirm_available);
        Button btnCancle = view.findViewById(R.id.btn_cancle_available);

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customerName.toString().isEmpty() || phonenumber.toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please enter full information!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(type == TYPE_RENT_BY_HOUR){
                        AvailableController.checkinWithoutBooking(
                                getActivity(),room, type,customerName.getText().toString(),
                                phonenumber.getText().toString(),null,null
                        );
                        dialog.dismiss();
                    }
                    else if(type == TYPE_RENT_BY_DAY){
                        dialog.dismiss();
                        showDialogPickTime(room, type,customerName.getText().toString(), phonenumber.getText().toString());
                    }
                }
            }
        });
        dialog.show();
    };
    public void showDialogPickTime(Room room, int type,String customerName, String phonenumber){
        boolean isCheckinTime = true;
        Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);

        View view  = getActivity().getLayoutInflater().inflate(R.layout.frag_rs_custom_dialog_choose_date, null);
        dialog.setContentView(view);

        edtCheckinTime = view.findViewById(R.id.edt_checkintime_available);
        edtCheckouTime = view.findViewById(R.id.edt_checkoutime_available);
        Button btnCheckintime = view.findViewById(R.id.btn_checkintime_available);
        Button btnCheckoutime = view.findViewById(R.id.btn_checkouttime_available);
        ImageButton btnConfirm = view.findViewById(R.id.btn_confirm_datepicker);
        ImageButton btnCancle = view.findViewById(R.id.btn_cancle_datepicker);

        btnCheckintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime(isCheckinTime);
            }
        });
        btnCheckoutime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime(!isCheckinTime);
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCheckinTime.toString().isEmpty() || edtCheckouTime.toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please enter full information!", Toast.LENGTH_SHORT).show();
                }
                else{
                    AvailableController.checkinWithoutBooking(
                            getActivity(),room, type, customerName, phonenumber,
                            edtCheckinTime.getText().toString(),edtCheckouTime.getText().toString()
                    );
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    public void pickTime(boolean isCheckinTime){
        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                if(isCheckinTime == true){
                    edtCheckinTime.setText(simpleDateFormat.format(calendar.getTime()));
                }
                else{
                    edtCheckouTime.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        },year,month,dayOfMonth);
        datePickerDialog.show();
    }
    public void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}