package com.example.hotelmangementproject.ui.systemmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.adapters.systemmanagementAdapter.CustomerAdapter;
import com.example.hotelmangementproject.controllers.systemmanagementcontrollers.CustomerController;
import com.example.hotelmangementproject.controllers.systemmanagementcontrollers.MenuController;
import com.example.hotelmangementproject.databinding.FragSmCustomerBinding;
import com.example.hotelmangementproject.interfaces.IClickCustomer;
import com.example.hotelmangementproject.models.Customer;
import com.example.hotelmangementproject.models.Menu;

import java.util.ArrayList;
import java.util.List;

public class CustomerFragment extends Fragment {
    private FragSmCustomerBinding binding;
    private SystemManagementViewModel model;
    private CustomerAdapter customerAdapter;
    private List<Customer> listCustomer;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(SystemManagementViewModel.class);
        binding = FragSmCustomerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listCustomer = new ArrayList<>();
        recyclerView = binding.recFragSmCustomer;
        customerAdapter = new CustomerAdapter(getActivity(), listCustomer, new IClickCustomer() {
            @Override
            public void onClick(Customer customer) {
                model.setCustomer(customer);
                navToCustomerDetail();
            }

            @Override
            public void onLongClick(Customer customer) {
                showDialog(customer);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);

        recyclerView.setAdapter(customerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        CustomerController.getListCustomer(listCustomer,customerAdapter);
        binding.floatButtonFragSmCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customer customer = new Customer();
                model.setCustomer(customer);
                navToCustomerDetail();
            }
        });

        return root;
    }
    public void navToCustomerDetail(){
        NavController navController = Navigation.findNavController(getView());
        navController.navigate(R.id.action_nav_sm_customer_to_nav_sm_customer_detail);
    }
    public void showDialog(Customer customer){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Confirm");
        alertDialogBuilder.setMessage("Do you want to delete this item?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                CustomerController.deleteCustomer(customer);
                Toast.makeText(getActivity(), "Delete successfully", Toast.LENGTH_SHORT).show();
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
