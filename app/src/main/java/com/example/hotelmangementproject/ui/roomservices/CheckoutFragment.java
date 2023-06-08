package com.example.hotelmangementproject.ui.roomservices;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelmangementproject.databinding.FragRsCheckoutBinding;
import com.example.hotelmangementproject.services.TimeService;
import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.controllers.rentcheckoutcontrollers.CheckoutController;
import com.example.hotelmangementproject.interfaces.IClickItemRoomBillListener;
import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.RoomBill;
import com.example.hotelmangementproject.adapters.roomservicesAdapter.RoomBillCheckedinAdapter;

import java.util.ArrayList;
import java.util.List;

public class CheckoutFragment extends Fragment {
    public static boolean haveSaved = true;
    private RoomServiceViewModel model;
    private FragRsCheckoutBinding binding;
    private List<RoomBill> listRoomBill;
    private RoomBillCheckedinAdapter roomBillCheckedinAdapter;

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
        binding = FragRsCheckoutBinding.inflate(inflater, container, false);
        model = new ViewModelProvider(requireActivity()).get(RoomServiceViewModel.class);
        View root = binding.getRoot();
        listRoomBill = new ArrayList<>();
        roomBillCheckedinAdapter = new RoomBillCheckedinAdapter(getActivity(), new IClickItemRoomBillListener() {
            @Override
            public void onClickItem(RoomBill roomBill, Bill bill) {
                handleSelectItem(roomBill,model.getBill().getValue());
            }

            @Override
            public void onLongClickItem(RoomBill roomBill, View view) {

            }
        });

        model.getBill().observe(this, bill -> {
            binding.txtCheckinTimeCheckout.setText(TimeService.convertMilisecondsToDate(bill.getCreateAt()));
            binding.txtCheckoutTimeCheckout.setText(TimeService.convertMilisecondsToDate(TimeService.getCurrentTime()));
            binding.edtCustomernameEditbillCheckedin.setText(bill.getCustomerName());
            binding.edtPhonenumberEditbillCheckedin.setText(bill.getPhoneNumber());
            binding.edtNoteEditbillCheckedin.setText(bill.getNote());
            binding.edtOtherpriceEditbillCheckedin.setText(Double.toString(bill.getOtherPrice()));
            binding.edtPrepaymentEditbillCheckedin.setText(Double.toString(bill.getPrepayment()));
            binding.edtSurchargeEditbillCheckedin.setText(Double.toString(bill.getSurcharge()));
            binding.txtMenucostEditbillCheckedin.setText("Menucost: "+Double.toString(bill.getMenuCost()));
            binding.txtTotalpriceEditbillCheckedin.setText("Total price: "+Double.toString(bill.getTotalPrice()));
            binding.txtTotalroomcostEditbillCheckedin.setText("Total roomcost: "+Double.toString(bill.getTotalRoomCost()));

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
            roomBillCheckedinAdapter.setData(listRoomBill,bill);
            binding.recCheckout.setLayoutManager(linearLayoutManager);
            binding.recCheckout.setAdapter(roomBillCheckedinAdapter);
            if(listRoomBill != null) {
                listRoomBill.clear();
            }
            listRoomBill.addAll(bill.getListRoomBill());
            roomBillCheckedinAdapter.notifyDataSetChanged();

        });
        binding.btnSaveEditbillCheckedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveChange();
            }
        });
        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(model.getBill().getValue());
            }
        });
        return root;
    }
    public void handleSaveChange(){
        Bill bill = model.getBill().getValue();

        if(!handleValidate()) return;

        bill.setSubmitBillTime(TimeService.getCurrentTime());
        bill.setCustomerName(binding.edtCustomernameEditbillCheckedin.getText().toString());
        bill.setPhoneNumber(binding.edtPhonenumberEditbillCheckedin.getText().toString());
        bill.setNote(binding.edtNoteEditbillCheckedin.getText().toString());
        bill.setOtherPrice(Double.parseDouble(binding.edtOtherpriceEditbillCheckedin.getText().toString()));
        bill.setPrepayment(Double.parseDouble(binding.edtPrepaymentEditbillCheckedin.getText().toString()));
        bill.setSurcharge(Double.parseDouble(binding.edtSurchargeEditbillCheckedin.getText().toString()));
        // caculate bill cost
        bill.caculateBill();
        model.setBill(bill);

        CheckoutController.updateToFirebase(model.getBill().getValue());

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
    public void handleSelectItem(RoomBill roomBill,Bill bill){
        model.selectedRoomBill(roomBill);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutRentCheckout, new EditCheckedinFragment());
        fragmentTransaction.addToBackStack("editRoomBill");
        fragmentTransaction.commit();
    }
    public void showDialog(Bill bill){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Setting Alert Dialog Title
        alertDialogBuilder.setTitle("Confirm");
        // Icon Of Alert Dialog
        // Setting Alert Dialog Message
        alertDialogBuilder.setMessage("Do you want to save all changed and checkout?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                CheckoutController.checkoutBillOnFireBase(getActivity(),bill);
                getParentFragmentManager().popBackStack();
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
}
