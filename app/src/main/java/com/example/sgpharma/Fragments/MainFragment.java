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
import com.example.sgpharma.databinding.FragmentMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainFragment extends Fragment {



    public MainFragment() {
        // Required empty public constructor
    }


   FragmentMainBinding binding;
    FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentMainBinding.inflate(inflater,container,false);
        auth= FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null){
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new LoginFragment()).commit();
        }
        binding.goToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new AccountFragment()).commit();
            }
        });
        binding.items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),MainActivity.class));
            }
        });
        return binding.getRoot();
    }
}