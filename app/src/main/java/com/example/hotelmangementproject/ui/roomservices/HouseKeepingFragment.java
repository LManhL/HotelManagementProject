package com.example.hotelmangementproject.ui.roomservices;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.controllers.roomservicescontroller.HouseKeepingController;
import com.example.hotelmangementproject.databinding.FragRsHouseKeepingBinding;
import com.example.hotelmangementproject.interfaces.IClickDirtyRoomListener;
import com.example.hotelmangementproject.models.DirtyRoom;
import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.adapters.roomservicesAdapter.RoomHouseKeepingAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class HouseKeepingFragment extends Fragment {
    private FragRsHouseKeepingBinding binding;
    private RecyclerView recyclerView;
    private RoomHouseKeepingAdapter roomHouseKeepingAdapter;

    private DatabaseReference mDatabase;
    private List<DirtyRoom> listRoom;
    private RoomServiceViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getParentFragmentManager().getBackStackEntryCount() > 1){
            OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
                @Override
                public void handleOnBackPressed() {
                    getParentFragmentManager().popBackStack();
                }
            };
            requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragRsHouseKeepingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        model = new ViewModelProvider(requireActivity()).get(RoomServiceViewModel.class);
        recyclerView = binding.recFragmentHousekeeping;
        roomHouseKeepingAdapter = new RoomHouseKeepingAdapter(getActivity(), new IClickDirtyRoomListener() {
            @Override
            public void onClick(DirtyRoom dirtyRoom) {
                Room room = dirtyRoom;
                model.selectedRoom(room);
                handleSelectItem();
            }

            @Override
            public void onLongClick(DirtyRoom dirtyRoom) {
                showDialog(dirtyRoom);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        listRoom = new ArrayList<>();
        roomHouseKeepingAdapter.setData(getActivity(),listRoom);
        recyclerView.setAdapter(roomHouseKeepingAdapter);
        HouseKeepingController.getListDirtyRoom(listRoom,roomHouseKeepingAdapter);

        return root;

    }

    public void showDialog(DirtyRoom dirtyRoom){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Setting Alert Dialog Title
        alertDialogBuilder.setTitle("Confirm");
        // Icon Of Alert Dialog
        // Setting Alert Dialog Message
        alertDialogBuilder.setMessage("Confirm the room has been cleaned");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                updateToFireBase(dirtyRoom);
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
    public void  updateToFireBase(DirtyRoom dirtyRoom){
        HouseKeepingController.updateState(dirtyRoom);
        Toast.makeText(getActivity(), "Update successfully", Toast.LENGTH_SHORT).show();
    }
    public void handleSelectItem(){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutRentCheckout, new SelectedItemFragment());
        fragmentTransaction.addToBackStack("itemRoom");
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}