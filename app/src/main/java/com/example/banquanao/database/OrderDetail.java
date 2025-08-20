package com.example.banquanao.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private int id_detail;
    private int id_order;
    private int id_Product;
    private double price;
    private int number;
    private double price_total;
    private int id_Size;

    public OrderDetail(){}

    public int getId_detail() {
        return id_detail;
    }

    public void setId_detail(int id_detail) {
        this.id_detail = id_detail;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_Product() {
        return id_Product;
    }

    public void setId_Product(int id_Product) {
        this.id_Product = id_Product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice_total() {
        return price_total;
    }

    public void setPrice_total(double price_total) {
        this.price_total = price_total;
    }

    public int getId_Size() {
        return id_Size;
    }

    public void setId_Size(int id_Size) {
        this.id_Size = id_Size;
    }

    public String DetailData(){
        return "ID order detail: "+id_detail+" id order:" +id_order+" quantity: "+number+" price total: "+price_total+"\n";
    }

    public OrderDetail(int id_detail, int id_order, int id_Product, double price, int number, double price_total, int id_Size) {
        this.id_detail = id_detail;
        this.id_order = id_order;
        this.id_Product = id_Product;
        this.price = price;
        this.number = number;
        this.price_total = price_total;
        this.id_Size = id_Size;
    }





}
