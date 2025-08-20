package com.example.banquanao.database;

import java.io.Serializable;

public class Card implements Serializable {
    private int id_Card;
    private int number_Cart;
    private String date_issue;
    private int numberCVV;

    public  Card(){}
    public Card(int id_Card, int number_Cart, String date_issue, int numberCVV) {
        this.id_Card = id_Card;
        this.number_Cart = number_Cart;
        this.date_issue = date_issue;
        this.numberCVV = numberCVV;
    }

    public int getId_Card() {
        return id_Card;
    }

    public void setId_Card(int id_Card) {
        this.id_Card = id_Card;
    }

    public int getNumber_Cart() {
        return number_Cart;
    }

    public void setNumber_Cart(int number_Cart) {
        this.number_Cart = number_Cart;
    }

    public String getDate_issue() {
        return date_issue;
    }

    public void setDate_issue(String date_issue) {
        this.date_issue = date_issue;
    }

    public int getNumberCVV() {
        return numberCVV;
    }

    public void setNumberCVV(int numberCVV) {
        this.numberCVV = numberCVV;
    }
    public String toStringData(){
        return getId_Card()+" "+getNumber_Cart()+" "+getNumberCVV()+" "+getDate_issue()+"";
    }
}
