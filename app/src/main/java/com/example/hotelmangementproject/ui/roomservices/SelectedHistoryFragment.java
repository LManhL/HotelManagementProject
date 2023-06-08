package com.example.hotelmangementproject.ui.roomservices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelmangementproject.databinding.FragRsItemHistoryDetailBinding;
import com.example.hotelmangementproject.services.TimeService;
import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.interfaces.IClickItemRoomBillListener;
import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.RoomBill;
import com.example.hotelmangementproject.adapters.roomservicesAdapter.RoomBillCheckedinAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectedHistoryFragment extends Fragment {
    private RoomServiceViewModel model;
    private FragRsItemHistoryDetailBinding binding;
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
        binding = FragRsItemHistoryDetailBinding.inflate(inflater, container, false);
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
            binding.txtCheckoutTimeCheckout.setText(TimeService.convertMilisecondsToDate(bill.getSubmitBillTime()));
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
        return root;
    }
    public void handleSelectItem(RoomBill roomBill,Bill bill){
        model.selectedRoomBill(roomBill);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutRentCheckout, new EditCheckedinFragment());
        fragmentTransaction.addToBackStack("editRoomBill");
        fragmentTransaction.commit();
    }
}
