package com.example.hotelmangementproject.ui.systemmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hotelmangementproject.controllers.systemmanagementcontrollers.PriceRuleController;
import com.example.hotelmangementproject.databinding.FragSmPriceRuleDetailBinding;
import com.example.hotelmangementproject.models.CalMoney;

public class PriceRuleDetailFragment extends Fragment {
    private FragSmPriceRuleDetailBinding binding;
    private SystemManagementViewModel model;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(SystemManagementViewModel.class);

        binding = FragSmPriceRuleDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        model.getCalMoney().observe(this, calMoney -> {
            binding.edtTypeFragSmPriceRuleDetail.setText(calMoney.getType());
            binding.edtFirstBlockFragSmPriceRuleDetail.setText(String.valueOf(calMoney.getFirstBlock()));
            binding.edtFirstBlockPriceFragSmPriceRuleDetail.setText(String.valueOf(calMoney.getFirstBlockPrice()));
            binding.edtAfterFirstBlockPriceFragSmPriceRuleDetail.setText(String.valueOf(calMoney.getAfterFirstBlockPrice()));
            binding.edtCheckinTimeFragSmPriceRuleDetail.setText(String.valueOf(calMoney.getCheckinTime()));
            binding.edtCheckoutTimeFragSmPriceRuleDetail.setText(String.valueOf(calMoney.getCheckoutTime()));
            binding.edtPriceFragSmPriceRuleDetail.setText(String.valueOf(calMoney.getPrice()));
            binding.edtOvertimeSurchargeFragSmPriceRuleDetail.setText(String.valueOf(calMoney.getOvertimeSurcharge()));
            binding.edtExtraAdultFragSmPriceRuleDetail.setText(String.valueOf(calMoney.getExtraAdultPrice()));
            binding.edtExtraChildFragSmPriceRuleDetail.setText(String.valueOf(calMoney.getExtraChildPrice()));
            binding.edtRoundedTo1hFragSmPriceRuleDetail.setText(String.valueOf(calMoney.getRoundedMinutesToOneHour()));
        });
        binding.floatButtonFragSmPriceRuleDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveChange();
            }
        });
        return root;
    }
    public void handleSaveChange(){
        String firstBlock = binding.edtFirstBlockFragSmPriceRuleDetail.getText().toString();
        String firstBlockPrice = binding.edtFirstBlockPriceFragSmPriceRuleDetail.getText().toString();
        String afterFirstBlockPrice = binding.edtAfterFirstBlockPriceFragSmPriceRuleDetail.getText().toString();
        String checkinTime = binding.edtCheckinTimeFragSmPriceRuleDetail.getText().toString();
        String checkoutTime = binding.edtCheckoutTimeFragSmPriceRuleDetail.getText().toString();
        String price = binding.edtPriceFragSmPriceRuleDetail.getText().toString();
        String overtimeSurcharge = binding.edtOvertimeSurchargeFragSmPriceRuleDetail.getText().toString();
        String extraAdult = binding.edtExtraAdultFragSmPriceRuleDetail.getText().toString();
        String extraChild = binding.edtExtraChildFragSmPriceRuleDetail.getText().toString();
        String rountdTo1h = binding.edtRoundedTo1hFragSmPriceRuleDetail.getText().toString();

        if(!checkEmpty(firstBlock,firstBlockPrice,afterFirstBlockPrice,checkinTime,checkoutTime,overtimeSurcharge,extraAdult,extraChild,rountdTo1h,price)) return;

        CalMoney curCalMoney = model.getCalMoney().getValue();
        CalMoney newCalMoney = new CalMoney(curCalMoney.getId(),checkinTime,checkoutTime,Double.parseDouble(extraAdult),
                                         Double.parseDouble(extraChild),Double.parseDouble(price),Double.parseDouble(overtimeSurcharge),
                                         Integer.parseInt(rountdTo1h),Double.parseDouble(afterFirstBlockPrice),Integer.parseInt(firstBlock),
                                         Double.parseDouble(firstBlockPrice), curCalMoney.getType());
        if(newCalMoney.getId().isEmpty()){
            PriceRuleController.createPriceRule(newCalMoney);
            Toast.makeText(getActivity(), "Create price rule successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            PriceRuleController.updatePriceRule(newCalMoney);
            Toast.makeText(getActivity(), "Save change successfully", Toast.LENGTH_SHORT).show();
        }
        NavController navController = Navigation.findNavController(getView());
        navController.popBackStack();
    }

    public boolean checkEmpty(String firstBlock,String firstBlockPrice, String afterFirstBlockPrice, String checkinTime,String checkoutTime, String overtimeSurcharge, String extraAdult, String extraChild,String rountdTo1h,String price ){
        if(firstBlock.isEmpty()){
            binding.edtFirstBlockFragSmPriceRuleDetail.setError("Required");
            return false;
        }
        if(firstBlockPrice.isEmpty()){
            binding.edtFirstBlockPriceFragSmPriceRuleDetail.setError("Required");
            return false;
        }
        if(afterFirstBlockPrice.isEmpty()){
            binding.edtAfterFirstBlockPriceFragSmPriceRuleDetail.setError("Required");
            return false;
        }
        if(checkinTime.isEmpty()){
            binding.edtCheckinTimeFragSmPriceRuleDetail.setError("Required");
            return false;
        }
        if(checkoutTime.isEmpty()){
            binding.edtCheckoutTimeFragSmPriceRuleDetail.setError("Required");
            return false;
        }
        if(overtimeSurcharge.isEmpty()){
            binding.edtOvertimeSurchargeFragSmPriceRuleDetail.setError("Required");
            return false;
        }
        if(extraAdult.isEmpty()){
            binding.edtExtraAdultFragSmPriceRuleDetail.setError("Required");
            return false;
        }
        if(extraChild.isEmpty()){
            binding.edtExtraChildFragSmPriceRuleDetail.setError("Required");
            return false;
        }
        if(rountdTo1h.isEmpty()){
            binding.edtRoundedTo1hFragSmPriceRuleDetail.setError("Required");
            return false;
        }
        return true;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
