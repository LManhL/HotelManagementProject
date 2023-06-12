package com.example.hotelmangementproject.ui.systemmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.adapters.systemmanagementAdapter.RoomAdapter;
import com.example.hotelmangementproject.controllers.systemmanagementcontrollers.RoomController;
import com.example.hotelmangementproject.controllers.systemmanagementcontrollers.RoomTypeController;
import com.example.hotelmangementproject.databinding.FragSmRoomBinding;
import com.example.hotelmangementproject.interfaces.IClickItemRoomListener;
import com.example.hotelmangementproject.models.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomFragment extends Fragment {
    private FragSmRoomBinding binding;
    List<Room> listRoom;
    RoomAdapter roomAdapter;
    RecyclerView recyclerView;
    private SystemManagementViewModel model;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(SystemManagementViewModel.class);

        binding = FragSmRoomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recFragSmRoom;
        listRoom = new ArrayList<>();
        roomAdapter = new RoomAdapter(getActivity(), listRoom, new IClickItemRoomListener() {
            @Override
            public void onClickItem(Room room) {
                model.setRoom(room);
                navToRoomDetail();
            }

            @Override
            public void onLongClickItem(Room room, View view) {
                showDialog(room);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(roomAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        RoomController.getListRoom(listRoom,roomAdapter);
        binding.floatButtonFragSmRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Room room = new Room();
                model.setRoom(room);
                navToRoomDetail();
            }
        });
        return root;
    }
    public void navToRoomDetail(){
        NavController navController = Navigation.findNavController(getView());
        navController.navigate(R.id.action_nav_sm_room_to_nav_sm_room_detail);
    }
    public void showDialog(Room room){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Confirm");
        alertDialogBuilder.setMessage("Do you want to delete this item?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                RoomController.deleteRoom(room,getActivity());
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
