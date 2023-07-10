package com.example.hotelmangementproject.firebaseServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmangementproject.MainActivity;
import com.example.hotelmangementproject.adapters.roomservicesAdapter.RoomAvailableAdapter;
import com.example.hotelmangementproject.models.CalMoney;
import com.example.hotelmangementproject.models.Room;
import com.example.hotelmangementproject.models.RoomTypeBooking;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomService {
    public static void getListRoom(List<Room> listRoom, RecyclerView.Adapter roomAvailableAdapter){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("room");
        Query query = mDatabase.orderByChild("roomState").equalTo(Room.STATE_AVAILABLE);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listRoom != null){
                    listRoom.clear();
                }
                for (DataSnapshot roomSnapshot: dataSnapshot.getChildren()) {
                    Log.d("key",roomSnapshot.getKey());
                    Room room = roomSnapshot.getValue(Room.class);
                    listRoom.add(room);
                    roomAvailableAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public static void getListRoom(List<Room> listRoom, CallBackGetListRoom callBackGetListRoom){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("room");
        Query query = mDatabase.orderByChild("roomState");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listRoom != null){
                    listRoom.clear();
                }
                for (DataSnapshot roomSnapshot: dataSnapshot.getChildren()) {
                    Log.d("key",roomSnapshot.getKey());
                    Room room = roomSnapshot.getValue(Room.class);
                    listRoom.add(room);
                }
                callBackGetListRoom.callBack();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public static void changeRoomState(Room room, int state){
        DatabaseReference mDatabase  = FirebaseDatabase.getInstance().getReference(MainActivity.UID);
        mDatabase.child("room").child(room.getId()).child("roomState").setValue(state);
    }
    public static void createRoom(Room room, Context context){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("room");
        DatabaseReference pushedPostRef = mDatabase.push();
        String id = pushedPostRef.getKey();
        if(room.getId().equals("0")){
            room.setId(id);
            PriceRuleService.findPriceRule(room.getRoomTypes(), new PriceRuleService.CallBackCalMoney() {
                @Override
                public void callBackFunc(List<CalMoney> list) {
                    room.setCalMoney(list.get(0));
                    for(int i = 0; i < room.getListImage().size(); i++){
                        uploadImageAndGetURL( context, i, room, new CallBackUpload() {
                            @Override
                            public void callBack(Room room, int position, Uri uri) {
                                room.getListImage().set(position,String.valueOf(uri));
                                if(position == room.getListImage().size()-1){
                                    mDatabase.child(id).setValue(room);
                                }
                            }
                        });
                    }
                }
            });
        }
    }
    public static void deleteFileOnStorage(Room room, Context context, CallBackDelete callBackDelete){
        ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        // Create a storage reference from our app
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        // Create a reference to the file to delete
        StorageReference desertRef = storageRef.child("images/"+"room/"+room.getId()+"/"+String.valueOf(room.getListImage().size()));

        // Delete the file
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callBackDelete.callBack(room);
                progress.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                progress.dismiss();
            }
        });
        progress.show();
    }
    public static void uploadImageAndGetURL(Context context,int position, Room room, CallBackUpload callBackUpload){
        ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

        Uri selectedImage = Uri.parse(room.getListImage().get(position));
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("images/"+"room/"+room.getId()+"/"+position);
        UploadTask uploadTask = imageRef.putFile(selectedImage);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("Error","File to upload file to firebase storage");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        progress.dismiss();
                        callBackUpload.callBack(room, position, uri);
                    }
                });
            }
        });
        progress.show();
    }
    public static void updateListImage(Room room){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("room");
        mDatabase.child(room.getId()).child("listImage").setValue(room.getListImage());
    }
    public static void updateRoom(Context context, Room room){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("room");
        mDatabase.child(room.getId()).setValue(room);
    }
    public static void deleteRoom(Room room, Context context){
        for(int i = room.getListImage().size()-1; i >= 0 ; i--){
            room.getListImage().remove(room.getListImage().size()-1);
            int finalI = i;
            deleteFileOnStorage(room, context, new CallBackDelete() {
                @Override
                public void callBack(Room room) {
                    if(finalI == 0){
                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("room");
                        mDatabase.child(room.getId()).removeValue();
                    }
                }
            });
        }
    }
    public static void getRoomListOfType(List<Room> roomList, RecyclerView.Adapter adapter, RoomTypeBooking roomTypeBooking){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.UID).child("room");
        Query query = mDatabase.orderByChild("roomState").equalTo(Room.STATE_AVAILABLE);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(roomList != null){
                    roomList.clear();
                }
                for (DataSnapshot roomSnapshot: dataSnapshot.getChildren()) {
                    Room room = roomSnapshot.getValue(Room.class);
                    if(room.getRoomTypes().equals(roomTypeBooking.getType())){
                        boolean check = true;
                        if(roomTypeBooking.getRoomList() != null){
                            for(Room value : roomTypeBooking.getRoomList()){
                                if(room.getName().equals(value.getName())){
                                    check = false;
                                    break;
                                }
                            }
                        }
                        if(check == true){
                            roomList.add(room);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    public interface CallBackUpload{
        public void callBack(Room room, int position,Uri uri);
    }
    public interface CallBackDelete{
        public void callBack(Room room);
    }
    public interface CallBackGetListRoom{
        public void callBack();
    }
}
