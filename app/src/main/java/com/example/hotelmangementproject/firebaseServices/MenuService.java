package com.example.hotelmangementproject.firebaseServices;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.adapters.roomservicesAdapter.ListMenuAdapter;
import com.example.hotelmangementproject.models.Menu;
import com.example.hotelmangementproject.models.MenuBill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MenuService {
    public static void getListMenuBill(List<MenuBill> listMenuBill, List<MenuBill> prevList, RecyclerView.Adapter listMenuAdapter){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("menu");
        Query query = mDatabase.orderByChild("name");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listMenuBill != null){
                    listMenuBill.clear();
                }
                for (DataSnapshot menuSnapshot: dataSnapshot.getChildren()) {
                    Menu menu = menuSnapshot.getValue(Menu.class);
                    int count = 0;
                    if(prevList != null){
                        for(MenuBill menuBill : prevList){
                            if(menuBill.getId().equals(menu.getId())){
                                count = menuBill.getCount();
                            }
                        }
                    }
                    MenuBill menuBill = new MenuBill(menu,count);
                    listMenuBill.add(menuBill);
                    listMenuAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public static void getListMenu(List<Menu> listMenu, RecyclerView.Adapter listMenuAdapter){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("menu");
        Query query = mDatabase.orderByChild("name");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listMenu != null){
                    listMenu.clear();
                }
                for (DataSnapshot menuSnapshot: dataSnapshot.getChildren()) {
                    Menu menu = menuSnapshot.getValue(Menu.class);
                    listMenu.add(menu);
                    listMenuAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public static void updateMenu(Menu menu){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("menu");
        mDatabase.child(menu.getId()).setValue(menu);
    }
    public static void deleteMenu(Menu menu){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("menu");
        mDatabase.child(menu.getId()).removeValue();
    }
    public static void createMenu(Menu menu){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("menu").push();
        String key = mDatabase.getKey();
        menu.setId(key);
        mDatabase.setValue(menu);
    }
}
