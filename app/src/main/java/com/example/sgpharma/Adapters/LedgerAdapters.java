package com.example.sgpharma.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sgpharma.Activities.LedgerActivity;
import com.example.sgpharma.Classes.Helper;
import com.example.sgpharma.Models.LedgerModel;
import com.example.sgpharma.R;
import com.example.sgpharma.databinding.LedgerSampleBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LedgerAdapters extends RecyclerView.Adapter<LedgerAdapters.LAHolder> {
    Context context;
    ArrayList<LedgerModel> ledgers;

    public LedgerAdapters(Context context, ArrayList<LedgerModel> ledgers) {
        this.context = context;
        this.ledgers = ledgers;
    }

    @NonNull
    @Override
    public LAHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.ledger_sample,parent,false);
        return new LAHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LAHolder holder, int position) {
        LedgerModel ledger =ledgers.get(position);
        holder.binding.ledgerNameTv.setText(ledger.getLedgerName());
        holder.binding.ledgerMoblieTv.setText(ledger.getLedgerMobile());
        holder.binding.ledgerBlrTv.setText(ledger.getLedgerBalance());
        Picasso.get().load(ledger.getLedgerProfilePic()).placeholder(R.drawable.ic_user).into(holder.binding.ledgerProfileImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.ledger_add_dialog_sample);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                //dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
                ImageView imageView=dialog.findViewById(R.id.ledgerPic);
                Picasso.get().load(ledger.getLedgerProfilePic()).placeholder(R.drawable.ic_user).into(imageView);
                EditText name=dialog.findViewById(R.id.ledgerNameEd);
                name.setText(ledger.getLedgerName());
                EditText mobile=dialog.findViewById(R.id.ledgerMobileEd);
                mobile.setText(ledger.getLedgerMobile());
                EditText balance=dialog.findViewById(R.id.ledgerOpeningBalEd);
                balance.setText(ledger.getLedgerBalance());
                Button update=dialog.findViewById(R.id.updateLedgerBtn);
                Button delete=dialog.findViewById(R.id.deleteLedgerBtn);
                String ledgerId=ledger.getLedgerId();
                ProgressDialog progressDialog=new ProgressDialog(context);
                progressDialog.setMessage("Updating Ledger.......");
                progressDialog.setCancelable(false);
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
                        message.setText("Are you sure want to Update This Ledger.");
                        if(name.getText().toString().isEmpty()){
                            name.setText("Name is empty");
                        }else if(mobile.getText().toString().length()>10||mobile.getText().toString().length()<10){
                            mobile.setText("Mobile number must be 10 digit");
                        }else{
                            if(balance.getText().toString().isEmpty()) {
                                balance.setText("0.00");
                            }
                        }
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context,"Ledger not Updated. ",Toast.LENGTH_SHORT).show();
                                dialogUpdate.dismiss();
                            }
                        });
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LedgerModel ledgerModel=new LedgerModel();
                                ledgerModel.setLedgerBalance(balance.getText().toString());
                                ledgerModel.setLedgerName(name.getText().toString());
                                ledgerModel.setLedgerMobile(mobile.getText().toString());
                                ledgerModel.setLedgerProfilePic(ledger.getLedgerProfilePic());
                                ledgerModel.setLedgerId(ledgerId);
                                Helper helper=new Helper();
                                String updateBy = "Vikram";
                                ledgerModel.setUpdatedBy(updateBy);
                                ledgerModel.setUpdatedDate("Last Update: "+helper.currentDate()+", ");
                                FirebaseDatabase.getInstance().getReference().child("Ledgers").child(ledgerId).setValue(ledgerModel);
                                dialogUpdate.dismiss();
                                Toast.makeText(context,"Ledger Updated Successfully!",Toast.LENGTH_SHORT).show();


                            }
                        });
                        dialog.dismiss();
                        dialogUpdate.show();

                    }
                });
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
                        message.setText("Are you sure want to delete This Ledger.");
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context,"Ledger is Safe",Toast.LENGTH_SHORT).show();
                                dialogDelete.dismiss();
                            }
                        });
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseDatabase.getInstance().getReference().child("Ledgers").child(ledger.getLedgerId()).removeValue();
                                FirebaseStorage.getInstance().getReference().child("LedgerPic").child(ledger.getLedgerId()).delete();
                                Toast.makeText(context,"Ledger is Deleted Successfully!",Toast.LENGTH_SHORT).show();
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
        return ledgers.size();
    }

    public class LAHolder extends RecyclerView.ViewHolder {
        LedgerSampleBinding binding;
        public LAHolder(@NonNull View itemView) {
            super(itemView);
            binding=LedgerSampleBinding.bind(itemView);
        }
    }
}
