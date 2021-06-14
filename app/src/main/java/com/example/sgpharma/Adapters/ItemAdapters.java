package com.example.sgpharma.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
