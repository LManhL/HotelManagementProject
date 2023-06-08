package com.example.hotelmangementproject.ui.roomservices;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelmangementproject.models.Bill;
import com.example.hotelmangementproject.models.MenuBill;
import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.models.RoomBill;

import java.util.ArrayList;
import java.util.List;

public class RoomServiceViewModel extends ViewModel {

    private MutableLiveData<Room> room;
    private MutableLiveData<RoomBill> roomBill;

    private MutableLiveData<List<MenuBill>> listMenuBill;
    private MutableLiveData<Bill> bill;
    public RoomServiceViewModel(){
        room = new MutableLiveData<Room>();
        roomBill = new MutableLiveData<RoomBill>();
        listMenuBill = new MutableLiveData<List<MenuBill>>();
        bill = new MutableLiveData<Bill>();
    }
    public void changeBill(){
        RoomBill roomBillValue = getRoomBill().getValue();
        Bill billValue = getBill().getValue();
        List<RoomBill> roomBillList = billValue.getListRoomBill();
        for (int i = 0; i < roomBillList.size(); i++) {
            RoomBill item = roomBillList.get(i);
            if (item.getId().equals(roomBillValue.getId())) {
                roomBillList.set(i, new RoomBill(roomBillValue));
                break;
            }
        }
        billValue.caculateBill();
        setBill(billValue);
    }
    public LiveData<Bill> getBill(){
        return bill;
    }
    public void setBill(Bill value){
        bill.setValue(value);
    }
    public LiveData<Room> getRoom(){
        return room;
    }
    public void selectedRoom(Room item){
        room.setValue(item);
    }

    public void selectedRoomBill(RoomBill item){
        roomBill.setValue(item);
        listMenuBill.setValue(item.getListMenuBill());
    }
    public void setNewRoomBill(int extraChild, int extraAdult,String note){
        RoomBill value = new RoomBill(roomBill.getValue(), extraChild, extraAdult,note, listMenuBill.getValue());
        value.caculateRoomCost();
        roomBill.setValue(value);
    }
    public LiveData<RoomBill> getRoomBill(){
        return roomBill;
    }

    public void addToListMenuBill(MenuBill menuBill, int type){
        List<MenuBill> list = new ArrayList<MenuBill>();
        if(listMenuBill.getValue() != null){
            list = listMenuBill.getValue();
        }
        boolean check = false;

        if(list != null){
            for(MenuBill value : list){
                if(menuBill.getId().equals(value.getId())){
                    int count = value.getCount();
                    count = Math.max(count+type,0);
                    if(count <= 0){
                        list.remove(value);
                    }
                    else value.setCount(count);
                    check = true;
                    break;
                }
            }
        }
        if(check == false){
            int count = menuBill.getCount();
            count = Math.max(count+type,0);
            menuBill.setCount(count);
            if(count > 0) list.add(menuBill);
        }
        listMenuBill.setValue(list);
    }
    public LiveData<List<MenuBill>> getListMenuBill(){
        return listMenuBill;
    }
    public void setListMenuBill(List<MenuBill> listMenuBill){
        this.listMenuBill.setValue(listMenuBill);
    }
}
