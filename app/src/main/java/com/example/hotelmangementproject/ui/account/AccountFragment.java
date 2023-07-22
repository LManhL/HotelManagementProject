package com.example.hotelmangementproject.ui.account;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmangementproject.databinding.FragAcMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AccountFragment extends Fragment {

    private FragAcMainBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragAcMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.btnChangePasswordAcMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidate()){
                    changePassword();
                }
            }
        });
        return root;
    }

    private boolean checkValidate(){
        if(binding.edtNewpasswordAcMain.getText().toString().isEmpty()){
            binding.edtNewpasswordAcMain.setError("Required");
            return false;
        }
        if(binding.edtConfirmNewpasswordAcMain.getText().toString().isEmpty()){
            binding.edtConfirmNewpasswordAcMain.setError("Required");
            return false;
        }
        if(!binding.edtConfirmNewpasswordAcMain.getText().toString().equals(binding.edtNewpasswordAcMain.getText().toString())){
            Toast.makeText(getActivity(), "Two passwords are not the same", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void changePassword(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(binding.edtNewpasswordAcMain.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("Change password","Success");
                }
                binding.edtConfirmNewpasswordAcMain.setText("");
                binding.edtNewpasswordAcMain.setText("");
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}