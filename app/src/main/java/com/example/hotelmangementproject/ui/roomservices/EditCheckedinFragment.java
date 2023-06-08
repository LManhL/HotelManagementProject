package com.example.hotelmangementproject.ui.roomservices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.hotelmangementproject.controllers.rentcheckoutcontrollers.EditCheckedinController;
import com.example.hotelmangementproject.databinding.FragRsEditRoombillBinding;
import com.example.hotelmangementproject.interfaces.IClickItemListMenuListener;
import com.example.hotelmangementproject.models.CalMoney;
import com.example.hotelmangementproject.adapters.roomservicesAdapter.ImageSlideAdapter;
import com.example.hotelmangementproject.adapters.roomservicesAdapter.ListMenuAdapter;
import com.example.hotelmangementproject.models.MenuBill;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class EditCheckedinFragment extends Fragment {
    private FragRsEditRoombillBinding binding;
    private RoomServiceViewModel model;
    private ListMenuAdapter listMenuAdapter;
    private List<MenuBill> listMenuBill;
    private RecyclerView recyclerView;
    private ImageSlideAdapter imageSlideAdapter;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private double menuDiff;
    private double roomcostDiff;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragRsEditRoombillBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        model = new ViewModelProvider(requireActivity()).get(RoomServiceViewModel.class);
        viewPager = binding.viewpagerCheckedin;
        circleIndicator = binding.cirleindicatorCheckedin;
        imageSlideAdapter = new ImageSlideAdapter(getActivity(), model.getRoomBill().getValue().getListImage());
        viewPager.setAdapter(imageSlideAdapter);
        circleIndicator.setViewPager(viewPager);
        imageSlideAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        recyclerView = binding.recFragmentEditCheckedin;
        listMenuBill = new ArrayList<MenuBill>();
        menuDiff = 0;
        roomcostDiff = 0;
        listMenuAdapter = new ListMenuAdapter(getActivity(), listMenuBill, new IClickItemListMenuListener() {
            @Override
            public void onClickAdd(MenuBill menuBill) {
                handleClickAdd(menuBill);
            }

            @Override
            public void onClickMinus(MenuBill menuBill) {
                handleClickMinus(menuBill);
            }
        });
        listMenuAdapter.setData(listMenuBill);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(listMenuAdapter);

        model.getRoomBill().observe(this, roomBill -> {
            CalMoney calMoney = roomBill.getCalMoney();
            // UPDATE UI
            binding.itemRoomnameSelectedCheckedin.setText(roomBill.getName().toUpperCase());
            binding.itemCheckinTimeSelectedCheckedin.setText("Checkin Time: "+ calMoney.getCheckinTime());
            binding.itemAfterFirstBlockPriceSelectedCheckedin.setText("After FirstBlock Price: "+ calMoney.getAfterFirstBlockPrice());
            binding.itemDescriptionSelectedAcheckedin.setText("Description: "+roomBill.getDescription());
            binding.itemRoomtypesSelectedCheckedin.setText("Roomtypes: "+roomBill.getRoomTypes());
            binding.itemExtraAdultPriceSelectedCheckedin.setText("Extra Adult Price: "+ calMoney.getExtraAdultPrice());
            binding.itemExtraChildPriceSelectedCheckedin.setText("Extra Child Price: "+calMoney.getExtraChildPrice());
            binding.itemCheckoutTimeSelectedCheckedin.setText("Checkout Time: "+calMoney.getCheckoutTime());
            binding.itemOvertimeSurchargeSelectedCheckedin.setText("Overtime Surcharge: "+calMoney.getOvertimeSurcharge());
            binding.itemRoundedMinutesToOneHourSelectedCheckedin.setText("Rounded Minutes to one hour: "+ calMoney.getRoundedMinutesToOneHour());
            binding.itemFirstBlockSelectedCheckedin.setText("First block: "+calMoney.getFirstBlock());
            binding.itemFirstBlockPriceSelectedCheckedin.setText("First block Price: "+ calMoney.getFirstBlockPrice());
            binding.itemPriceSelectedCheckedin.setText("Price: "+calMoney.getPrice());
            binding.txtExtraChildEditCheckedin.setText(Integer.toString(roomBill.getExtraChild()));
            binding.txtExtraAdultEditCheckedin.setText(Integer.toString(roomBill.getExtraAdult()));
            binding.edtNoteEditCheckedin.setText(roomBill.getNote());
            binding.txtRoomcostEditCheckedin.setText("Roomcost: "+ roomBill.getRoomCost());

            imageSlideAdapter.setData(roomBill.getListImage());
            imageSlideAdapter.notifyDataSetChanged();

            if(model.getBill().getValue().getState() == 2){
                binding.btnCaculateRoomcost.setEnabled(false);
                binding.btnAddListMenuEditCheckedin.setEnabled(false);
                binding.btnMinusExtraChildEditCheckedin.setEnabled(false);
                binding.btnMinusExtraAdult.setEnabled(false);
                binding.btnAddExtraChildEditCheckedin.setEnabled(false);
                binding.btnAddExtraAdult.setEnabled(false);
            }

        });

        model.getListMenuBill().observe(this, list -> {
            if(listMenuBill != null){
                listMenuBill.clear();
            }

            if(list != null){
                for(MenuBill value: list){
                    listMenuBill.add(value);
                    listMenuAdapter.notifyDataSetChanged();
                }
            }
        });


        binding.btnAddExtraAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = binding.txtExtraAdultEditCheckedin.getText().toString();
                int count = Integer.parseInt(value) + 1;
                binding.txtExtraAdultEditCheckedin.setText(Integer.toString(Math.max(0,count)));
            }
        });
        binding.btnMinusExtraAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = binding.txtExtraAdultEditCheckedin.getText().toString();
                int count = Integer.parseInt(value) - 1;
                binding.txtExtraAdultEditCheckedin.setText(Integer.toString(Math.max(0,count)));
            }
        });
        binding.btnAddExtraChildEditCheckedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = binding.txtExtraChildEditCheckedin.getText().toString();
                int count = Integer.parseInt(value) + 1;
                binding.txtExtraChildEditCheckedin.setText(Integer.toString(Math.max(0,count)));
            }
        });
        binding.btnMinusExtraChildEditCheckedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = binding.txtExtraChildEditCheckedin.getText().toString();
                int count = Integer.parseInt(value) - 1;
                binding.txtExtraChildEditCheckedin.setText(Integer.toString(Math.max(0,count)));
            }
        });
        binding.btnAddListMenuEditCheckedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = EditMenuDialog.newInstance();
                ((EditMenuDialog) dialog).setCallback(new EditMenuDialog.Callback() {
                    @Override
                    public void onActionClick() {
                        Toast.makeText(getActivity(), "Edit menu successfull!", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show(getParentFragmentManager(), "tag");
            }
        });
        binding.btnCaculateRoomcost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int extraAdult = Integer.parseInt(binding.txtExtraAdultEditCheckedin.getText().toString());
                int extraChild = Integer.parseInt(binding.txtExtraChildEditCheckedin.getText().toString());
                String note = binding.edtNoteEditCheckedin.getText().toString();

                // value is changed
                model.setNewRoomBill(extraChild,extraAdult, note);

                model.changeBill();

                EditCheckedinController.updateDataToFireBase(model.getBill().getValue());
                Toast.makeText(getActivity(), "Save change successfully", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    public void handleClickAdd(MenuBill menuBill){
        model.addToListMenuBill(menuBill,1);
    }
    public void handleClickMinus(MenuBill menuBill){
       model.addToListMenuBill(menuBill,-1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
