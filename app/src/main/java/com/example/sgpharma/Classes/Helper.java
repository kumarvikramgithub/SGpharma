package com.example.sgpharma.Classes;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sgpharma.Activities.InputItemActivity;
import com.example.sgpharma.Activities.MainActivity;
import com.example.sgpharma.Models.ItemModels;
import com.example.sgpharma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class Helper {


    public Helper() {

    }

    public String calculationDownRate(String itemMrp,String itemNetRate) {

        Float mrpFloat = Float.parseFloat(itemMrp);
        Float netRateFloat = Float.parseFloat(itemNetRate);

//                Down rate Calculation

        Float mrpFloat23less = mrpFloat - mrpFloat * 23 / 100;
        Float lessToNetRate = mrpFloat23less - netRateFloat;
        Float downRate = lessToNetRate * 100 / mrpFloat23less;


        String itemDownRate = String.valueOf(downRate);
        return itemDownRate;
    }
    public String calculationLessRate(String itemMrp,String itemNetRate) {

        Float mrpFloat = Float.parseFloat(itemMrp);
        Float netRateFloat = Float.parseFloat(itemNetRate);

//                Less Rate from Mrp calculation
        Float lessToNetRate = mrpFloat - netRateFloat;
        Float lessRate = lessToNetRate * 100 / mrpFloat;


        String itemLessRate = String.valueOf(lessRate);

        return itemLessRate;
    }
    public String calculationPtrLessRate(String itemMrp,String itemNetRate) {

        Float mrpFloat = Float.parseFloat(itemMrp);
        Float netRateFloat = Float.parseFloat(itemNetRate);



//                Ptr Less Calculation
        Float tradeLessAmount = mrpFloat - mrpFloat * 28.57f / 100;
        Float tradeLessAmountplusless = netRateFloat * 100 / 112;
        tradeLessAmountplusless = tradeLessAmount - tradeLessAmountplusless;
        tradeLessAmountplusless *= 100;
        tradeLessAmountplusless /= tradeLessAmount;

        String itemPtrLessRate = String.valueOf(tradeLessAmountplusless);

        return itemPtrLessRate;
    }
    public String currentDate(){
        Calendar calendar =Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
        return currentDate;
    }



}
