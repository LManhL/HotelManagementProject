package com.example.hotelmangementproject.ui.roomservices;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.databinding.FragRsMainBinding;


public class RoomServiceFragment extends Fragment {

    private FragRsMainBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RoomServiceViewModel roomServiceViewModel =
                new ViewModelProvider(this).get(RoomServiceViewModel.class);

        binding = FragRsMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        FragmentManager fragmentManager = getChildFragmentManager();
        handleNavigation(new AvailableFragment(),"available");
        binding.botNavRentCheckout.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bot_nav_available:
                    handleNavigation(new AvailableFragment(),"available");
                    break;
                case R.id.bot_nav_checkedin:
                    handleNavigation(new CheckedinFragment(),"checkedin");
                    break;
                case R.id.bot_nav_history:
                    handleNavigation(new HistoryFragment(),"history");
                    break;
                case R.id.bot_nav_housekeeping:
                    handleNavigation(new HouseKeepingFragment(),"housekeeping");
                    break;
            }
            return true;
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void handleNavigation(Fragment fragment, String tag){
        Fragment check = getChildFragmentManager().findFragmentByTag(tag);
        if(check != null) {
            getChildFragmentManager().popBackStackImmediate(tag,0);
        }
        else{
            replaceFragment(fragment,tag);
        }
    }
    public void replaceFragment (Fragment fragment, String tag){
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayoutRentCheckout,fragment,tag)
                .addToBackStack(tag)
                .commit();
    }
}