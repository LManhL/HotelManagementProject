package com.example.hotelmangementproject.ui.systemmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hotelmangementproject.controllers.systemmanagementcontrollers.CustomerController;
import com.example.hotelmangementproject.databinding.FragSmCustomerDetailBinding;
import com.example.hotelmangementproject.models.Customer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CustomerDetailFragment extends Fragment {
    final Calendar myCalendar= Calendar.getInstance();
    FragSmCustomerDetailBinding binding;
    SystemManagementViewModel model;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(SystemManagementViewModel.class);

        binding = FragSmCustomerDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        model.getCustomer().observe(this, customer -> {
            binding.nameFragSmCustomerDetail.setText(customer.getName());
            binding.dateofbirthFragSmCustomerDetail.setText(customer.getDateOfBirth());
            binding.addressFragSmMenuDetail.setText(customer.getAddress());
            binding.personalIdentifierFragSmMenuDetail.setText(customer.getPersonalIndentifier());
        });
        binding.floatButtonFragSmCustomerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidate()){
                    Customer curCustomer = model.getCustomer().getValue();
                    Customer newCustomer = new Customer(curCustomer.getId(),binding.nameFragSmCustomerDetail.getText().toString(),
                                                        binding.dateofbirthFragSmCustomerDetail.getText().toString(),
                                                        binding.personalIdentifierFragSmMenuDetail.getText().toString(),
                                                        binding.addressFragSmMenuDetail.getText().toString());
                    if(newCustomer.getId().isEmpty()){
                        CustomerController.createCustomer(newCustomer);
                        Toast.makeText(getActivity(), "Create successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        CustomerController.updateCustomer(newCustomer);
                        Toast.makeText(getActivity(), "Update successfully", Toast.LENGTH_SHORT).show();
                    }
                    navToCustomerFragment();
                }
            }
        });
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        binding.dateofbirthFragSmCustomerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return root;
    }
    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        binding.dateofbirthFragSmCustomerDetail.setText(dateFormat.format(myCalendar.getTime()));
    }
    public boolean checkValidate(){
        if(binding.nameFragSmCustomerDetail.getText().toString().isEmpty()){
            binding.nameFragSmCustomerDetail.setError("Required");
            return false;
        }
        if(binding.personalIdentifierFragSmMenuDetail.getText().toString().isEmpty()){
            binding.personalIdentifierFragSmMenuDetail.setError("Required");
            return false;
        }
        if(binding.addressFragSmMenuDetail.getText().toString().isEmpty()){
            binding.addressFragSmMenuDetail.setError("Required");
        }
        if(binding.dateofbirthFragSmCustomerDetail.getText().toString().isEmpty()){
            binding.dateofbirthFragSmCustomerDetail.setError("Required");
        }
        return true;
    }
    public void navToCustomerFragment(){
        NavController navController = Navigation.findNavController(getView());
        navController.popBackStack();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
