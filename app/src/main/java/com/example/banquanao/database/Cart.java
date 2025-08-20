package com.example.banquanao.database;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Cart implements Parcelable {
    private int id;
    private String name ;
    private byte[] img;
    private int quantity_cart;
    private double price;
    private double total;
    public Cart(){}


    public Cart(int id, String name,  byte[] img, int quantity_cart, double price) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.quantity_cart = quantity_cart;
        this.price = price;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  byte[] getImg() {
        return img;
    }

    public void setImg( byte[] img) {
        this.img = img;
    }

    public int getQuantity_cart() {
        return quantity_cart;
    }

    public void setQuantity_cart(int quantity_cart) {
        this.quantity_cart = quantity_cart;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getString(){
        return getId()+" "+getName()+" "+getPrice()+" " +getQuantity_cart()+" "+getImg()+" ";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            dest.writeBlob(img);
        }
        dest.writeInt(quantity_cart);
        dest.writeDouble(price);
        dest.writeDouble(total);
    }

    public static final Parcelable.Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel source) {
            return new Cart(source);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    protected Cart(Parcel in){
        id = in.readInt();
        name = in.readString();
        img = in.readBlob();
        quantity_cart = in.readInt();
        price = in.readDouble();
        total = in.readDouble();
    }
}
