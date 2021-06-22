package com.example.sgpharma.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sgpharma.Adapters.ItemAdapters;
import com.example.sgpharma.Fragments.AccountFragment;
import com.example.sgpharma.Fragments.OtpFragment;
import com.example.sgpharma.Models.ItemMainModel;
import com.example.sgpharma.Models.ItemModels;
import com.example.sgpharma.Models.ItemPicModel;
import com.example.sgpharma.Models.Users;
import com.example.sgpharma.R;
import com.example.sgpharma.databinding.ActivityMainBinding;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<ItemModels> list=new ArrayList<>();
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    ItemAdapters itemAdapter; //=new ItemAdapters(MainActivity.this,list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,InputItemActivity.class));
            }
        });

            //binding.itemRecyclerView.showShimmerAdapter();
            database =FirebaseDatabase.getInstance();
            auth=FirebaseAuth.getInstance();
            storage=FirebaseStorage.getInstance();
            database.getReference().child("Items").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    list.clear();
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        ItemModels item =dataSnapshot.getValue(ItemModels.class);
                        list.add(item);

                    }
                    //binding.itemRecyclerView.hideShimmerAdapter();
                    itemAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        itemAdapter =new ItemAdapters(MainActivity.this,list);

        binding.itemRecyclerView.setAdapter(itemAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.itemRecyclerView.setLayoutManager(layoutManager);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemAdapter.getFilter().filter(newText);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}