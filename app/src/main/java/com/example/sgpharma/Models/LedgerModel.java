package com.example.sgpharma.Models;

public class LedgerModel {
    String ledgerId,ledgerName,ledgerMobile,ledgerBalance,ledgerProfilePic,updatedBy,updatedDate;

    public LedgerModel(String ledgerId, String ledgerName, String ledgerMobile, String ledgerBalance,
                       String ledgerProfilePic, String updatedBy, String updatedDate) {
        this.ledgerId = ledgerId;
        this.ledgerName = ledgerName;
        this.ledgerMobile = ledgerMobile;
        this.ledgerBalance = ledgerBalance;
        this.ledgerProfilePic = ledgerProfilePic;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }

    public LedgerModel() {
    }

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public String getLedgerMobile() {
        return ledgerMobile;
    }

    public void setLedgerMobile(String ledgerMobile) {
        this.ledgerMobile = ledgerMobile;
    }

    public String getLedgerBalance() {
        return ledgerBalance;
    }

    public void setLedgerBalance(String ledgerBalance) {
        this.ledgerBalance = ledgerBalance;
    }

    public String getLedgerProfilePic() {
        return ledgerProfilePic;
    }

    public void setLedgerProfilePic(String ledgerProfilePic) {
        this.ledgerProfilePic = ledgerProfilePic;
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
}
