package com.example.sgpharma.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sgpharma.Models.ItemModels;
import com.example.sgpharma.Models.ItemPicModel;
import com.example.sgpharma.R;
import com.example.sgpharma.databinding.ItemPicSampleBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemPicAdapters extends RecyclerView.Adapter<ItemPicAdapters.ViewHolder> {
        Context context;
        ArrayList<ItemPicModel> itemPics;

    public ItemPicAdapters(Context context, ArrayList<ItemPicModel> itemPics) {
        this.context = context;
        this.itemPics = itemPics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_pic_sample,parent,false);
        return new ItemPicAdapters.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemPicModel pic=itemPics.get(position);
        Picasso.get().load(pic.getItemPic()).placeholder(R.drawable.ic_user).into(holder.binding.itemPics);
    }

    @Override
    public int getItemCount() {
        return itemPics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemPicSampleBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemPicSampleBinding.bind(itemView);
        }
    }
}
