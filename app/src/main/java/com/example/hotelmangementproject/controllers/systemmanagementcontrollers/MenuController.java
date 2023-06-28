package com.example.hotelmangementproject.controllers.systemmanagementcontrollers;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.firebaseServices.MenuService;
import com.example.hotelmangementproject.models.Menu;

import java.util.List;

public class MenuController {
    public static void getListMenu(List<Menu> listMenu, RecyclerView.Adapter adapter){
        MenuService.getListMenu(listMenu,adapter);
    }
    public static void updateMenu(Menu menu){
        MenuService.updateMenu(menu);
    }
    public static void createMenu(Menu menu){

    }
    public static void deleteMenu(Menu menu){
        MenuService.deleteMenu(menu);
    }
}
