package com.example.sgpharma.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.sgpharma.Activities.MainActivity;
import com.example.sgpharma.R;
import com.example.sgpharma.databinding.FragmentLoginBinding;
import com.example.sgpharma.databinding.FragmentOtpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;


public class OtpFragment extends Fragment {

    public OtpFragment() {
        // Required empty public constructor
    }

    FragmentOtpBinding binding;
    String phoneNo;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String verificationId;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentOtpBinding.inflate(inflater,container,false);
        binding.etOtp.requestFocus();

        //phoneNo=getActivity().getIntent().getStringExtra("phoneNo").toString();
        Bundle bundle =this.getArguments();
        phoneNo=bundle.getString("phoneNo");
        binding.verifyPhone.setText("Verify +91"+phoneNo);
        auth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();

        dialog=new ProgressDialog(getContext());
        dialog.setMessage("Sending OTP...");
        dialog.setCancelable(false);
        dialog.show();

        PhoneAuthOptions options= PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91"+phoneNo)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(getActivity())
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationId=s;
                        dialog.dismiss();
//                        InputMethodManager imm=(InputMethodManager)getSystemService(getActivity().INPUT_METHOD_SERVICE);
//                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,2);
//                        binding.etOtp.requestFocus();
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp=binding.etOtp.getText().toString();
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,otp);
                auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new AccountFragment()).commit();
                        }else{
                            Toast.makeText(getContext(), "Otp fail", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });
        return binding.getRoot();
    }
}