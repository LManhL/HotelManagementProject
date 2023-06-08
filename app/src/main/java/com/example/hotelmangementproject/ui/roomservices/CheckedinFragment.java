package com.example.hotelmangementproject.ui.roomservices;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.controllers.rentcheckoutcontrollers.CheckedinController;
import com.example.hotelmangementproject.databinding.FragRsCheckinBinding;
import com.example.hotelmangementproject.interfaces.IClickBillListener;
import com.example.hotelmangementproject.interfaces.IClickItemRoomBillListener;
import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.adapters.roomservicesAdapter.ListDataCheckedinAdapter;
import com.example.hotelmangementproject.models.RoomBill;
import com.google.firebase.database.Query;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class CheckedinFragment extends Fragment {

    public static boolean isUpdate = true;
    private FragRsCheckinBinding binding;
    private RecyclerView recyclerView;
    private Button btnChangeType;
    private ListDataCheckedinAdapter listDataCheckedinAdapter;

    private Query query;
    private List<Bill> listBill;
    private RoomServiceViewModel model;
    private Boolean rentByDay = true;
    Handler handler = new Handler();
    Runnable runnable;

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
        binding = FragRsCheckinBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        model = new ViewModelProvider(requireActivity()).get(RoomServiceViewModel.class);
        recyclerView = binding.recFragmentCheckedin;
        btnChangeType = binding.buttonCheckedin;
        listDataCheckedinAdapter = new ListDataCheckedinAdapter(getActivity(), new IClickItemRoomBillListener() {
            @Override
            public void onClickItem(RoomBill roomBill,Bill bill) {
                handleSelectItem(roomBill,bill);
            }

            @Override
            public void onLongClickItem(RoomBill roomBill, View view) {

            }
        }, new IClickBillListener() {
            @Override
            public void onClickItem(Bill bill) {
                handleSelectedBill(bill);
            }

            @Override
            public void onLongClickItem(Bill bill,View view) {
                showPopupMenu(bill,view);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        listBill = new ArrayList<>();
        listDataCheckedinAdapter.setData(listBill);
        recyclerView.setAdapter(listDataCheckedinAdapter);
        CheckedinController.getListRoom(rentByDay,listBill,listDataCheckedinAdapter);

        if(rentByDay){
            btnChangeType.setText("Rent by day");
        }
        else btnChangeType.setText("Rent by hour");

        btnChangeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rentByDay){
                    rentByDay = false;
                    btnChangeType.setText("Rent by hour");
                    CheckedinController.getListRoom(rentByDay,listBill,listDataCheckedinAdapter);
                }
                else{
                    rentByDay = true;
                    btnChangeType.setText("Rent by day");
                    CheckedinController.getListRoom(rentByDay,listBill,listDataCheckedinAdapter);
                }
            }
        });
        return root;
    }
    public void showPopupMenu(Bill bill, View view){
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.menu_checkedin); // R.menu.menu là tệp menu.xml bạn đã tạo

        setForceShowIcon(popupMenu);


        // Đăng ký lắng nghe sự kiện cho các menu item
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Xử lý sự kiện khi người dùng chọn một menu item
                switch (menuItem.getItemId()) {
                    case R.id.menu_item_checkout:
                        showCheckoutDialog(bill);
                        return true;
                    case R.id.menu_item_delete_checkedin:
                        handleDeleteBill(bill);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }
    public void showCheckoutDialog(Bill bill){
        model.setBill(bill);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutRentCheckout, new CheckoutFragment());
        fragmentTransaction.addToBackStack("checkout");
        fragmentTransaction.commit();
    }
    public void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    public void handleSelectItem(RoomBill roomBill,Bill bill){
        model.selectedRoomBill(roomBill);
        model.setBill(bill);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutRentCheckout, new EditCheckedinFragment());
        fragmentTransaction.addToBackStack("editRoomBill");
        fragmentTransaction.commit();

    }

    public void handleSelectedBill(Bill bill){
        model.setBill(bill);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutRentCheckout, new EditBillCheckedinFragment());
        fragmentTransaction.addToBackStack("editBill");
        fragmentTransaction.commit();

    }
    public void handleDeleteBill(Bill bill){
        showDialog(bill);
    }
    public void showDialog(Bill bill){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Setting Alert Dialog Title
        alertDialogBuilder.setTitle("Confirm");
                // Icon Of Alert Dialog
                // Setting Alert Dialog Message
                alertDialogBuilder.setMessage("Do you want to delete this item?");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        CheckedinController.deleteBillOnFireBase(getActivity(),bill);
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
    public void setUpdateTime(){
        isUpdate = true;
        listDataCheckedinAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, 60000);
                setUpdateTime();
            }
        }, 60000);
        super.onResume();
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}