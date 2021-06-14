package com.example.sgpharma.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sgpharma.Activities.LoginActivity;
import com.example.sgpharma.Activities.MainActivity;
import com.example.sgpharma.R;
import com.example.sgpharma.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {



    public LoginFragment() {
        // Required empty public constructor
    }

    FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentLoginBinding.inflate(inflater,container,false);

        binding.btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo=binding.etPhone.getText().toString();
                if(phoneNo.isEmpty()||phoneNo.length()!=10){
                    binding.etPhone.setError("phone must be 10 digit");
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("phoneNo",phoneNo);
                OtpFragment fragment=new OtpFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();

            }
        });
        return binding.getRoot();
    }
}