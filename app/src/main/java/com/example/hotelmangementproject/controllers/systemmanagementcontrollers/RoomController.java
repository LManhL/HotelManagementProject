package com.example.hotelmangementproject.controllers.systemmanagementcontrollers;

import android.content.Context;
import android.net.Uri;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.MainActivity;
import com.example.hotelmangementproject.adapters.systemmanagementAdapter.RoomAdapter;
import com.example.hotelmangementproject.adapters.systemmanagementAdapter.RoomTypeAdapter;
import com.example.hotelmangementproject.firebaseServices.RoomService;
import com.example.hotelmangementproject.firebaseServices.RoomTypeService;
import com.example.hotelmangementproject.models.Room;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class RoomController {
    public static void getListRoom(List<Room> listRoom, RecyclerView.Adapter roomAdapter){
        RoomService.getListRoom(listRoom, roomAdapter);
    }
    public static void getListRoomType(List<String> listRoomType){
        RoomTypeService.getListRoomType(listRoomType);
    }
    public static void createRoom(Context context, Room room){
        RoomService.createRoom(room,context);
    }
    public static void addImage(Context context, Room room){
        RoomService.uploadImageAndGetURL(context, room.getListImage().size() - 1, room, new RoomService.CallBackUpload() {
            @Override
            public void callBack(Room room, int position, Uri uri) {
                room.getListImage().set(position,String.valueOf(uri));
                RoomService.updateListImage(room);
            }
        });
    }
    public static void removeImage(Context context, Room room){
        RoomService.deleteFileOnStorage(room, context, new RoomService.CallBackDelete() {
            @Override
            public void callBack(Room room) {
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().
                        getReference("room").child(room.getId()).
                        child("listImage");
                mDatabase.setValue(room.getListImage());
            }
        });
    }
    public static void updateRoom(Context context, Room room){
        RoomService.updateRoom(context,room);
    }
    public static void deleteRoom(Room room, Context context){
        RoomService.deleteRoom(room, context);
    }
}
