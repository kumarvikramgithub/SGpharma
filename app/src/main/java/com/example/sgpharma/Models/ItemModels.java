package com.example.sgpharma.Models;

public class ItemModels {
    String itemId,itemMrp,itemRate,itemExpairyDate,itemCompany,itemRateInDown,itemRateInLess,itemRateInPtr,itemName,updatedBy,updatedDate,itemPic;
    //ArrayList<ItemPicModel> itemPic;


    public ItemModels(String itemId, String itemMrp, String itemRate, String itemExpairyDate, String itemCompany, String itemRateInDown,
                      String itemRateInLess, String itemRateInPtr, String itemName, String updatedBy, String updatedDate, String itemPic) {
        this.itemId = itemId;
        this.itemMrp = itemMrp;
        this.itemRate = itemRate;
        this.itemExpairyDate = itemExpairyDate;
        this.itemCompany = itemCompany;
        this.itemRateInDown = itemRateInDown;
        this.itemRateInLess = itemRateInLess;
        this.itemRateInPtr = itemRateInPtr;
        this.itemName = itemName;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.itemPic = itemPic;
    }

    public ItemModels() {
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemMrp() {
        return itemMrp;
    }

    public void setItemMrp(String itemMrp) {
        this.itemMrp = itemMrp;
    }

    public String getItemRate() {
        return itemRate;
    }

    public void setItemRate(String itemRate) {
        this.itemRate = itemRate;
    }

    public String getItemExpairyDate() {
        return itemExpairyDate;
    }

    public void setItemExpairyDate(String itemExpairyDate) {
        this.itemExpairyDate = itemExpairyDate;
    }

    public String getItemCompany() {
        return itemCompany;
    }

    public void setItemCompany(String itemCompany) {
        this.itemCompany = itemCompany;
    }

    public String getItemRateInDown() {
        return itemRateInDown;
    }

    public void setItemRateInDown(String itemRateInDown) {
        this.itemRateInDown = itemRateInDown;
    }

    public String getItemRateInLess() {
        return itemRateInLess;
    }

    public void setItemRateInLess(String itemRateInLess) {
        this.itemRateInLess = itemRateInLess;
    }

    public String getItemRateInPtr() {
        return itemRateInPtr;
    }

    public void setItemRateInPtr(String itemRateInPtr) {
        this.itemRateInPtr = itemRateInPtr;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getItemPic() {
        return itemPic;
    }

    public void setItemPic(String itemPic) {
        this.itemPic = itemPic;
    }
}
