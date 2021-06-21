package com.example.sgpharma.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sgpharma.Activities.MainActivity;
import com.example.sgpharma.Models.ItemMainModel;
import com.example.sgpharma.Models.ItemModels;
import com.example.sgpharma.Models.ItemPicModel;
import com.example.sgpharma.R;
import com.example.sgpharma.databinding.ItemSampleBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapters extends RecyclerView.Adapter<ItemAdapters.ViewHolder> {
    Context context;
    ArrayList<ItemModels> items;

    public ItemAdapters(Context context, ArrayList<ItemModels> items) {
        this.context = context;
        this.items =items;
    }

    @NonNull
    @Override
    public ItemAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_sample,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapters.ViewHolder holder, int position) {
        ItemModels item=items.get(position);


        holder.binding.itemName.setText(item.getItemName());
        holder.binding.netRateValueTv.setText(item.getItemRate());
        holder.binding.downRateValueTv.setText(item.getItemRateInDown());
        holder.binding.lessRateValueTv.setText(item.getItemRateInLess());
        holder.binding.ptrLessRateValueTv.setText(item.getItemRateInPtr());
        holder.binding.mrpValueTv.setText(item.getItemMrp());
        holder.binding.companyValueTv.setText(item.getItemCompany());
        holder.binding.expairyDateValueTv.setText(item.getItemExpairyDate());
//        ArrayList<ItemPicModel> pics= (ArrayList<ItemPicModel>) item.getItemPic();
//        ItemPicAdapters adapter=new ItemPicAdapters(context,pics);
//        holder.binding.itemPicRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        //holder.binding.itemPicRecyclerView.setAdapter(adapter);
        Picasso.get().load(item.getItemPic()).placeholder(R.drawable.ic_user).into(holder.binding.itemPics);
        holder.binding.updateByDate.setText(item.getUpdatedDate()+" By "+item.getUpdatedBy());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Dialog dialog=new Dialog(context);
               dialog.setContentView(R.layout.item_input_sample);
//               if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
//                   dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.
//                           bg_login_page));
//               }
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations=R.style.animation;

                // set data in dialog box;
                ImageView imageView=dialog.findViewById(R.id.itemPics1);
                Picasso.get().load(item.getItemPic()).placeholder(R.drawable.ic_user).into(imageView);
                EditText name=dialog.findViewById(R.id.itemNamesEt);
                name.setText(item.getItemName());
                EditText mrp=dialog.findViewById(R.id.mrpEt);
                mrp.setText(item.getItemMrp());
                EditText expairy=dialog.findViewById(R.id.expairyEt);
                expairy.setText(item.getItemExpairyDate());

                EditText company=dialog.findViewById(R.id.companyEt);
                company.setText(item.getItemCompany());

                EditText net=dialog.findViewById(R.id.netRateEt);
                net.setText(item.getItemRate());


                Button update=dialog.findViewById(R.id.updateBtn);
                Button delete=dialog.findViewById(R.id.deleteBtn);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Update Btn", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Delete Btn", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSampleBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemSampleBinding.bind(itemView);
        }
    }
}