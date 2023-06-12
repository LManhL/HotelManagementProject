package com.example.hotelmangementproject.ui.systemmanagement;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.PathDashPathEffect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.hotelmangementproject.R;
import com.example.hotelmangementproject.adapters.roomservicesAdapter.ImageSlideAdapter;
import com.example.hotelmangementproject.controllers.systemmanagementcontrollers.RoomController;
import com.example.hotelmangementproject.databinding.FragSmRoomDetailBinding;
import com.example.hotelmangementproject.firebaseServices.RoomService;
import com.example.hotelmangementproject.models.CalMoney;
import com.example.hotelmangementproject.models.Room;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class RoomDetailFragment extends Fragment {
    private FragSmRoomDetailBinding binding;
    private SystemManagementViewModel model;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    ImageSlideAdapter imageSlideAdapter;
    final int REQUEST_CODE = 123;
    ArrayAdapter<String> adapter;
    AutoCompleteTextView autoCompleteTextView;
    List<String> listRoomType;
    List<String> originListImage;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(SystemManagementViewModel.class);

        binding = FragSmRoomDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewPager = binding.viewpagerFragSmRoomdetail;
        circleIndicator = binding.cirleindicatorFragSmRoomdetail;
        listRoomType = new ArrayList<>();
        originListImage = new ArrayList<>();
        for(String val : model.getRoom().getValue().getListImage()){
            originListImage.add(val);
        }

        imageSlideAdapter = new ImageSlideAdapter(getActivity(), model.getRoom().getValue().getListImage());

        viewPager.setAdapter(imageSlideAdapter);

        circleIndicator.setViewPager(viewPager);
        imageSlideAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, listRoomType);
        // Lấy tham chiếu tới AutoCompleteTextView
        autoCompleteTextView = binding.autoCompleteFragSmRoomdetail;
        // Đặt adapter cho AutoCompleteTextView
        autoCompleteTextView.setAdapter(adapter);

        model.getRoom().observe(this, room -> {
            adapter.notifyDataSetChanged();
            binding.edtNameFragsmRoomdetail.setText(room.getName());
            binding.edtDescriptionFragSmRoomdetail.setText(room.getDescription());
            autoCompleteTextView.setAdapter(null);
            autoCompleteTextView.setText(room.getRoomTypes());
            autoCompleteTextView.setAdapter(adapter);
            imageSlideAdapter.notifyDataSetChanged();
        });
        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.showDropDown();
            }
        });

        binding.btnUploadFragSmRoomdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        binding.btnRemoveFragSmRoomdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getRoom().getValue().getListImage().size() >= 1){
                    int currentPosition = viewPager.getCurrentItem();
                    int lastPosition = model.getRoom().getValue().getListImage().size() - 1;
                    model.getRoom().getValue().getListImage().remove(lastPosition);
                    if(!model.getRoom().getValue().getId().equals("0")){
                        RoomController.removeImage(getActivity(), model.getRoom().getValue());
                    }
                    imageSlideAdapter.notifyDataSetChanged();

                    if (currentPosition == lastPosition ) {
                        // Nếu đang ở trang cuối cùng, chuyển đổi ViewPager đến trang trước đó
                        viewPager.setCurrentItem(currentPosition - 1);
                        viewPager.setAdapter(imageSlideAdapter);
                    }

                }
            }
        });

        binding.floatButtonFragSmRoomdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Room room = model.getRoom().getValue();
                room.setName(binding.edtNameFragsmRoomdetail.getText().toString());
                room.setRoomTypes(binding.autoCompleteFragSmRoomdetail.getText().toString());
                room.setDescription(binding.edtDescriptionFragSmRoomdetail.getText().toString());
                if(checkValid()){
                    if(room.getId().equals("0")){
                        RoomController.createRoom(getActivity(), room);
                    }
                    else{
                        RoomController.updateRoom(getActivity(),room);
                    }
                    navToRoom();
                }
            }
        });

        RoomController.getListRoomType(listRoomType);

        return root;
    }
    public boolean checkValid(){
        if(binding.edtNameFragsmRoomdetail.getText().toString().isEmpty()){
            binding.edtNameFragsmRoomdetail.setError("Required");
            return false;
        }
        if(binding.autoCompleteFragSmRoomdetail.getText().toString().isEmpty()){
            binding.autoCompleteFragSmRoomdetail.setError("Required");
            return false;
        }
        return true;
    }
    public void navToRoom(){
        NavController navController = Navigation.findNavController(getView());
        navController.popBackStack();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null && requestCode == REQUEST_CODE){
            Uri selectedImage = data.getData();
            model.getRoom().getValue().getListImage().add(String.valueOf(selectedImage));
            if(!model.getRoom().getValue().getId().equals("0")){
                RoomController.addImage(getActivity(),model.getRoom().getValue());
            }
            imageSlideAdapter.notifyDataSetChanged();
        }

    }
}
