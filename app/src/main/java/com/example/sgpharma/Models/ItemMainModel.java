package com.example.sgpharma.Models;

import java.util.ArrayList;

public class ItemMainModel {
    ArrayList<ItemModels> itemDetails;
   ArrayList<ItemPicModel> itemPics;

    public ItemMainModel(ArrayList<ItemModels> itemDetails, ArrayList<ItemPicModel> itemPics) {
        this.itemDetails = itemDetails;
        this.itemPics = itemPics;
    }

    public ItemMainModel() {
    }

    public ArrayList<ItemModels> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ArrayList<ItemModels> itemDetails) {
        this.itemDetails = itemDetails;
    }

    public ArrayList<ItemPicModel> getItemPics() {
        return itemPics;
    }

    public void setItemPics(ArrayList<ItemPicModel> itemPics) {
        this.itemPics = itemPics;
    }
}


