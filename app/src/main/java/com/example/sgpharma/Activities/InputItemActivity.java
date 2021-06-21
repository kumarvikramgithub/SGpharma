package com.example.sgpharma.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sgpharma.Models.ItemModels;
import com.example.sgpharma.Models.ItemPicModel;
import com.example.sgpharma.Models.Users;
import com.example.sgpharma.databinding.ActivityInputItemBinding;
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

public class InputItemActivity extends AppCompatActivity {
    ActivityInputItemBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    Uri selectedImage;
    String imageUri;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityInputItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        getSupportActionBar().hide();
        dialog=new ProgressDialog(InputItemActivity.this);
        dialog.setMessage("Adding Item.......");
        dialog.setCancelable(false);
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InputItemActivity.this,MainActivity.class));
            }
        });
        binding.itemPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,37);
            }
        });
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAddItemFunction();
            }
        });
    }

    private void callAddItemFunction() {
        dialog.show();
        String itemName =binding.itemNames.getText().toString();
        String itemMrp =binding.mrp.getText().toString();
        String itemExpairy =binding.expairy.getText().toString();
        String itemCompany =binding.company.getText().toString();
        String itemNetRate =binding.netRate.getText().toString();
        Float mrpFloat=Float.parseFloat(itemMrp);
        Float netRateFloat =Float.parseFloat(itemNetRate);

//                Down rate Calculation

        Float mrpFloat23less =mrpFloat -mrpFloat*23/100;
        Float lessToNetRate =mrpFloat23less-netRateFloat;
        Float downRate=lessToNetRate*100/mrpFloat23less;

//                Less Rate from Mrp calculation
        lessToNetRate=mrpFloat-netRateFloat;
        Float lessRate = lessToNetRate*100/mrpFloat;

//                Ptr Less Calculation
        Float tradeLessAmount=mrpFloat -mrpFloat*28.57f/100;
        Float tradeLessAmountplusless=netRateFloat*100/112;
        tradeLessAmountplusless=tradeLessAmount-tradeLessAmountplusless;
        tradeLessAmountplusless*=100;
        tradeLessAmountplusless/=tradeLessAmount;

        String itemDownRate=String.valueOf(downRate);
        String itemLessRate=String.valueOf(lessRate);
        String itemPtrLessRate=String.valueOf(tradeLessAmountplusless);
        ItemModels item= new ItemModels();
        item.setItemName(itemName);
        item.setItemMrp(itemMrp);
        item.setItemCompany(itemCompany);
        item.setItemExpairyDate(itemExpairy);
        item.setItemRate(itemNetRate);
        item.setItemRateInDown(itemDownRate);
        item.setItemRateInLess(itemLessRate);
        item.setItemRateInPtr(itemPtrLessRate);
        //String imageId=database.getReference().push().getKey();
        String itemId=database.getReference().push().getKey();
        if(selectedImage!=null){
            StorageReference reference=storage.getReference().child("ItemPic").
                    child(itemId);
            reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageUri=uri.toString();
                                database.getReference().child("Items").child(itemId).
                                        child("itemPic").setValue(imageUri);
                                Toast.makeText(InputItemActivity.this, "Item Added", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Calendar calendar =Calendar.getInstance();
                        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
                        String dateOfUpdation = "Last Update: "+currentDate+", ";

                        String updateBy = "Vikram";
                        item.setUpdatedBy(updateBy);
                        item.setItemId(itemId);
                        item.setUpdatedDate(dateOfUpdation);
                        database.getReference().child("Items").child(itemId).setValue(item);
                        dialog.dismiss();
                        startActivity(new Intent(InputItemActivity.this,MainActivity.class));
                    }
                }
            });
        }
//        if(selectedImage!=null) {
//            StorageReference reference = storage.getReference().child("ItemPic").
//                    child(itemId);
//            reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                imageUri = uri.toString();
//                                database.getReference().child("Items").child(itemId).
//                                        child("itemPic").setValue(imageUri);
//                                Toast.makeText(InputItemActivity.this, "Image Uri", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        //String updatedByName="";
//                        database.getReference().child("User").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
//                                    Users user = dataSnapshot.getValue(Users.class);
//                                    if (auth.getCurrentUser().getPhoneNumber()==user.getPhoneNumber()){
//                                        item.setUpdatedBy(user.getName());
//                                    }
//
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                        String dateOfUpdation = "13/06/2021";
//
//                        item.setUpdatedDate(dateOfUpdation);
//                        database.getReference().child("Items").child(itemId).setValue(item);
//                    }
//                }
//            });
//
//
//        }else{
//            Toast.makeText(InputItemActivity.this, "Image Not selected", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(data.getData()!=null){
                binding.itemPic1.setImageURI(data.getData());
                selectedImage=data.getData();
            }
        }
    }
}