package com.example.hotelmangementproject.ui.systemmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.controllers.systemmanagementcontrollers.MenuController;
import com.example.hotelmangementproject.databinding.FragSmMenuDetailBinding;
import com.example.hotelmangementproject.models.Menu;


public class MenuDetailFragment extends Fragment {
    FragSmMenuDetailBinding binding;
    SystemManagementViewModel model;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(SystemManagementViewModel.class);

        binding = FragSmMenuDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        model.getMenu().observe(this, menu -> {
            binding.importPriceFragSmMenuDetail.setText(String.valueOf(menu.getImportPrice()));
            binding.nameFragSmMenuDetail.setText(menu.getName());
            binding.typeFragSmMenuDetail.setText(menu.getType());
            binding.priceFragSmMenuDetail.setText(String.valueOf(menu.getPrice()));
        });
        binding.floatButtonFragSmMenuDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidate()){
                    Menu curMenu = model.getMenu().getValue();
                    Menu menu = new Menu(curMenu.getId(), Double.valueOf(binding.importPriceFragSmMenuDetail.getText().toString()),
                            binding.nameFragSmMenuDetail.getText().toString(), Double.valueOf(binding.priceFragSmMenuDetail.getText().toString()),
                            binding.typeFragSmMenuDetail.getText().toString());
                    MenuController.updateMenu(menu);
                    navToMenuFragment();
                    Toast.makeText(getActivity(), "Update successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
    public boolean checkValidate(){
        if(binding.nameFragSmMenuDetail.getText().toString().isEmpty()){
            binding.nameFragSmMenuDetail.setError("Required");
            return false;
        }
        if(binding.typeFragSmMenuDetail.getText().toString().isEmpty()){
            binding.typeFragSmMenuDetail.setError("Required");
            return false;
        }
        if(binding.priceFragSmMenuDetail.getText().toString().isEmpty()){
            binding.priceFragSmMenuDetail.setError("Required");
            return false;
        }
        if(binding.importPriceFragSmMenuDetail.getText().toString().isEmpty()){
            binding.importPriceFragSmMenuDetail.setError("Required");
            return false;
        }
        return true;
    }
    public void navToMenuFragment(){
        NavController navController = Navigation.findNavController(getView());
        navController.popBackStack();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
