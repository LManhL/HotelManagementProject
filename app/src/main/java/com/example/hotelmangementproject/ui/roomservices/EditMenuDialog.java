package com.example.hotelmangementproject.ui.roomservices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.controllers.roomservicescontroller.EditMenuDialogController;
import com.example.hotelmangementproject.interfaces.IClickItemListMenuListener;
import com.example.hotelmangementproject.adapters.roomservicesAdapter.ListMenuAdapter;
import com.example.hotelmangementproject.models.MenuBill;

import java.util.ArrayList;
import java.util.List;

public class EditMenuDialog extends DialogFragment implements View.OnClickListener {

    private Callback callback;
    private ListMenuAdapter listMenuAdapter;
    private List<MenuBill> listMenuBill;

    private RecyclerView recyclerView;
    private RoomServiceViewModel model;

    static EditMenuDialog newInstance() {
        return new EditMenuDialog();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_rs_choose_menu, container, false);
        TextView action = view.findViewById(R.id.fullscreen_dialog_action);
        model = new ViewModelProvider(requireActivity()).get(RoomServiceViewModel.class);


        recyclerView = view.findViewById(R.id.rec_fragment_listmenu_edit_checkedin);
        listMenuBill = new ArrayList<MenuBill>();
        listMenuAdapter = new ListMenuAdapter(getActivity(), listMenuBill, new IClickItemListMenuListener() {
            @Override
            public void onClickAdd(MenuBill menuBill) {
                handleClickAdd(menuBill);
            }

            @Override
            public void onClickMinus(MenuBill menuBill) {
                handleClickMinus(menuBill);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(listMenuAdapter);

        EditMenuDialogController.getListRoomFromFireBase(listMenuBill, model.getListMenuBill().getValue(),listMenuAdapter);

        action.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.fullscreen_dialog_action:
                callback.onActionClick();
                dismiss();
                break;

        }

    }
    public void handleClickAdd(MenuBill menuBill){
        model.addToListMenuBill(menuBill,1);
    }
    public void handleClickMinus(MenuBill menuBill){
        model.addToListMenuBill(menuBill,-1);
    }

    public interface Callback {

        void onActionClick();

    }

}