package com.example.hotelmangementproject.ui.systemmanagement;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.databinding.FragSmMainBinding;

public class SystemManagementFragment extends Fragment {

    private FragSmMainBinding binding;
    CardView cardRoomType,cardCaculate,cardRoom,cardMenu,cardCustomer,cardHistory,cardStatistic;
    Animation animAtg;
    Animation animClick;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SystemManagementViewModel systemManagementViewModel =
                new ViewModelProvider(getActivity()).get(SystemManagementViewModel.class);

        binding = FragSmMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        animAtg = AnimationUtils.loadAnimation(getActivity(),R.anim.atg);
        animClick = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_click);

        initView();
        setAnim();
        handleChangeFragment();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getView());
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
                handleNavigate(R.id.action_nav_system_management_to_nav_sm_roomtype);
            }
        });

        cardCaculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardCaculate.startAnimation(animClick);
                handleNavigate(R.id.action_nav_system_management_to_nav_sm_price_rule);
            }
        });

        cardRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardRoom.startAnimation(animClick);
                handleNavigate(R.id.action_nav_system_management_to_nav_sm_room);
            }
        });

        cardMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardMenu.startAnimation(animClick);
                handleNavigate(R.id.action_nav_system_management_to_nav_sm_menu);
            }
        });

        cardCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardCustomer.startAnimation(animClick);
                handleNavigate(R.id.action_nav_system_management_to_nav_sm_customer);
            }
        });

        cardHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardHistory.startAnimation(animClick);
                handleNavigate(R.id.action_nav_system_management_to_nav_sm_history);
            }
        });

        cardStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStatistic.startAnimation(animClick);
                handleNavigate(R.id.action_nav_system_management_to_nav_sm_statistic);
            }
        });
    }
    public void handleNavigate(int id){
        navController.navigate(id);
    }
}