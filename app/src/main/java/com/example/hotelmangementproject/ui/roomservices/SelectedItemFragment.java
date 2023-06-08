package com.example.hotelmangementproject.ui.roomservices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.hotelmangementproject.databinding.FragRsItemAvailableDetailBinding;
import com.example.hotelmangementproject.models.CalMoney;
import com.example.hotelmangementproject.adapters.roomservicesAdapter.ImageSlideAdapter;

import me.relex.circleindicator.CircleIndicator;

public class SelectedItemFragment extends Fragment {
    FragRsItemAvailableDetailBinding binding;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    ImageSlideAdapter imageSlideAdapter;
    RoomServiceViewModel model;
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
        binding = FragRsItemAvailableDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        model = new ViewModelProvider(requireActivity()).get(RoomServiceViewModel.class);

        viewPager = binding.viewpagerAvailable;
        circleIndicator = binding.cirleindicatorAvailable;

        imageSlideAdapter = new ImageSlideAdapter(getActivity(), model.getRoom().getValue().getListImage());

        viewPager.setAdapter(imageSlideAdapter);

        circleIndicator.setViewPager(viewPager);
        imageSlideAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        model.getRoom().observe(this, room -> {
            CalMoney calMoney = room.getCalMoney();
            // UPDATE UI
            binding.itemRoomnameSelectedAvailable.setText(room.getName().toUpperCase());
            binding.itemCheckinTimeSelectedAvailable.setText("Checkin Time: "+ calMoney.getCheckinTime());
            binding.itemAfterFirstBlockPriceSelectedAvailable.setText("After FirstBlock Price: "+ calMoney.getAfterFirstBlockPrice());
            binding.itemDescriptionSelectedAvailable.setText("Description: "+room.getDescription());
            binding.itemRoomtypesSelectedAvailable.setText("Roomtypes: "+room.getRoomTypes());
            binding.itemExtraAdultPriceSelectedAvailable.setText("Extra Adult Price: "+ calMoney.getExtraAdultPrice());
            binding.itemExtraChildPriceSelectedAvailable.setText("Extra Child Price: "+calMoney.getExtraChildPrice());
            binding.itemCheckoutTimeSelectedAvailable.setText("Checkout Time: "+calMoney.getCheckoutTime());
            binding.itemOvertimeSurchargeSelectedAvailable.setText("Overtime Surcharge: "+calMoney.getOvertimeSurcharge());
            binding.itemRoundedMinutesToOneHourSelectedAvailable.setText("Rounded Minutes to one hour: "+ calMoney.getRoundedMinutesToOneHour());
            binding.itemFirstBlockSelectedAvailable.setText("First block: "+calMoney.getFirstBlock());
            binding.itemFirstBlockPriceSelectedAvailable.setText("First block Price: "+ calMoney.getFirstBlockPrice());
            binding.itemPriceSelectedAvailable.setText("Price: "+calMoney.getPrice());


            imageSlideAdapter.setData(room.getListImage());
            imageSlideAdapter.notifyDataSetChanged();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
