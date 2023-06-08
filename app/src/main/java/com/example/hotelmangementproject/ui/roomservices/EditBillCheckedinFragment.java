package com.example.hotelmangementproject.ui.roomservices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hotelmangementproject.controllers.rentcheckoutcontrollers.EditBillCheckedinController;
import com.example.hotelmangementproject.databinding.FragRsEditBillBinding;

import com.example.hotelmangementproject.models.Bill;

public class EditBillCheckedinFragment extends Fragment {

    private RoomServiceViewModel model;
    private FragRsEditBillBinding binding;

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
        binding = FragRsEditBillBinding.inflate(inflater, container, false);
        model = new ViewModelProvider(requireActivity()).get(RoomServiceViewModel.class);
        View root = binding.getRoot();

        model.getBill().observe(this, bill -> {
            binding.edtCustomernameEditbillCheckedin.setText(bill.getCustomerName());
            binding.edtPhonenumberEditbillCheckedin.setText(bill.getPhoneNumber());
            binding.edtNoteEditbillCheckedin.setText(bill.getNote());
            binding.edtOtherpriceEditbillCheckedin.setText(Double.toString(bill.getOtherPrice()));
            binding.edtPrepaymentEditbillCheckedin.setText(Double.toString(bill.getPrepayment()));
            binding.edtSurchargeEditbillCheckedin.setText(Double.toString(bill.getSurcharge()));
            binding.txtMenucostEditbillCheckedin.setText("Menucost: "+Double.toString(bill.getMenuCost()));
            binding.txtTotalpriceEditbillCheckedin.setText("Total price: "+Double.toString(bill.getTotalPrice()));
            binding.txtTotalroomcostEditbillCheckedin.setText("Total roomcost: "+Double.toString(bill.getTotalRoomCost()));
        });
        binding.btnSaveEditbillCheckedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveChange();
            }
        });
        return root;

    }

    public void handleSaveChange(){
        Bill bill = model.getBill().getValue();

        if(!handleValidate()) return;

        bill.setCustomerName(binding.edtCustomernameEditbillCheckedin.getText().toString());
        bill.setPhoneNumber(binding.edtPhonenumberEditbillCheckedin.getText().toString());
        bill.setNote(binding.edtNoteEditbillCheckedin.getText().toString());
        bill.setOtherPrice(Double.parseDouble(binding.edtOtherpriceEditbillCheckedin.getText().toString()));
        bill.setPrepayment(Double.parseDouble(binding.edtPrepaymentEditbillCheckedin.getText().toString()));
        bill.setSurcharge(Double.parseDouble(binding.edtSurchargeEditbillCheckedin.getText().toString()));
        // caculate bill cost
        bill.caculateBill();
        model.setBill(bill);

        EditBillCheckedinController.updateToFirebase(model.getBill().getValue());

        Toast.makeText(getActivity(), "Save change successfully", Toast.LENGTH_SHORT).show();
    }
    public boolean handleValidate(){
        boolean check = true;
        if(binding.edtCustomernameEditbillCheckedin.getText().toString().isEmpty()){
            check = false;
            binding.edtCustomernameEditbillCheckedin.setError("This input is required");
        }
        if(binding.edtPhonenumberEditbillCheckedin.getText().toString().isEmpty()){
            check = false;
            binding.edtPhonenumberEditbillCheckedin.setError("This input is required");
        }
        if(binding.edtOtherpriceEditbillCheckedin.getText().toString().isEmpty()){
            check = false;
            binding.edtOtherpriceEditbillCheckedin.setError("This input is required");
        }
        if(binding.edtPrepaymentEditbillCheckedin.getText().toString().isEmpty()){
            check = false;
            binding.edtPrepaymentEditbillCheckedin.setError("This input is required");
        }
        if(binding.edtSurchargeEditbillCheckedin.getText().toString().isEmpty()){
            check = false;
            binding.edtSurchargeEditbillCheckedin.setError("This input is required");
        }
        return check;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
