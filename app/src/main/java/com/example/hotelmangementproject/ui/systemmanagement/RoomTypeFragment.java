package com.example.hotelmangementproject.ui.systemmanagement;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.adapters.systemmanagementAdapter.RoomTypeAdapter;
import com.example.hotelmangementproject.controllers.systemmanagementcontrollers.RoomTypeController;
import com.example.hotelmangementproject.databinding.FragSmRoomtypeBinding;
import com.example.hotelmangementproject.interfaces.IClickRoomTypeListener;

import java.util.ArrayList;
import java.util.List;

public class RoomTypeFragment extends Fragment {
    private FragSmRoomtypeBinding binding;
    private SystemManagementViewModel model;

    private List<String> listRoomType;
    private RoomTypeAdapter roomTypeAdapter;
    private RecyclerView recyclerView;

    private boolean isVisible = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(SystemManagementViewModel.class);

        binding = FragSmRoomtypeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recFragSmRoomtype;
        listRoomType = new ArrayList<>();
        roomTypeAdapter = new RoomTypeAdapter(getActivity(), listRoomType, new IClickRoomTypeListener() {
            @Override
            public void onClick(String roomType) {
                showDialog(roomType);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);

        recyclerView.setAdapter(roomTypeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        RoomTypeController.getListRoomType(listRoomType,roomTypeAdapter);

        binding.floatButtonFragSmRoomtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVisible){
                    // Create new roomtype
                    String roomtype = binding.edtFragSmRoomtype.getText().toString();
                    if(!roomtype.isEmpty()){
                        RoomTypeController.createRoomType(roomtype);
                    }

                    binding.edtFragSmRoomtype.setText("");
                    binding.layoutEdtFragSmRoomtype.setVisibility(View.GONE);

                    hideKeyboard();

                    isVisible = false;
                }
                else{
                    binding.layoutEdtFragSmRoomtype.setVisibility(View.VISIBLE);
                    isVisible = true;
                }
            }
        });


        return root;
    }
    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }
    public void showDialog(String roomtype){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Confirm");
        alertDialogBuilder.setMessage("Do you want to delete this item?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                RoomTypeController.deleteRoomType(roomtype);
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
