package com.example.sgpharma.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.sgpharma.Activities.LoginActivity;
import com.example.sgpharma.Activities.MainActivity;
import com.example.sgpharma.Models.Users;
import com.example.sgpharma.R;
import com.example.sgpharma.databinding.FragmentAccountBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AccountFragment extends Fragment {


    public AccountFragment() {
        // Required empty public constructor
    }

    FragmentAccountBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    Uri selectedImage;
    String imageUri;
    ProgressDialog dialog;
    ProgressDialog dialogLoad;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAccountBinding.inflate(inflater,container,false);
        auth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        storage= FirebaseStorage.getInstance();
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        dialog=new ProgressDialog(getContext());
        dialog.setMessage("Updating Account.....");
        dialog.setCancelable(false);

        dialogLoad=new ProgressDialog(getContext());
        dialogLoad.setMessage("Please Wait..");
        dialogLoad.setCancelable(false);
        dialogLoad.show();
        database.getReference().child("User").child(FirebaseAuth.getInstance().getUid()).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren() ){
                            Users users = snapshot.getValue(Users.class);
                            Picasso.get().load(users.getProfileImage()).placeholder(R.drawable.ic_user).into(binding.profileImage);
                            binding.etName.setText(users.getName());
                            binding.etEmail.setText(users.getEmail());
                            binding.etAboutUs.setText(users.getAboutUs());
                            //binding.laguageSpiner.set

                        }
                        dialogLoad.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
        dialog.setCancelable(false);
        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,37);
            }
        });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.show();
                Users user=new Users();
                user.setUserId(auth.getCurrentUser().getUid());
                user.setName(binding.etName.getText().toString());
                user.setEmail(binding.etEmail.getText().toString());
                user.setPhoneNumber(auth.getCurrentUser().getPhoneNumber());
                user.setAboutUs(binding.etAboutUs.getText().toString());
                if(selectedImage!=null){
                    StorageReference reference=storage.getReference().child("ProfileImage").
                            child(auth.getCurrentUser().getUid());
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imageUri=uri.toString();
                                        database.getReference().child("User").child(auth.getCurrentUser().getUid()).
                                                child("profileImage").setValue(imageUri);
                                        Toast.makeText(getContext(), "Image Uri", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }

                database.getReference().child("User").child(auth.getCurrentUser().getUid()).setValue(user);
                startActivity(new Intent(getContext(), MainActivity.class));
                dialog.dismiss();
            }
        });



        return binding.getRoot();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(data.getData()!=null){
                binding.profileImage.setImageURI(data.getData());
                selectedImage=data.getData();
            }
        }
    }
}