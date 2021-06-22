package com.example.sgpharma.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sgpharma.Adapters.LedgerAdapters;
import com.example.sgpharma.Classes.Helper;
import com.example.sgpharma.Models.ItemModels;
import com.example.sgpharma.Models.LedgerModel;
import com.example.sgpharma.R;
import com.example.sgpharma.databinding.ActivityLedgerBinding;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LedgerActivity extends AppCompatActivity {
    ActivityLedgerBinding binding;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImage;
    String imageUri;
    ProgressDialog progressDialog;
    ArrayList<LedgerModel> ledgers=new ArrayList<>();
    LedgerAdapters adapters;
    ImageView ledgerPic;
    Float totalBalance=00.00f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLedgerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapters =new LedgerAdapters(LedgerActivity.this,ledgers);
        binding.ledgerRview.setAdapter(adapters);
        LinearLayoutManager layoutManager=new LinearLayoutManager(LedgerActivity.this);
        binding.ledgerRview.setLayoutManager(layoutManager);
        database =FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        getSupportActionBar().hide();
        progressDialog=new ProgressDialog(LedgerActivity.this);
        progressDialog.setMessage("Adding Ledger.......");
        progressDialog.setCancelable(false);
        database.getReference().child("Ledgers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ledgers.clear();
                totalBalance=0.0f;
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    LedgerModel ledger =dataSnapshot.getValue(LedgerModel.class);
                    ledgers.add(ledger);
                    totalBalance +=Float.parseFloat(ledger.getLedgerBalance());
                    String totalRs="Rs.: ";
                    totalRs+=  String.valueOf(totalBalance);
                    binding.totalBalanceLedgerBtn.setText(totalRs);

                }
                //binding.itemRecyclerView.hideShimmerAdapter();
                adapters.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.addNewLedgerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(LedgerActivity.this);
                dialog.setContentView(R.layout.ledger_input_sample);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
               ledgerPic=dialog.findViewById(R.id.ledgerPicIP);
                EditText ledgerName=dialog.findViewById(R.id.ledgerNameEdIP);
                EditText ledgerMobile=dialog.findViewById(R.id.ledgerMobileEdIP);
                EditText ledgerBalance=dialog.findViewById(R.id.ledgerOpeningBalEdIP);
                Button cancel=dialog.findViewById(R.id.cancelLedgerBtn);
                Button addLedger=dialog.findViewById(R.id.addLedgerBtn);
                ledgerPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent,37);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(LedgerActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                addLedger.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        LedgerModel ledgerModel=new LedgerModel();
                        ledgerModel.setLedgerBalance(ledgerBalance.getText().toString());
                        String ledgerId = database.getReference().push().getKey();
                        ledgerModel.setLedgerId(ledgerId);
                        ledgerModel.setLedgerName(ledgerName.getText().toString());
                        ledgerModel.setLedgerMobile(ledgerMobile.getText().toString());

                        if (selectedImage != null) {
                            StorageReference reference = storage.getReference().child("LedgerPic").
                                    child(ledgerId);
                            reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                imageUri = uri.toString();
                                                database.getReference().child("Ledgers").child(ledgerId).
                                                        child("ledgerProfilePic").setValue(imageUri);
                                                Toast.makeText(LedgerActivity.this, "Ledger Added", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        Helper helper = new Helper();

                                        String updateBy = "Vikram";
                                        ledgerModel.setUpdatedBy(updateBy);
                                        ledgerModel.setUpdatedDate("Last Update: " + helper.currentDate() + ", ");
                                        database.getReference().child("Ledgers").child(ledgerId).setValue(ledgerModel);
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                        }
                        dialog.dismiss();

                    }

                });
                dialog.show();

            }
        });




    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(data.getData()!=null){
                ledgerPic.setImageURI(data.getData());
                selectedImage=data.getData();
            }
        }
    }
}