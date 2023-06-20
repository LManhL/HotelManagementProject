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
import com.example.hotelmangementproject.adapters.systemmanagementAdapter.MenuAdapter;
import com.example.hotelmangementproject.controllers.systemmanagementcontrollers.MenuController;
import com.example.hotelmangementproject.controllers.systemmanagementcontrollers.PriceRuleController;
import com.example.hotelmangementproject.databinding.FragSmMenuBinding;
import com.example.hotelmangementproject.interfaces.IClickMenuListener;
import com.example.hotelmangementproject.models.CalMoney;
import com.example.hotelmangementproject.models.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {
    private FragSmMenuBinding binding;
    private SystemManagementViewModel model;
    RecyclerView recyclerView;
    MenuAdapter menuAdapter;
    List<Menu> listMenu;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(SystemManagementViewModel.class);

        binding = FragSmMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recFragSmMenu;
        listMenu = new ArrayList<>();
        menuAdapter = new MenuAdapter(getActivity(), listMenu, new IClickMenuListener() {
            @Override
            public void onClick(Menu menu) {
                model.setMenu(menu);
                navToMenuDetail();
            }

            @Override
            public void onLongClick(Menu menu) {
                showDialog(menu);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(menuAdapter);

        MenuController.getListMenu(listMenu, menuAdapter);
        binding.floatButtonFragSmMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu menu = new Menu();
                model.setMenu(menu);
                navToMenuDetail();
            }
        });
        return root;
    }
    public void navToMenuDetail(){
        NavController navController = Navigation.findNavController(getView());
        navController.navigate(R.id.action_nav_sm_menu_to_nav_sm_menu_detail);
    }
    public void showDialog(Menu menu){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Confirm");
        alertDialogBuilder.setMessage("Do you want to delete this item?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                MenuController.deleteMenu(menu);
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
