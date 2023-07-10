package com.example.hotelmangementproject.ui.roomservices;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.controllers.roomservicescontroller.HistoryController;
import com.example.hotelmangementproject.databinding.FragRsHistoryBinding;
import com.example.hotelmangementproject.interfaces.IClickBillListener;
import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.adapters.roomservicesAdapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {
    private RoomServiceViewModel model;
    private FragRsHistoryBinding binding;
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;

    private List<Bill> listBill;

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
        binding = FragRsHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        model = new ViewModelProvider(requireActivity()).get(RoomServiceViewModel.class);

        recyclerView = binding.recFragmentHistory;
        historyAdapter = new HistoryAdapter(getActivity(), new IClickBillListener() {
            @Override
            public void onClickItem(Bill bill) {
                Log.d("???","dfsadf");
                selectedItem(bill);
            }

            @Override
            public void onLongClickItem(Bill bill, View v) {

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        listBill = new ArrayList<Bill>();
        historyAdapter.setData(getActivity(),listBill);
        recyclerView.setAdapter(historyAdapter);
        HistoryController.getHistory(listBill,historyAdapter);

        return root;
    }
    public void selectedItem(Bill bill){
        model.setBill(bill);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutRentCheckout, new SelectedHistoryFragment());
        fragmentTransaction.addToBackStack("itemHistory");
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}