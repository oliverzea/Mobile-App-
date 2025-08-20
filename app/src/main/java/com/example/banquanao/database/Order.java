package com.example.banquanao.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Order implements Serializable {
    private int id_Order;
    private int id_Cus;
    private int id_Manager;
    private int id_Staff;
    private double  price_total;
    private int id_State;
    private String toPay;
    private String orderDate;
    private String expertedDate;
    Customer customer;
    public Order(){}

    public Order(Customer customer, int id_Order, int id_Manager, int id_Staff, double price_total, int id_State, String toPay) {
        this.customer=customer;
        this.id_Order = id_Order;
        this.id_Manager = id_Manager;
        this.id_Staff = id_Staff;
        this.price_total = price_total;
        this.id_State = id_State;
        this.toPay = toPay;
    }
    public Order(int idOrder, Customer customer, int idSate, int idManager, int idStaff, double priceTotal, String toPay) {
        this.id_Order = idOrder;
        this.customer = customer;
        this.id_State = idSate;
        this.id_Manager = idManager;
        this.id_Staff = idStaff;
        this.price_total = priceTotal;
        this.toPay = toPay;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order(int id_Order, int id_Cus, int id_Manager, int id_Staff, double price_total, int id_State, String toPay, String orderDate, String expertedDate) {
        this.id_Order = id_Order;
        this.id_Cus = id_Cus;
        this.id_Manager = id_Manager;
        this.id_Staff = id_Staff;
        this.price_total = price_total;
        this.id_State = id_State;
        this.toPay = toPay;
        this.orderDate = orderDate;
        this.expertedDate = expertedDate;
    }

    protected Order(Parcel in) {
        id_Order = in.readInt();
        id_Cus = in.readInt();
        id_Manager = in.readInt();
        id_Staff = in.readInt();
        price_total = in.readDouble();
        id_State = in.readInt();
        toPay = in.readString();
        orderDate = in.readString();
        expertedDate=in.readString();
    }



    public int getId_Order() {
        return id_Order;
    }

    public void setId_Order(int id_Order) {
        this.id_Order = id_Order;
    }

    public int getId_Cus() {
        return id_Cus;
    }

    public void setId_Cus(int id_Cus) {
        this.id_Cus = id_Cus;
    }

    public int getId_Manager() {
        return id_Manager;
    }

    public void setId_Manager(int id_Manager) {
        this.id_Manager = id_Manager;
    }

    public int getId_Staff() {
        return id_Staff;
    }

    public void setId_Staff(int id_Staff) {
        this.id_Staff = id_Staff;
    }

    public double getPrice_total() {
        return price_total;
    }

    public void setPrice_total(double price_total) {
        this.price_total = price_total;
    }

    public int getId_State() {
        return id_State;
    }

    public void setId_State(int id_State) {
        this.id_State = id_State;
    }

    public String getToPay() {
        return toPay;
    }

    public void setToPay(String toPay) {
        this.toPay = toPay;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getExpertedDate() {
        return expertedDate;
    }

    public void setExpertedDate(String expertedDate) {
        this.expertedDate = expertedDate;
    }

    public String getStringData(){
        return "Id order: "+id_Order+" id cus: "+id_Cus+" Statement: "+id_State+"\n";
    }

}
