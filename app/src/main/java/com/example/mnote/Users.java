package com.example.mnote;

import static com.example.mnote.HomePage.total;

public class Users
{
    private String Date;
    private String Note;
    private String Amount;
//    private String added;
//    private String deducted;
    private String Total;

    public Users(String date, String note, String Amount, String Total) {
        this.Date = date;
        this.Note = note;
        this.Amount = Amount;
//        this.added = added;
//        this.deducted = deducted;
        this.Total = Total;
    }
    public Users() {

    }



    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        this.Amount = amount;
    }

//    public String getAdded() {
//        return added;
//    }

//    public void setAdded(String added) {
//        this.added = added;
//    }

//    public String getDeducted() {
//        return deducted;
//    }

//    public void setDeducted(String deducted) {
//        this.deducted = deducted;
//    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        this.Total = total;
    }

    public  String toString()
    {
        return  this.Date +" " + this.Note + " " + this.Amount +  " "
                + " " + this.Total;
    }
}
