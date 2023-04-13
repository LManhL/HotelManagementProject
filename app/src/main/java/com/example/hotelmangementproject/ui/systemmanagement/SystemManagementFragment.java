package com.example.hotelmangementproject.ui.systemmanagement;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmangementproject.MainActivity;
import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.databinding.FragmentHomeBinding;
import com.example.hotelmangementproject.databinding.FragmentSystemManagementBinding;
import com.example.hotelmangementproject.ui.home.HomeViewModel;

public class SystemManagementFragment extends Fragment {

    private FragmentSystemManagementBinding binding;
    CardView cardRoomType,cardCaculate,cardRoom,cardMenu,cardCustomer,cardHistory,cardStatistic;
    Animation animAtg;
    Animation animClick;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SystemManagementViewModel systemManagementViewModel =
                new ViewModelProvider(this).get(SystemManagementViewModel.class);

        binding = FragmentSystemManagementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        animAtg = AnimationUtils.loadAnimation(getActivity(),R.anim.atg);
        animClick = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_click);

        initView();
        setAnim();
        handleChangeFragment();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void initView(){
        cardRoomType = binding.smRoomType;
        cardCaculate = binding.smCaculate;
        cardRoom = binding.smRoom;
        cardMenu = binding.smMenu;
        cardCustomer = binding.smCustomer;
        cardHistory = binding.smHistory;
        cardStatistic = binding.smStatistic;
    }

    private void setAnim(){
        cardRoomType.startAnimation(animAtg);
        cardRoom.startAnimation(animAtg);
        cardCaculate.startAnimation(animAtg);
        cardHistory.startAnimation(animAtg);
        cardMenu.startAnimation(animAtg);
        cardStatistic.startAnimation(animAtg);
        cardCustomer.startAnimation(animAtg);
    }
    private void handleChangeFragment(){
        cardRoomType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardRoomType.startAnimation(animClick);
                Toast.makeText(getActivity(), "Loại phòng", Toast.LENGTH_SHORT).show();
            }
        });

        cardCaculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardCaculate.startAnimation(animClick);
                Toast.makeText(getActivity(), "Cách tính tiền", Toast.LENGTH_SHORT).show();
            }
        });

        cardRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardRoom.startAnimation(animClick);
                Toast.makeText(getActivity(), "Phòng", Toast.LENGTH_SHORT).show();
            }
        });

        cardMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardMenu.startAnimation(animClick);
                Toast.makeText(getActivity(), "Menu", Toast.LENGTH_SHORT).show();
            }
        });

        cardCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardCustomer.startAnimation(animClick);
                Toast.makeText(getActivity(), "Khách hàng", Toast.LENGTH_SHORT).show();
            }
        });

        cardHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardHistory.startAnimation(animClick);
                Toast.makeText(getActivity(), "Lịch sử", Toast.LENGTH_SHORT).show();
            }
        });

        cardStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStatistic.startAnimation(animClick);
                Toast.makeText(getActivity(), "Thống kê", Toast.LENGTH_SHORT).show();
            }
        });
    }
}