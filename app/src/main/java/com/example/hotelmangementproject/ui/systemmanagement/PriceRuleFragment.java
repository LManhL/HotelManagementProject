package com.example.hotelmangementproject.ui.systemmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.adapters.systemmanagementAdapter.PriceRuleAdapter;
import com.example.hotelmangementproject.adapters.systemmanagementAdapter.RoomTypeAdapter;
import com.example.hotelmangementproject.controllers.systemmanagementcontrollers.PriceRuleController;
import com.example.hotelmangementproject.databinding.FragSmPriceRuleBinding;
import com.example.hotelmangementproject.interfaces.IClickPriceRule;
import com.example.hotelmangementproject.interfaces.IClickRoomTypeListener;
import com.example.hotelmangementproject.models.CalMoney;

import java.util.ArrayList;
import java.util.List;

public class PriceRuleFragment extends Fragment {
    private FragSmPriceRuleBinding binding;
    private SystemManagementViewModel model;

    private List<CalMoney> listPriceRule;
    private PriceRuleAdapter priceRuleAdapter;
    private RecyclerView recyclerView;

    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapter;

    private List<String> listRoomType;

    private boolean isVisible = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(SystemManagementViewModel.class);

        binding = FragSmPriceRuleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recFragSmPriceRule;
        listPriceRule = new ArrayList<>();
        priceRuleAdapter = new PriceRuleAdapter(getActivity(), listPriceRule, new IClickPriceRule() {
            @Override
            public void onClick(CalMoney calMoney) {
                model.setCalMoney(calMoney);
                navToPriceRuleDetail();
            }
        });

        recyclerView.setAdapter(priceRuleAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        PriceRuleController.getListPriceRule(listPriceRule,priceRuleAdapter);


        listRoomType = new ArrayList<>();
        PriceRuleController.getListRoomTypeAvailable(listRoomType);
        // Khởi tạo ArrayAdapter để hiển thị danh sách
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, listRoomType);
        // Lấy tham chiếu tới AutoCompleteTextView
        autoCompleteTextView = binding.autoCompleteFragSmPriceRule;
        // Đặt adapter cho AutoCompleteTextView
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.showDropDown();
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CalMoney calMoney = new CalMoney();
                calMoney.setType(listRoomType.get(position));
                model.setCalMoney(calMoney);
                navToPriceRuleDetail();
            }
        });

        binding.autoCompleteFragSmPriceRule.setVisibility(View.INVISIBLE);
        binding.floatButtonFragSmPriceRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVisible){
                    isVisible = false;
                    binding.autoCompleteFragSmPriceRule.setVisibility(View.INVISIBLE);
                }
                else{
                    isVisible = true;
                    binding.autoCompleteFragSmPriceRule.setVisibility(View.VISIBLE);
                }
            }
        });

        return root;
    }

    public void navToPriceRuleDetail(){
        NavController navController = Navigation.findNavController(getView());
        navController.navigate(R.id.action_nav_sm_price_rule_to_nav_sm_price_rule_detail);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
