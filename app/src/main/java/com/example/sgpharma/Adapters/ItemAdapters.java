package com.example.sgpharma.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sgpharma.Classes.Helper;
import com.example.sgpharma.Models.ItemModels;
import com.example.sgpharma.R;
import com.example.sgpharma.databinding.ItemSampleBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ItemAdapters extends RecyclerView.Adapter<ItemAdapters.ViewHolder> implements Filterable {
    Context context;
    ArrayList<ItemModels> data;
    ArrayList<ItemModels> backup;
    public ItemAdapters(Context context, ArrayList<ItemModels> data) {
        this.context = context;
        this.data =data;
       backup=new ArrayList<>(data);
    }

    @NonNull
    @Override
    public ItemAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_sample,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapters.ViewHolder holder, int position) {
        ItemModels item=data.get(position);


        holder.binding.itemName.setText(item.getItemName());
        holder.binding.netRateValueTv.setText(item.getItemRate());
        holder.binding.downRateValueTv.setText(item.getItemRateInDown());
        holder.binding.lessRateValueTv.setText(item.getItemRateInLess());
        holder.binding.ptrLessRateValueTv.setText(item.getItemRateInPtr());
        holder.binding.mrpValueTv.setText(item.getItemMrp());
        holder.binding.companyValueTv.setText(item.getItemCompany());
        holder.binding.expairyDateValueTv.setText(item.getItemExpairyDate());
        Picasso.get().load(item.getItemPic()).placeholder(R.drawable.ic_user).into(holder.binding.itemPics);
        holder.binding.updateByDate.setText(item.getUpdatedDate()+" By "+item.getUpdatedBy());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Dialog dialog=new Dialog(context);
               dialog.setContentView(R.layout.item_input_sample);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                //dialog.setCancelable(false);
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

                // TextView updateBy = dialog.findViewById(R.id.updateby);


                Button update=dialog.findViewById(R.id.updateBtn);
                Button delete=dialog.findViewById(R.id.deleteBtn);

                // Item Update Button click listener
                String itemId=item.getItemId();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialogUpdate=new Dialog(context);
                        dialogUpdate.setContentView(R.layout.delete_item_dialog);
                        dialogUpdate.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialogUpdate.setCancelable(false);
                        dialogUpdate.getWindow().getAttributes().windowAnimations=R.style.animation;
                        Button no=dialogUpdate.findViewById(R.id.noDeleteItem);
                        Button yes=dialogUpdate.findViewById(R.id.yesDeleteItem);
                        TextView message=dialogUpdate.findViewById(R.id.deleteTextMessage);
                        message.setText("Are you sure want to Update This Item.");
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context,"Item is not Updated. ",Toast.LENGTH_SHORT).show();
                                dialogUpdate.dismiss();
                            }
                        });
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String itemName =name.getText().toString();
                                String itemMrp =mrp.getText().toString();
                                String itemExpairy =expairy.getText().toString();
                                String itemCompany =company.getText().toString();
                                String itemNetRate =net.getText().toString();
                                Helper helper=new Helper();
                                String itemDownRate=helper.calculationDownRate(itemMrp,itemNetRate);
                                String itemLessRate=helper.calculationLessRate(itemMrp,itemNetRate);
                                String itemPtrLessRate=helper.calculationPtrLessRate(itemMrp,itemNetRate);
                                ItemModels itemModels= new ItemModels();
                                itemModels.setItemName(itemName);
                                itemModels.setItemMrp(itemMrp);
                                itemModels.setItemCompany(itemCompany);
                                itemModels.setItemExpairyDate(itemExpairy);
                                itemModels.setItemRate(itemNetRate);
                                itemModels.setItemRateInDown(itemDownRate);
                                itemModels.setItemRateInLess(itemLessRate);
                                itemModels.setItemRateInPtr(itemPtrLessRate);
                                Calendar calendar =Calendar.getInstance();
                                String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
                                String dateOfUpdation = "Last Update: "+currentDate+", ";
                                String updateBy = "Vikram";
                                itemModels.setUpdatedBy(updateBy);
                                itemModels.setItemId(itemId);
                                itemModels.setUpdatedDate(dateOfUpdation);
                                itemModels.setItemPic(item.getItemPic());
                                FirebaseDatabase.getInstance().getReference().child("Items").child(itemId)
                                        .setValue(itemModels);
                                Toast.makeText(context,"Item is Updated Successfully! ",Toast.LENGTH_SHORT).show();
                                dialogUpdate.dismiss();
                            }
                        });
                        dialog.dismiss();
                        dialogUpdate.show();
                    }
                });

                // Item Delete Button click listener

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialogDelete=new Dialog(context);
                        dialogDelete.setContentView(R.layout.delete_item_dialog);
                        dialogDelete.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialogDelete.setCancelable(false);
                        dialogDelete.getWindow().getAttributes().windowAnimations=R.style.animation;
                        Button no=dialogDelete.findViewById(R.id.noDeleteItem);
                        Button yes=dialogDelete.findViewById(R.id.yesDeleteItem);
                        TextView message=dialogDelete.findViewById(R.id.deleteTextMessage);
                        message.setText("Are you sure want to delete This Item.");
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context,"Item is Safe",Toast.LENGTH_SHORT).show();
                                dialogDelete.dismiss();
                            }
                        });
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseDatabase.getInstance().getReference().child("Items").child(item.getItemId()).removeValue();
                                FirebaseStorage.getInstance().getReference().child("ItemPic").child(item.getItemId()).delete();
                                Toast.makeText(context,"Item is Deleted Successfully!",Toast.LENGTH_SHORT).show();
                                dialogDelete.dismiss();
                            }
                        });
                        dialog.dismiss();
                        dialogDelete.show();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<ItemModels> filteredList=new ArrayList<>();
            if(keyword.toString().isEmpty()){
                filteredList.addAll(backup);
            }else{
                for (ItemModels models:backup){
                    if(models.getItemName().toLowerCase().contains(keyword.toString().toLowerCase())) {
                        filteredList.add(models);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.clear();
            data.addAll((ArrayList<ItemModels>) results.values);
            notifyDataSetChanged();
        }
    };
    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSampleBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemSampleBinding.bind(itemView);
        }
    }
}